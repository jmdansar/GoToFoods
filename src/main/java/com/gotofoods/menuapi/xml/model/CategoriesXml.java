package com.gotofoods.menuapi.xml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;

public class CategoriesXml {

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "category")
  private List<CategoryXml> categories;

  public List<CategoryXml> getCategories() {
    return categories;
  }

  public void setCategories(List<CategoryXml> categories) {
    this.categories = categories;
  }
}
