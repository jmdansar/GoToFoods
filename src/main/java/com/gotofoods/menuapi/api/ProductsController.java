package com.gotofoods.menuapi.api;

import com.gotofoods.menuapi.service.MenuService;
import com.gotofoods.menuapi.xml.model.ProductXml;
import java.util.List;
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

  @GetMapping("/{productId}")
  public ProductXml getProduct(@PathVariable String productId) {
    ProductXml product = menuService.getStore().getProductById(productId);
    if (product == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + productId);
    }
    return product;
  }
}
