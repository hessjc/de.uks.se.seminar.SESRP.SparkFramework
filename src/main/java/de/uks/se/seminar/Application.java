/**
 * file description
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
package de.uks.se.seminar;

import static spark.Spark.after;
import static spark.Spark.awaitInitialization;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.uks.se.seminar.controller.ApplicationController;
import de.uks.se.seminar.controller.LecturerController;
import de.uks.se.seminar.controller.SemesterController;
import de.uks.se.seminar.controller.TopicController;
import de.uks.se.seminar.controller.UserController;
import de.uks.se.seminar.persistence.Lecturer;
import de.uks.se.seminar.persistence.Semester;
import de.uks.se.seminar.persistence.Topic;

/**
 * starting class with main()-method for spark framework.
 * 
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
public class Application
{
   private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

   public static ApplicationController applicationController;
   public static SemesterController semesterController;
   public static LecturerController lecturerController;
   public static TopicController topicController;
   public static UserController userController;

   public static void main(String[] args)
   {
      // static files are located in /public folder
      staticFileLocation("public");

      // heroku port
      port(getHerokuAssignedPort());

      //initialize necessary controllers
      applicationController = new ApplicationController();
      semesterController = new SemesterController();
      lecturerController = new LecturerController();
      topicController = new TopicController();
      userController = new UserController();

      LOGGER.debug("Controller initalised.");

      // enable gzip
      after((request, response) -> {
         //response.header("Content-Encoding", "gzip");
      });

      // initialize an exemplary datamodel for development mode
      if (args.length > 0 && args[0].equals("dev"))
      {
         try
         {
            Semester semesterSS16 = SemesterController.semesterService.createSemester("SS16",
                  new SimpleDateFormat("dd/MM/yyyy").parse("14/05/2016"));

            Lecturer tobias = LecturerController.lecturerService.createLecturer("Tobias George",
                  "george@uni-kassel.de");
            Lecturer lennert = LecturerController.lecturerService.createLecturer("Lennert Raesch",
                  "lra@cs.uni-kassel.de");
            Lecturer marcel = LecturerController.lecturerService.createLecturer("Marcel Hahn",
                  "hahn@cs.uni-kassel.de");
            Lecturer stefan = LecturerController.lecturerService.createLecturer("Stefan Lindel",
                  "stefan.lindel@cs.uni-kassel.de");

            Topic ninja = TopicController.topicService.createTopic("Ninja",
                  "Ninja is a full stack web framework for Java. Rock solid, fast and super productive.", semesterSS16,
                  tobias);
            Topic jodd = TopicController.topicService.createTopic("Jodd",
                  "The Unbearable Lightness of Java.", semesterSS16, tobias);
            Topic jobby = TopicController.topicService.createTopic("Jobby",
                  "Jooby a micro web framework for Java 8 or higher.", semesterSS16, tobias);
            Topic spark = TopicController.topicService.createTopic("Spark",
                  "Spark - A tiny Sinatra inspired framework for creating web applications in Java 8 with minimal effort.",
                  semesterSS16, tobias);

            TopicController.topicService.createTopic("GameMaker Studio",
                  "GameMaker Studio Beschreibung.", semesterSS16, lennert);
            TopicController.topicService.createTopic("Metriken für Immersion",
                  "Metriken für Immersion Beschreibung.",
                  semesterSS16, lennert);

            TopicController.topicService.createTopic(
                  "Verteilte Graphtransformationen mit Apache Spark",
                  "Verteilte Graphtransformationen mit Apache Spark.", semesterSS16, marcel);
            TopicController.topicService.createTopic("k-Means Clustering von Wetterdaten mit Mahout",
                  "k-Means Clustering von Wetterdaten mit Mahout.", semesterSS16, marcel);

            TopicController.topicService.createTopic("Bazel",
                  "Bazel is Google’s own build tool ..[].. built-in support for building both client and server software, including client applications for both Android and iOS platforms.",
                  semesterSS16, stefan);

            List<Topic> topics = new LinkedList<Topic>();
            topics.add(spark);
            topics.add(ninja);
            UserController.userService.createUser("Jan", "jan-chr.hess@student.uni-kassel.de", topics);

            topics = new LinkedList<Topic>();
            topics.add(spark);
            topics.add(jobby);
            topics.add(ninja);
            UserController.userService.createUser("Gemeiner Student", "gemeiner.student@student.uni-kassel.de", topics);

            topics = new LinkedList<Topic>();
            topics.add(jobby);
            topics.add(jodd);
            topics.add(ninja);
            UserController.userService.createUser("Gemeiner Student2", "gemeiner.student2@student.uni-kassel.de",
                  topics);

            LOGGER.debug("Exemplary Datamodel for Development Mode initalised.");
         }
         catch (ParseException e)
         {
            LOGGER.error("Error while Controller Initialisation: e=" + e.getMessage());
         }
      }
      // wait for server to be initialized
      awaitInitialization();
   }

   static int getHerokuAssignedPort()
   {
      ProcessBuilder processBuilder = new ProcessBuilder();
      if (processBuilder.environment().get("PORT") != null)
      {
         return Integer.parseInt(processBuilder.environment().get("PORT"));
      }
      return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
   }
}