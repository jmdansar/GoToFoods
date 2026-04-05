package com.gotofoods.menuapi.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.gotofoods.menuapi.config.MenuProperties;
import com.gotofoods.menuapi.xml.model.RestaurantXml;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

  private final XmlMapper xmlMapper;
  private final MenuProperties menuProperties;
  private final AtomicReference<MenuStore> storeRef = new AtomicReference<>();

  public MenuService(@Qualifier("xmlMapper") XmlMapper xmlMapper, MenuProperties menuProperties) {
    this.xmlMapper = xmlMapper;
    this.menuProperties = menuProperties;
  }

  @PostConstruct
  public void init() {
    reload();
  }

  public MenuStore getStore() {
    MenuStore store = storeRef.get();
    if (store == null) {
      throw new IllegalStateException("Menu not loaded");
    }
    return store;
  }

  public synchronized void reload() {
    String path = menuProperties.getXmlPath();
    if (path == null || path.isBlank()) {
      throw new IllegalStateException("menu.xmlPath is not configured");
    }

    File xmlFile = new File(path);
    if (!xmlFile.isAbsolute()) {
      xmlFile = new File(System.getProperty("user.dir"), path);
    }

    try {
      RestaurantXml restaurant = xmlMapper.readValue(xmlFile, RestaurantXml.class);
      storeRef.set(new MenuStore(restaurant));
    } catch (IOException e) {
      throw new IllegalStateException("Failed to read menu XML from " + xmlFile.getAbsolutePath(), e);
    }
  }
}
