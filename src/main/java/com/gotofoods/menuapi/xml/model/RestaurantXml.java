package com.gotofoods.menuapi.xml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

@JacksonXmlRootElement(localName = "restaurant")
public class RestaurantXml {

  @JacksonXmlProperty(isAttribute = true, localName = "id")
  private String id;

  @JacksonXmlProperty(isAttribute = true, localName = "name")
  private String name;

  @JacksonXmlProperty(isAttribute = true, localName = "storename")
  private String storeName;

  @JacksonXmlProperty(isAttribute = true, localName = "brand")
  private String brand;

  @JacksonXmlProperty(isAttribute = true, localName = "telephone")
  private String telephone;

  @JacksonXmlProperty(isAttribute = true, localName = "showcalories")
  private Boolean showCalories;

  @JacksonXmlProperty(localName = "menu")
  private MenuXml menu;

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

  public String getStoreName() {
    return storeName;
  }

  public void setStoreName(String storeName) {
    this.storeName = storeName;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public Boolean getShowCalories() {
    return showCalories;
  }

  public void setShowCalories(Boolean showCalories) {
    this.showCalories = showCalories;
  }

  public MenuXml getMenu() {
    return menu;
  }

  public void setMenu(MenuXml menu) {
    this.menu = menu;
  }
}
