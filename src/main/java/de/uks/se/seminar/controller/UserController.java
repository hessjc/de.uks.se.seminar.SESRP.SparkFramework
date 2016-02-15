/**
 * file description
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
package de.uks.se.seminar.controller;

import static spark.Spark.get;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.uks.se.seminar.service.UserService;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

/**
 * UserController with user dependent routes.
 * 
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
public class UserController extends AbstractHttpController
{
   private static final Logger LOGGER = LoggerFactory.getLogger(TopicController.class);

   public static UserService userService = new UserService();

   public UserController()
   {
      // show user template to show users (using HTTP get method)
      get("/users", (req, res) -> {
         LOGGER.debug("get-request to /users .");
         HashMap<String, Object> datas = new HashMap<String, Object>();
         datas.put("users", UserController.userService.findAll());
         return new ModelAndView(datas, USER_TEMPLATE);
      } , new ThymeleafTemplateEngine());
   }
}