package com.gotofoods.menuapi.xml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class OptionXml {

  @JacksonXmlProperty(isAttribute = true, localName = "id")
  private String id;

  @JacksonXmlProperty(isAttribute = true, localName = "name")
  private String name;

  @JacksonXmlProperty(isAttribute = true, localName = "isdefault")
  private Boolean isDefault;

  @JacksonXmlProperty(isAttribute = true, localName = "cost")
  private String cost;

  @JacksonXmlProperty(isAttribute = true, localName = "basecalories")
  private Integer baseCalories;

  @JacksonXmlProperty(isAttribute = true, localName = "adjustsparentcalories")
  private Boolean adjustsParentCalories;

  @JacksonXmlProperty(isAttribute = true, localName = "adjustsparentprice")
  private Boolean adjustsParentPrice;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(Boolean aDefault) {
    isDefault = aDefault;
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

  public Boolean getAdjustsParentCalories() {
    return adjustsParentCalories;
  }

  public void setAdjustsParentCalories(Boolean adjustsParentCalories) {
    this.adjustsParentCalories = adjustsParentCalories;
  }

  public Boolean getAdjustsParentPrice() {
    return adjustsParentPrice;
  }

  public void setAdjustsParentPrice(Boolean adjustsParentPrice) {
    this.adjustsParentPrice = adjustsParentPrice;
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
