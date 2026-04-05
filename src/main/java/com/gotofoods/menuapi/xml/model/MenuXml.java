package com.gotofoods.menuapi.xml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class MenuXml {

  @JacksonXmlProperty(localName = "categories")
  private CategoriesXml categories;

  public CategoriesXml getCategories() {
    return categories;
  }

  public void setCategories(CategoriesXml categories) {
    this.categories = categories;
  }
}
