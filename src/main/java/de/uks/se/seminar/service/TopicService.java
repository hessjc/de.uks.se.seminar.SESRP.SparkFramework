/**
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 *
 * file description
 */
package de.uks.se.seminar.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import de.uks.se.seminar.persistence.Lecturer;
import de.uks.se.seminar.persistence.Semester;
import de.uks.se.seminar.persistence.Topic;
import de.uks.se.seminar.utils.HibernateUtil;

/**
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 *
 */
public class TopicService
{
   private static Session session = HibernateUtil.getSessionFactory().openSession();

   /**
    * @param id
    * @return Topic
    */
   public Topic find(Long id)
   {
      return (Topic)session.get(Topic.class, id);
   }

   /**
    * @param
    * @return list of Topics
    */
   @SuppressWarnings("unchecked")
   public List<Topic> findAll()
   {
      return session.createCriteria(Topic.class).list();
   }

   /**
    * @param title
    * @param description
    * @param topic
    * @param lecturer
    * @return
    */
   public Topic createTopic(String title, String description, Semester semester, Lecturer lecturer)
   {
      Topic topic = new Topic(title, description, semester, lecturer);
      save(topic);
      return topic;
   }

   /**
    * @param topic
    * @param title
    * @param description
    * @param semester
    * @param lecturer
    * @return
    */
   public Topic updateTopic(Topic topic, String title, String description, Semester semester, Lecturer lecturer)
   {
      topic.setTitle(title);
      topic.setDescription(description);
      topic.setSemester(semester);
      topic.setLecturer(lecturer);
      update(topic);
      return topic;
   }

   /**
    * @param topic
    * @return
    */
   public Topic updateTopic(Topic topic)
   {
      update(topic);
      return topic;
   }

   public void save(Topic topic)
   {
      Transaction beginTransaction = session.beginTransaction();
      session.save(topic);
      beginTransaction.commit();
   }

   public void update(Topic topic)
   {
      Transaction beginTransaction = session.beginTransaction();
      session.merge(topic);
      session.update(topic);
      beginTransaction.commit();
   }

   public void remove(Topic topic)
   {
      Transaction beginTransaction = session.beginTransaction();
      session.delete(topic);
      beginTransaction.commit();
   }
}