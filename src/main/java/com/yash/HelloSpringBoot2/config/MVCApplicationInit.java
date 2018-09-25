package com.yash.HelloSpringBoot2.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MVCApplicationInit extends AbstractAnnotationConfigDispatcherServletInitializer {

   @Override
   protected Class<?>[] getRootConfigClasses() {
      return null;
   }

   @Override
   protected Class<?>[] getServletConfigClasses() {
      return new Class[] { MVCWebConfig.class };
   }

   @Override
   protected String[] getServletMappings() {
      return new String[] { "/" };
   }

}