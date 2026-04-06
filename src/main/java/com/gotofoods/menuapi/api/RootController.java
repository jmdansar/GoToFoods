package com.gotofoods.menuapi.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RootController {

  @GetMapping("/")
  public String root() {
    return "redirect:/swagger-ui/index.html";
  }

  @GetMapping("/health")
  @ResponseBody
  public String health() {
    return "ok";
  }
}
