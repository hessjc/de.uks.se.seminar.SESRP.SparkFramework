/**
 * file description
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
package de.uks.se.seminar.controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.uks.se.seminar.persistence.Semester;
import de.uks.se.seminar.persistence.Topic;
import de.uks.se.seminar.persistence.User;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

/**
 * ApplicationController with default routes.
 * 
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
public class ApplicationController extends AbstractHttpController
{
   private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationController.class);

   @SuppressWarnings("unused")
   public ApplicationController()
   {
      // default/entry application route
      get("/", (req, res) -> {
         LOGGER.debug("get-request to / .");

         return new ModelAndView(new HashMap<String, Object>(), INDEX_TEMPLATE);
      } , new ThymeleafTemplateEngine());

      // registration route (register for topic)
      get("/registration", (req, res) -> {
         LOGGER.debug("get-request to /registration .");

         HashMap<String, Object> datas = new HashMap<String, Object>();
         Semester semester = SemesterController.semesterService.lastSemester();
         datas.put("semester", semester);
         datas.put("topics", semester.getTopics());

         return new ModelAndView(datas, REGISTRATION_TEMPLATE);
      } , new ThymeleafTemplateEngine());

      // post registration (register for topic)
      post("/registration", (req, res) -> {
         LOGGER.debug("post-request to /registration with parameters name={" + req.queryParams("name")
               + "}, email={" + req.queryParams("email") + ".");

         String name = req.queryParams("name");
         String email = req.queryParams("email");

         Set<String> queryParams = req.queryParams();
         List<Topic> topics = new LinkedList<Topic>();

         for (String param : queryParams)
         {
            if (param.startsWith("topic_"))
            {
               String[] queryParamsValues = req.queryParamsValues(param);
               if (queryParamsValues.length > 0 && Long.valueOf(queryParamsValues[0]) == 1)
               {
                  Topic topic = TopicController.topicService.find(Long.valueOf(param.substring(6)));
                  topics.add(topic);
               }
            }
         }

         User user = UserController.userService.createUser(name, email, topics);

         res.redirect("/");

         HashMap<String, Object> datas = new HashMap<String, Object>();

         return new ModelAndView(datas, INDEX_TEMPLATE);
      } , new ThymeleafTemplateEngine());
   }
}