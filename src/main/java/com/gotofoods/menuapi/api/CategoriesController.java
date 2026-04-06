package com.gotofoods.menuapi.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gotofoods.menuapi.service.MenuService;
import com.gotofoods.menuapi.xml.model.CategoryXml;
import com.gotofoods.menuapi.xml.model.ProductXml;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController {

  private static final int PAGE_SIZE = 20;

  private final MenuService menuService;
  private final ObjectMapper objectMapper;

  public CategoriesController(MenuService menuService, ObjectMapper objectMapper) {
    this.menuService = menuService;
    this.objectMapper = objectMapper;
  }

  @GetMapping("/id-names")
  public List<CategoryIdName> listCategoryIdNames() {
    return menuService.getStore().getCategories().stream()
        .map(c -> new CategoryIdName(c.getId(), c.getName()))
        .toList();
  }

  @GetMapping
  public List<CategoryXml> listCategories(@RequestParam(name = "page", defaultValue = "0") int page) {
    if (page < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page must be >= 0");
    }

    List<CategoryXml> categories = menuService.getStore().getCategories();
    int total = categories.size();
    long fromIndexLong = (long) page * PAGE_SIZE;
    if (fromIndexLong >= total) {
      return List.of();
    }

    int fromIndex = (int) fromIndexLong;
    int toIndex = Math.min(fromIndex + PAGE_SIZE, total);
    return categories.subList(fromIndex, toIndex);
  }

  @GetMapping("/{categoryId}")
  public CategoryXml getCategory(
      @PathVariable String categoryId,
      @RequestParam(name = "includeProducts", defaultValue = "false") boolean includeProducts) {

    CategoryXml category = menuService.getStore().getCategoryById(categoryId);
    if (category == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + categoryId);
    }

    if (includeProducts) {
      return category;
    }

    // Return category without the potentially large products list
    return shallowCopy(category);
  }

  @GetMapping("/{categoryId}/products-kv")
  public List<Map<String, Object>> listProductsKeyValue(@PathVariable String categoryId) {
    CategoryXml category = menuService.getStore().getCategoryById(categoryId);
    if (category == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + categoryId);
    }

    if (category.getProducts() == null || category.getProducts().getProducts() == null) {
      return List.of();
    }

    List<ProductXml> products = category.getProducts().getProducts();
    TypeReference<Map<String, Object>> mapType = new TypeReference<>() {};
    return products.stream().map(p -> objectMapper.convertValue(p, mapType)).toList();
  }

  private CategoryXml shallowCopy(CategoryXml category) {
    CategoryXml copy = new CategoryXml();
    copy.setId(category.getId());
    copy.setName(category.getName());
    copy.setDescription(category.getDescription());
    copy.setSortOrder(category.getSortOrder());
    copy.setProducts(null);
    return copy;
  }

  public record CategoryIdName(String id, String name) {}
}
