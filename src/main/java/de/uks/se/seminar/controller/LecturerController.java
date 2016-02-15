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
import de.uks.se.seminar.service.LecturerService;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

/**
 * LecturerController with lecturer dependent routes.
 * 
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
public class LecturerController extends AbstractHttpController
{
   private static final Logger LOGGER = LoggerFactory.getLogger(LecturerController.class);

   public static LecturerService lecturerService = new LecturerService();

   public LecturerController()
   {
      // show lecturer template to show lecturers (using HTTP get method)
      get("/lecturers", (req, res) -> {
         LOGGER.debug("get-request to /lecturers .");

         HashMap<String, Object> datas = new HashMap<String, Object>();
         datas.put("lecturers", lecturerService.findAll());

         return new ModelAndView(datas, LECTURER_TEMPLATE);
      } , new ThymeleafTemplateEngine());

      // insert a lecturer (using HTTP post method)
      post("/lecturer/create", (req, res) -> {
         LOGGER.debug("post-request to /lecturer/create with parameters name={" + req.queryParams("name")
               + "}, email={" + req.queryParams("email") + ".");

         String name = req.queryParams("name");
         String email = req.queryParams("email");

         lecturerService.createLecturer(name, email);

         HashMap<String, Object> datas = new HashMap<String, Object>();
         datas.put("lecturers", lecturerService.findAll());

         return new ModelAndView(datas, LECTURER_TEMPLATE);
      } , new ThymeleafTemplateEngine());

      // show lecturer.edit template to edit a lecturer (using HTTP get method)
      get("/lecturer/edit", (req, res) -> {
         LOGGER.debug("get-request to /lecturer/edit with parameters id={" + req.queryParams("id") + "}.");

         Long lecturer_id = Long.valueOf(req.queryParams("id"));

         Lecturer lecturer = lecturerService.find(lecturer_id);

         HashMap<String, Object> datas = new HashMap<String, Object>();
         datas.put("lecturer", lecturer);

         return new ModelAndView(datas, LECTURER_EDIT_TEMPLATE);
      } , new ThymeleafTemplateEngine());

      // edit a lecturer (using HTTP post method)
      post("/lecturer/edit", (req, res) -> {
         LOGGER.debug("post-request to /lecturer/edit with parameters id={" + req.queryParams("id") + ", name={"
               + req.queryParams("name")
               + "}, email={" + req.queryParams("email") + "}.");

         Long id = Long.valueOf(req.queryParams("id"));
         String name = req.queryParams("name");
         String email = req.queryParams("email");

         Lecturer lecturer = lecturerService.find(id);
         lecturerService.updateLecturer(lecturer, name, email);

         HashMap<String, Object> datas = new HashMap<String, Object>();
         datas.put("lecturers", lecturerService.findAll());
         res.redirect("/lecturers");

         return new ModelAndView(datas, LECTURER_TEMPLATE);
      } , new ThymeleafTemplateEngine());

      // delete a lecturer (using HTTP post method)
      post("/lecturer/delete", (req, res) -> {
         LOGGER.debug("post-request to /lecturer/delete with parameters id={" + req.queryParams("id") + "}.");

         Long id = Long.valueOf(req.queryParams("id"));

         Lecturer lecturer = lecturerService.find(id);
         lecturerService.deleteLecturer(lecturer);

         HashMap<String, Object> datas = new HashMap<String, Object>();
         datas.put("lecturers", lecturerService.findAll());
         res.redirect("/lecturers");

         return new ModelAndView(datas, LECTURER_TEMPLATE);
      } , new ThymeleafTemplateEngine());
   }
}