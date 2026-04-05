package com.gotofoods.menuapi.xml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;

public class ModifiersXml {

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "optiongroup")
  private List<OptionGroupXml> optionGroups;

  public List<OptionGroupXml> getOptionGroups() {
    return optionGroups;
  }

  public void setOptionGroups(List<OptionGroupXml> optionGroups) {
    this.optionGroups = optionGroups;
  }
}
