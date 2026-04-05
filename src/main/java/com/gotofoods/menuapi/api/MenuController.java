package com.gotofoods.menuapi.api;

import com.gotofoods.menuapi.api.dto.MenuInfoDto;
import com.gotofoods.menuapi.service.MenuService;
import com.gotofoods.menuapi.service.MenuStore;
import com.gotofoods.menuapi.xml.model.RestaurantXml;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {

  private final MenuService menuService;

  public MenuController(MenuService menuService) {
    this.menuService = menuService;
  }

  @GetMapping
  public MenuInfoDto getMenuInfo() {
    MenuStore store = menuService.getStore();
    RestaurantXml restaurant = store.getRestaurant();

    MenuInfoDto dto = new MenuInfoDto();
    if (restaurant != null) {
      dto.setRestaurantId(restaurant.getId());
      dto.setRestaurantName(restaurant.getName());
      dto.setStoreName(restaurant.getStoreName());
      dto.setBrand(restaurant.getBrand());
      dto.setShowCalories(restaurant.getShowCalories());
    }

    dto.setCategoryCount(store.getCategories().size());
    dto.setProductCount(store.listProducts(null, null, null, null, null, true).size());

    return dto;
  }
}
