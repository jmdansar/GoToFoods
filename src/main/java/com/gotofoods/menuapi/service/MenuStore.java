package com.gotofoods.menuapi.service;

import com.gotofoods.menuapi.xml.model.CategoryXml;
import com.gotofoods.menuapi.xml.model.OptionGroupXml;
import com.gotofoods.menuapi.xml.model.OptionXml;
import com.gotofoods.menuapi.xml.model.ProductXml;
import com.gotofoods.menuapi.xml.model.RestaurantXml;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuStore {

  private final RestaurantXml restaurant;
  private final Map<String, CategoryXml> categoryById;
  private final Map<String, ProductXml> productById;
  private final Map<String, OptionGroupXml> optionGroupById;
  private final Map<String, OptionXml> optionById;

  public MenuStore(RestaurantXml restaurant) {
    this.restaurant = restaurant;

    Map<String, CategoryXml> categories = new HashMap<>();
    Map<String, ProductXml> products = new HashMap<>();
    Map<String, OptionGroupXml> optionGroups = new HashMap<>();
    Map<String, OptionXml> options = new HashMap<>();

    if (restaurant != null
        && restaurant.getMenu() != null
        && restaurant.getMenu().getCategories() != null
        && restaurant.getMenu().getCategories().getCategories() != null) {
      for (CategoryXml category : restaurant.getMenu().getCategories().getCategories()) {
        if (category == null || category.getId() == null) {
          continue;
        }
        categories.put(category.getId(), category);

        if (category.getProducts() == null || category.getProducts().getProducts() == null) {
          continue;
        }
        for (ProductXml product : category.getProducts().getProducts()) {
          if (product == null || product.getId() == null) {
            continue;
          }
          products.put(product.getId(), product);

          if (product.getModifiers() != null && product.getModifiers().getOptionGroups() != null) {
            indexOptionGroups(product.getModifiers().getOptionGroups(), optionGroups, options);
          }
        }
      }
    }

    this.categoryById = Collections.unmodifiableMap(categories);
    this.productById = Collections.unmodifiableMap(products);
    this.optionGroupById = Collections.unmodifiableMap(optionGroups);
    this.optionById = Collections.unmodifiableMap(options);
  }

  private static void indexOptionGroups(
      List<OptionGroupXml> roots,
      Map<String, OptionGroupXml> optionGroups,
      Map<String, OptionXml> options) {

    if (roots == null) {
      return;
    }

    Deque<OptionGroupXml> stack = new ArrayDeque<>(roots);
    while (!stack.isEmpty()) {
      OptionGroupXml group = stack.pop();
      if (group == null) {
        continue;
      }
      if (group.getId() != null) {
        optionGroups.put(group.getId(), group);
      }

      if (group.getOptions() == null || group.getOptions().getOptions() == null) {
        continue;
      }

      for (OptionXml option : group.getOptions().getOptions()) {
        if (option == null) {
          continue;
        }
        if (option.getId() != null) {
          options.put(option.getId(), option);
        }

        if (option.getModifiers() != null && option.getModifiers().getOptionGroups() != null) {
          stack.addAll(option.getModifiers().getOptionGroups());
        }
      }
    }
  }

  public RestaurantXml getRestaurant() {
    return restaurant;
  }

  public List<CategoryXml> getCategories() {
    if (restaurant == null
        || restaurant.getMenu() == null
        || restaurant.getMenu().getCategories() == null
        || restaurant.getMenu().getCategories().getCategories() == null) {
      return List.of();
    }
    return restaurant.getMenu().getCategories().getCategories();
  }

  public CategoryXml getCategoryById(String categoryId) {
    return categoryById.get(categoryId);
  }

  public ProductXml getProductById(String productId) {
    return productById.get(productId);
  }

  public OptionGroupXml getOptionGroupById(String optionGroupId) {
    return optionGroupById.get(optionGroupId);
  }

  public OptionXml getOptionById(String optionId) {
    return optionById.get(optionId);
  }

  public List<ProductXml> listProducts(
      String categoryId,
      Integer minCalories,
      Integer maxCalories,
      String q,
      String ingredient,
      Boolean includeDisabled) {

    List<ProductXml> out = new ArrayList<>();

    List<ProductXml> candidates;
    if (categoryId != null) {
      CategoryXml category = categoryById.get(categoryId);
      if (category == null || category.getProducts() == null || category.getProducts().getProducts() == null) {
        return List.of();
      }
      candidates = category.getProducts().getProducts();
    } else {
      candidates = new ArrayList<>(productById.values());
    }

    String qNorm = q == null ? null : q.trim().toLowerCase();
    String ingredientNorm = ingredient == null ? null : ingredient.trim().toLowerCase();
    boolean allowDisabled = includeDisabled != null && includeDisabled;

    for (ProductXml p : candidates) {
      if (p == null) {
        continue;
      }

      if (!allowDisabled && Boolean.TRUE.equals(p.getDisabled())) {
        continue;
      }

      Integer base = p.getBaseCalories();
      Integer max = p.getMaxCalories();
      if (minCalories != null) {
        // If base is known, use it; otherwise fall back to max.
        Integer value = base != null ? base : max;
        if (value != null && value < minCalories) {
          continue;
        }
      }
      if (maxCalories != null) {
        // If max is known, use it; otherwise fall back to base.
        Integer value = max != null ? max : base;
        if (value != null && value > maxCalories) {
          continue;
        }
      }

      if (qNorm != null && !qNorm.isEmpty()) {
        String hay = (safe(p.getName()) + " " + safe(p.getDescription())).toLowerCase();
        if (!hay.contains(qNorm)) {
          continue;
        }
      }

      if (ingredientNorm != null && !ingredientNorm.isEmpty()) {
        String desc = safe(p.getDescription()).toLowerCase();
        // This XML seems to encode ingredients in the product description with separators like " | ".
        if (!desc.contains(ingredientNorm)) {
          continue;
        }
      }

      out.add(p);
    }

    return out;
  }

  private static String safe(String s) {
    return s == null ? "" : s;
  }
}
