/**
 * file description
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
package de.uks.se.seminar.controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.uks.se.seminar.persistence.Semester;
import de.uks.se.seminar.persistence.Topic;
import de.uks.se.seminar.persistence.User;
import de.uks.se.seminar.service.SemesterService;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

/**
 * SemesterController with semester dependent routes.
 * 
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
public class SemesterController extends AbstractHttpController
{
   private static final Logger LOGGER = LoggerFactory.getLogger(SemesterController.class);

   public static SemesterService semesterService = new SemesterService();

   public SemesterController()
   {
      // show semester template to show semesters (using HTTP get method)
      get("/semesters", (req, res) -> {
         LOGGER.debug("get-request to /semesters .");

         HashMap<String, Object> datas = new HashMap<String, Object>();
         datas.put("semesters", semesterService.findAll());

         return new ModelAndView(datas, SEMESTER_TEMPLATE);
      } , new ThymeleafTemplateEngine());

      // insert a semester (using HTTP post method)
      post("/semester/create", (req, res) -> {
         LOGGER.debug("post-request to /semester/create with parameters name={" + req.queryParams("name")
               + "}, date={" + req.queryParams("date") + ".");

         String name = req.queryParams("name");
         String date = req.queryParams("date");

         Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);

         semesterService.createSemester(name, expiryDate);

         res.redirect("/semesters");

         HashMap<String, Object> datas = new HashMap<String, Object>();
         datas.put("semesters", semesterService.findAll());

         return new ModelAndView(datas, SEMESTER_TEMPLATE);
      } , new ThymeleafTemplateEngine());

      // show semester.edit template to edit a semester (using HTTP get method)
      get("/semester/edit", (req, res) -> {
         LOGGER.debug("get-request to /semester/edit with parameters id={" + req.queryParams("id") + "}.");

         Long semester_id = Long.valueOf(req.queryParams("id"));

         Semester semester = semesterService.find(semester_id);

         HashMap<String, Object> datas = new HashMap<String, Object>();
         datas.put("semester", semester);

         return new ModelAndView(datas, SEMESTER_EDIT_TEMPLATE);
      } , new ThymeleafTemplateEngine());

      // edit a semester (using HTTP post method)
      post("/semester/edit", (req, res) -> {
         LOGGER.debug("post-request to /semester/edit with parameters id={" + req.queryParams("id") + "}, name={"
               + req.queryParams("name")
               + "}, date={" + req.queryParams("date") + ".");

         Long id = Long.valueOf(req.queryParams("id"));
         String name = req.queryParams("name");
         String date = req.queryParams("date");

         Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);

         Semester semester = semesterService.find(id);
         semesterService.updateSemester(semester, name, expiryDate);

         res.redirect("/semesters");

         HashMap<String, Object> datas = new HashMap<String, Object>();
         datas.put("semesters", semesterService.findAll());

         return new ModelAndView(datas, SEMESTER_TEMPLATE);
      } , new ThymeleafTemplateEngine());

      // delete a semester (using HTTP post method)
      post("/semester/delete", (req, res) -> {

         LOGGER.debug("post-request to /semester/delete with parameters id={" + req.queryParams("id") + "}.");
         Long id = Long.valueOf(req.queryParams("id"));

         Semester semester = semesterService.find(id);
         semesterService.deleteSemester(semester);

         if (semester.isValid() == false)
         {
            res.status(HTTP_BAD_REQUEST);
         }
         else
         {
            res.status(HTTP_OK_REQUEST);
         }

         res.redirect("/semesters");

         HashMap<String, Object> datas = new HashMap<String, Object>();
         datas.put("semesters", semesterService.findAll());

         return new ModelAndView(datas, SEMESTER_TEMPLATE);
      } , new ThymeleafTemplateEngine());

      // assign topics (using HTTP post method)
      post("/semester/assign/topic", (req, res) -> {

         LOGGER.debug("post-request to /semester/assign/topics .");
         Long id = Long.valueOf(req.queryParams("id"));

         Semester semester = SemesterController.semesterService.find(id);

         Set<Topic> restTopics = new HashSet<Topic>();
         Set<User> restUsers = new HashSet<User>();

         // get all topics and all users
         for (Topic topic : semester.getTopics())
         {
            if (topic.getUsersChoices().size() > 0)
            {
               restTopics.add(topic);
               restUsers.addAll(topic.getUsersChoices());
            }
         }

         // todo: now - only choosen topics will be assigned

         do
         {
            HashSet<Topic> topicsToRemove = new HashSet<Topic>();

            for (Topic topic : restTopics)
            {
               // topic is chose by one user.
               if (topic.getUsersChoices().size() == 1)
               {
                  User user = topic.getUsersChoices().get(0);
                  user.setTopic(topic);
                  UserController.userService.updateUser(user);

                  topicsToRemove.add(topic);
                  restUsers.remove(user);
               }
               else
               {
                  // add users that have no topic to restUsers
                  for (User user : topic.getUsersChoices())
                  {
                     if (user.getTopic() == null) restUsers.add(user);
                  }
               }
            }

            // remove all assigned topics from restTopics
            restTopics.removeAll(topicsToRemove);
            if (restUsers.size() == 0) break;

            //random topic
            Topic randomTopic = getRandomTopic(restTopics);
            // random user
            User randomUser = getRandomUser(randomTopic.getUsersChoices());
            // assign random user
            if (randomUser != null && randomUser.getTopic() == null)
            {
               randomTopic.addUser(randomUser);
               UserController.userService.updateUser(randomUser);

               restTopics.remove(randomTopic);
               restUsers.remove(randomUser);
            }
         }
         while (restUsers.size() > 0); // while all users have assigned one topic

         res.redirect("/users");

         HashMap<String, Object> datas = new HashMap<String, Object>();

         return new ModelAndView(datas, SEMESTER_TEMPLATE);
      } , new ThymeleafTemplateEngine());
   }

   private User getRandomUser(List<User> users)
   {
      int random = (int)Math.random() * users.size();
      return users.get(random);
   }

   private Topic getRandomTopic(Set<Topic> restTopics)
   {
      int random = (int)Math.random() * restTopics.size();
      ArrayList<Topic> arrayList = new ArrayList<Topic>();
      arrayList.addAll(restTopics);
      return arrayList.get(random);
   }
}