package com.gotofoods.menuapi.xml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;

public class OptionsXml {

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "option")
  private List<OptionXml> options;

  public List<OptionXml> getOptions() {
    return options;
  }

  public void setOptions(List<OptionXml> options) {
    this.options = options;
  }
}
