package com.gotofoods.menuapi.api;

import com.gotofoods.menuapi.service.MenuService;
import com.gotofoods.menuapi.xml.model.CategoryXml;
import java.util.List;
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

  private final MenuService menuService;

  public CategoriesController(MenuService menuService) {
    this.menuService = menuService;
  }

  @GetMapping
  public List<CategoryXml> listCategories() {
    return menuService.getStore().getCategories();
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
    CategoryXml copy = new CategoryXml();
    copy.setId(category.getId());
    copy.setName(category.getName());
    copy.setDescription(category.getDescription());
    copy.setSortOrder(category.getSortOrder());
    copy.setProducts(null);
    return copy;
  }
}
