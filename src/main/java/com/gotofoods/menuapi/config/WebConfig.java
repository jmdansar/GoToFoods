package com.gotofoods.menuapi.config;

import java.util.Iterator;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    // We keep jackson-dataformat-xml for parsing the menu XML file via XmlMapper.
    // But we don't want Spring MVC to serialize responses as XML (it breaks springdoc's swagger-config).
    for (Iterator<HttpMessageConverter<?>> it = converters.iterator(); it.hasNext(); ) {
      HttpMessageConverter<?> c = it.next();
      if (c instanceof MappingJackson2XmlHttpMessageConverter) {
        it.remove();
      }
    }
  }
}
