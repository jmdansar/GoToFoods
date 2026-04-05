package com.gotofoods.menuapi.xml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;

public class OptionGroupXml {

  @JacksonXmlProperty(isAttribute = true, localName = "id")
  private String id;

  @JacksonXmlProperty(isAttribute = true, localName = "description")
  private String description;

  @JacksonXmlProperty(isAttribute = true, localName = "mandatory")
  private Boolean mandatory;

  @JacksonXmlProperty(isAttribute = true, localName = "sortorder")
  private Integer sortOrder;

  @JacksonXmlProperty(localName = "options")
  private OptionsXml options;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getMandatory() {
    return mandatory;
  }

  public void setMandatory(Boolean mandatory) {
    this.mandatory = mandatory;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
  }

  public OptionsXml getOptions() {
    return options;
  }

  public void setOptions(OptionsXml options) {
    this.options = options;
  }
}
