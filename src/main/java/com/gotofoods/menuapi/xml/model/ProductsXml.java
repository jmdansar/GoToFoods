package com.gotofoods.menuapi.xml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;

public class ProductsXml {

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "product")
  private List<ProductXml> products;

  public List<ProductXml> getProducts() {
    return products;
  }

  public void setProducts(List<ProductXml> products) {
    this.products = products;
  }
}
