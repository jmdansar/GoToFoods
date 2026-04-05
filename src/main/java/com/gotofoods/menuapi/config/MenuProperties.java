package com.gotofoods.menuapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "menu")
public class MenuProperties {

  /** Path to the menu XML. Supports absolute path or path relative to the working directory. */
  private String xmlPath;

  public String getXmlPath() {
    return xmlPath;
  }

  public void setXmlPath(String xmlPath) {
    this.xmlPath = xmlPath;
  }
}
