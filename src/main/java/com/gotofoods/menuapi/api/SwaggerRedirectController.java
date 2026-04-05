package com.gotofoods.menuapi.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {

  @GetMapping("/swagger-ui/")
  public String redirectSwaggerUi() {
    return "redirect:/swagger-ui/index.html";
  }
}
