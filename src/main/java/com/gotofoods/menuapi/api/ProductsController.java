package com.gotofoods.menuapi.api;

import com.gotofoods.menuapi.service.MenuService;
import com.gotofoods.menuapi.xml.model.ProductXml;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

  private final MenuService menuService;

  public ProductsController(MenuService menuService) {
    this.menuService = menuService;
  }

  @GetMapping
  public List<ProductXml> listProducts(
      @RequestParam(name = "categoryId", required = false) String categoryId,
      @RequestParam(name = "minCalories", required = false) Integer minCalories,
      @RequestParam(name = "maxCalories", required = false) Integer maxCalories,
      @RequestParam(name = "q", required = false) String q,
      @RequestParam(name = "ingredient", required = false) String ingredient,
      @RequestParam(name = "includeDisabled", required = false, defaultValue = "false") boolean includeDisabled) {

    return menuService.getStore().listProducts(categoryId, minCalories, maxCalories, q, ingredient, includeDisabled);
  }

  @GetMapping("/by-calories")
  public List<ProductXml> listProductsByCalories(
      @RequestParam(name = "minCalories", required = false) Integer minCalories,
      @RequestParam(name = "maxCalories", required = false) Integer maxCalories,
      @RequestParam(name = "categoryId", required = false) String categoryId,
      @RequestParam(name = "includeDisabled", required = false, defaultValue = "false") boolean includeDisabled) {

    if (minCalories != null && maxCalories != null && minCalories > maxCalories) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "minCalories must be <= maxCalories");
    }

    return menuService.getStore().listProducts(categoryId, minCalories, maxCalories, null, null, includeDisabled);
  }

  @GetMapping("/by-price")
  public List<ProductXml> listProductsByPrice(
      @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
      @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
      @RequestParam(name = "categoryId", required = false) String categoryId,
      @RequestParam(name = "includeDisabled", required = false, defaultValue = "false") boolean includeDisabled) {

    if (minPrice == null && maxPrice == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "minPrice or maxPrice is required");
    }
    if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "minPrice must be <= maxPrice");
    }

    List<ProductXml> candidates =
        menuService.getStore().listProducts(categoryId, null, null, null, null, includeDisabled);

    return candidates.stream()
        .filter(p -> matchesPrice(p, minPrice, maxPrice))
        .toList();
  }

  @GetMapping("/by-ingredients")
  public List<ProductXml> listProductsByIngredients(
      @RequestParam(name = "ingredients") List<String> ingredients,
      @RequestParam(name = "matchMode", required = false, defaultValue = "any") String matchMode,
      @RequestParam(name = "categoryId", required = false) String categoryId,
      @RequestParam(name = "minCalories", required = false) Integer minCalories,
      @RequestParam(name = "maxCalories", required = false) Integer maxCalories,
      @RequestParam(name = "q", required = false) String q,
      @RequestParam(name = "includeDisabled", required = false, defaultValue = "false") boolean includeDisabled) {

    List<String> tokens = normalizeIngredients(ingredients);
    if (tokens.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ingredients is required");
    }

    boolean requireAll;
    String mode = matchMode == null ? "any" : matchMode.trim().toLowerCase(Locale.ROOT);
    if (mode.equals("all")) {
      requireAll = true;
    } else if (mode.equals("any")) {
      requireAll = false;
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "matchMode must be 'any' or 'all'");
    }

    List<ProductXml> candidates =
        menuService.getStore().listProducts(categoryId, minCalories, maxCalories, q, null, includeDisabled);

    return candidates.stream()
        .filter(p -> matchesIngredients(p, tokens, requireAll))
        .toList();
  }

  @GetMapping("/{productId}")
  public ProductXml getProduct(@PathVariable String productId) {
    ProductXml product = menuService.getStore().getProductById(productId);
    if (product == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + productId);
    }
    return product;
  }

  private static List<String> normalizeIngredients(List<String> ingredients) {
    if (ingredients == null || ingredients.isEmpty()) {
      return List.of();
    }
    List<String> out = new ArrayList<>();
    for (String raw : ingredients) {
      if (raw == null) {
        continue;
      }
      String s = raw.trim();
      if (s.isEmpty()) {
        continue;
      }
      if (s.contains(",")) {
        for (String part : s.split(",")) {
          String p = part.trim();
          if (!p.isEmpty()) {
            out.add(p);
          }
        }
      } else {
        out.add(s);
      }
    }
    return out;
  }

  private static boolean matchesIngredients(ProductXml product, List<String> ingredients, boolean requireAll) {
    if (product == null) {
      return false;
    }
    String desc = product.getDescription();
    String hay = (desc == null ? "" : desc).toLowerCase(Locale.ROOT);

    if (requireAll) {
      for (String ing : ingredients) {
        if (!hay.contains(ing.toLowerCase(Locale.ROOT))) {
          return false;
        }
      }
      return true;
    }

    for (String ing : ingredients) {
      if (hay.contains(ing.toLowerCase(Locale.ROOT))) {
        return true;
      }
    }
    return false;
  }

  private static boolean matchesPrice(ProductXml product, BigDecimal minPrice, BigDecimal maxPrice) {
    if (product == null) {
      return false;
    }
    BigDecimal cost = parseCost(product.getCost());
    if (cost == null) {
      return false;
    }
    if (minPrice != null && cost.compareTo(minPrice) < 0) {
      return false;
    }
    if (maxPrice != null && cost.compareTo(maxPrice) > 0) {
      return false;
    }
    return true;
  }

  private static BigDecimal parseCost(String cost) {
    if (cost == null) {
      return null;
    }
    String s = cost.trim();
    if (s.isEmpty()) {
      return null;
    }
    try {
      return new BigDecimal(s);
    } catch (NumberFormatException e) {
      return null;
    }
  }
}
