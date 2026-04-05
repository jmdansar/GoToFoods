package com.gotofoods.menuapi.xml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ProductXml {

  @JacksonXmlProperty(isAttribute = true, localName = "id")
  private String id;

  @JacksonXmlProperty(isAttribute = true, localName = "chainproductid")
  private String chainProductId;

  @JacksonXmlProperty(isAttribute = true, localName = "name")
  private String name;

  @JacksonXmlProperty(isAttribute = true, localName = "description")
  private String description;

  @JacksonXmlProperty(isAttribute = true, localName = "cost")
  private String cost;

  @JacksonXmlProperty(isAttribute = true, localName = "basecalories")
  private Integer baseCalories;

  @JacksonXmlProperty(isAttribute = true, localName = "maxcalories")
  private Integer maxCalories;

  @JacksonXmlProperty(isAttribute = true, localName = "isdisabled")
  private Boolean disabled;

  @JacksonXmlProperty(isAttribute = true, localName = "sortorder")
  private Integer sortOrder;

  @JacksonXmlProperty(localName = "modifiers")
  private ModifiersXml modifiers;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getChainProductId() {
    return chainProductId;
  }

  public void setChainProductId(String chainProductId) {
    this.chainProductId = chainProductId;
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

  public String getCost() {
    return cost;
  }

  public void setCost(String cost) {
    this.cost = cost;
  }

  public Integer getBaseCalories() {
    return baseCalories;
  }

  public void setBaseCalories(Integer baseCalories) {
    this.baseCalories = baseCalories;
  }

  public Integer getMaxCalories() {
    return maxCalories;
  }

  public void setMaxCalories(Integer maxCalories) {
    this.maxCalories = maxCalories;
  }

  public Boolean getDisabled() {
    return disabled;
  }

  public void setDisabled(Boolean disabled) {
    this.disabled = disabled;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
  }

  public ModifiersXml getModifiers() {
    return modifiers;
  }

  public void setModifiers(ModifiersXml modifiers) {
    this.modifiers = modifiers;
  }
}
