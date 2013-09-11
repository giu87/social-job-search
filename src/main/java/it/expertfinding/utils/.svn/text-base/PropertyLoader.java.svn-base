package it.expertfinding.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;

public class PropertyLoader {

   protected static String config_file_name = "conf.properties";
   protected static Logger log = Facade.log;

   public static Configuration load() {
      Configuration conf = null;
      try {
         conf = new PropertiesConfiguration(config_file_name);
      } catch (ConfigurationException e) {
         log.error("Unable to load configuration file", e);
      }
      return conf;
   }
}
