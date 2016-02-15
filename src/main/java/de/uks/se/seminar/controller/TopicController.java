/**
 * file description
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
package de.uks.se.seminar.controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.uks.se.seminar.persistence.Lecturer;
import de.uks.se.seminar.persistence.Semester;
import de.uks.se.seminar.persistence.Topic;
import de.uks.se.seminar.service.TopicService;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

/**
 * TopicController with topic dependent routes.
 * 
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
public class TopicController extends AbstractHttpController
{
   private static final Logger LOGGER = LoggerFactory.getLogger(TopicController.class);

   public static TopicService topicService = new TopicService();

   public TopicController()
   {
      // show topic template to show topics (using HTTP get method)
      get("/topics", (req, res) -> {

         LOGGER.debug("get-request to /topics .");
         HashMap<String, Object> datas = new HashMap<String, Object>();
         datas.put("topics", topicService.findAll());
         datas.put("semesters", SemesterController.semesterService.findAll());
         datas.put("lecturers", LecturerController.lecturerService.findAll());

         return new ModelAndView(datas, TOPIC_TEMPLATE);
      } , new ThymeleafTemplateEngine());

      // insert a semester (using HTTP post method)
      post("/topic/create", (req, res) -> {

         LOGGER.debug("post-request to /topic/create with parameters title={" + req.queryParams("title")
               + "}, description={" + req.queryParams("description") + ", description={"
               + req.queryParams("description") + ", semester_id={" + req.queryParams("semester") + ", lecturer_id={"
               + req.queryParams("lecturer") + ".");
         String title = req.queryParams("title");
         String description = req.queryParams("description");
         String semester_id = req.queryParams("semester");
         String lecturer_id = req.queryParams("lecturer");

         Semester semester = SemesterController.semesterService.find(Long.valueOf(semester_id));
         Lecturer lecturer = LecturerController.lecturerService.find(Long.valueOf(lecturer_id));

         topicService.createTopic(title, description, semester, lecturer);

         if (!semester.isValid())
         {
            res.status(HTTP_BAD_REQUEST);
         }
         else
         {
            res.status(HTTP_OK_REQUEST);
            res.redirect("/topics");
         }

         HashMap<String, Object> datas = new HashMap<String, Object>();
         datas.put("topics", topicService.findAll());

         return new ModelAndView(datas, TOPIC_TEMPLATE);
      } , new ThymeleafTemplateEngine());

      // show topic.edit template to edit a topic (using HTTP post method)
      get("/topic/edit", (req, res) -> {

         LOGGER.debug("get-request to /topic/edit .");
         Long topic_id = Long.valueOf(req.queryParams("id"));
         Topic topic = topicService.find(topic_id);

         HashMap<String, Object> datas = new HashMap<String, Object>();
         datas.put("topic", topic);
         datas.put("lecturers", LecturerController.lecturerService.findAll());
         datas.put("semesters", SemesterController.semesterService.findAll());

         return new ModelAndView(datas, TOPIC_EDIT_TEMPLATE);
      } , new ThymeleafTemplateEngine());

      // edit a topic (using HTTP post method)
      post("/topic/edit", (req, res) -> {

         LOGGER.debug("post-request to /topic/edit with parameters id={" + req.queryParams("id") + ", title={"
               + req.queryParams("title")
               + "}, description={" + req.queryParams("description") + ", description={"
               + req.queryParams("description") + ", semester_id={" + req.queryParams("semester") + ", lecturer_id={"
               + req.queryParams("lecturer") + ".");
         Long id = Long.valueOf(req.queryParams("id"));
         String title = req.queryParams("title");
         String description = req.queryParams("description");
         String semester_id = req.queryParams("semester");
         String lecturer_id = req.queryParams("lecturer");

         Semester semester = SemesterController.semesterService.find(Long.valueOf(semester_id));
         Lecturer lecturer = LecturerController.lecturerService.find(Long.valueOf(lecturer_id));

         Topic topic = topicService.find(id);
         topicService.updateTopic(topic, title, description, semester, lecturer);

         HashMap<String, Object> datas = new HashMap<String, Object>();
         datas.put("topics", topicService.findAll());
         res.redirect("/topics");
         
         return new ModelAndView(datas, TOPIC_TEMPLATE);
      } , new ThymeleafTemplateEngine());
   }
}