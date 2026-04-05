package com.gotofoods.menuapi.api;

import com.gotofoods.menuapi.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

  private final MenuService menuService;

  public AdminController(MenuService menuService) {
    this.menuService = menuService;
  }

  @PostMapping("/reload")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void reload() {
    menuService.reload();
  }
}
