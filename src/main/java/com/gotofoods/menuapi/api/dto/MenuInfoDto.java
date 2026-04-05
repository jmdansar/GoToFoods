package com.gotofoods.menuapi.api.dto;

public class MenuInfoDto {
  private String restaurantId;
  private String restaurantName;
  private String storeName;
  private String brand;
  private Boolean showCalories;
  private int categoryCount;
  private int productCount;

  public String getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(String restaurantId) {
    this.restaurantId = restaurantId;
  }

  public String getRestaurantName() {
    return restaurantName;
  }

  public void setRestaurantName(String restaurantName) {
    this.restaurantName = restaurantName;
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

  public Boolean getShowCalories() {
    return showCalories;
  }

  public void setShowCalories(Boolean showCalories) {
    this.showCalories = showCalories;
  }

  public int getCategoryCount() {
    return categoryCount;
  }

  public void setCategoryCount(int categoryCount) {
    this.categoryCount = categoryCount;
  }

  public int getProductCount() {
    return productCount;
  }

  public void setProductCount(int productCount) {
    this.productCount = productCount;
  }
}
