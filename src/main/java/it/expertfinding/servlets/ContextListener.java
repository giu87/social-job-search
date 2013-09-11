package it.expertfinding.servlets;

import it.expertfinding.utils.Facade;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;

@WebListener
public class ContextListener implements ServletContextListener {

   protected Logger log = Facade.log;

   @Override
   public void contextInitialized(ServletContextEvent sce) {
      log.info("Context initialized");

      log.debug("Context path: " + sce.getServletContext().getContextPath());
      log.debug("Server info: " + sce.getServletContext().getServerInfo());
      log.debug("Servlet Context Name: " + sce.getServletContext().getServletContextName());
   }

   @Override
   public void contextDestroyed(ServletContextEvent sce) {
      log.info("Context destroyed");

   }

}
