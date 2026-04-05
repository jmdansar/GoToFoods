package com.gotofoods.menuapi.api;

import com.gotofoods.menuapi.service.MenuService;
import com.gotofoods.menuapi.xml.model.OptionGroupXml;
import com.gotofoods.menuapi.xml.model.OptionXml;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/modifiers")
public class ModifiersController {

  private final MenuService menuService;

  public ModifiersController(MenuService menuService) {
    this.menuService = menuService;
  }

  @GetMapping("/option-groups/{optionGroupId}")
  public OptionGroupXml getOptionGroup(@PathVariable String optionGroupId) {
    OptionGroupXml group = menuService.getStore().getOptionGroupById(optionGroupId);
    if (group == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Option group not found: " + optionGroupId);
    }
    return group;
  }

  @GetMapping("/options/{optionId}")
  public OptionXml getOption(@PathVariable String optionId) {
    OptionXml option = menuService.getStore().getOptionById(optionId);
    if (option == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Option not found: " + optionId);
    }
    return option;
  }
}
