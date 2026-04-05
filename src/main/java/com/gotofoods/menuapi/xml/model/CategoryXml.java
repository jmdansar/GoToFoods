package com.gotofoods.menuapi.xml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CategoryXml {

  @JacksonXmlProperty(isAttribute = true, localName = "id")
  private String id;

  @JacksonXmlProperty(isAttribute = true, localName = "name")
  private String name;

  @JacksonXmlProperty(isAttribute = true, localName = "description")
  private String description;

  @JacksonXmlProperty(isAttribute = true, localName = "sortorder")
  private Integer sortOrder;

  @JacksonXmlProperty(localName = "products")
  private ProductsXml products;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
  }

  public ProductsXml getProducts() {
    return products;
  }

  public void setProducts(ProductsXml products) {
    this.products = products;
  }
}
