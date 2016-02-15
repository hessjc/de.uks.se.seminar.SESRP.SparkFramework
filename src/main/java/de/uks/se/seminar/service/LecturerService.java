/**
 * file description
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
package de.uks.se.seminar.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import de.uks.se.seminar.persistence.Lecturer;
import de.uks.se.seminar.utils.HibernateUtil;

/**
 * class description.
 * 
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
public class LecturerService
{
   private static Session session = HibernateUtil.getSessionFactory().openSession();

   /**
    * @param id
    * @return semester
    */
   public Lecturer find(Long id)
   {
      return (Lecturer)session.get(Lecturer.class, id);
   }

   /**
    * @param
    * @return list of lecturers
    */
   @SuppressWarnings("unchecked")
   public List<Lecturer> findAll()
   {
      return session.createCriteria(Lecturer.class).list();
   }

   /**
    * @param name
    * @param email
    * @return
    */
   public Lecturer createLecturer(String name, String email)
   {
      Lecturer lecturer = new Lecturer(name, email);
      save(lecturer);
      return lecturer;
   }

   /**
    * @param lecturer
    * @return
    */
   public Lecturer deleteLecturer(Lecturer lecturer)
   {
      remove(lecturer);
      return lecturer;
   }

   /**
    * @param semester
    * @param name
    * @param email
    * @return
    */
   public Lecturer updateLecturer(Lecturer lecturer, String name, String email)
   {
      lecturer.setName(name);
      lecturer.setEmail(email);
      update(lecturer);
      return lecturer;
   }

   public void save(Lecturer lecturer)
   {
      Transaction beginTransaction = session.beginTransaction();
      session.save(lecturer);
      beginTransaction.commit();
   }

   public void update(Lecturer lecturer)
   {
      Transaction beginTransaction = session.beginTransaction();
      session.update(lecturer);
      beginTransaction.commit();
   }

   public void remove(Lecturer lecturer)
   {
      Transaction beginTransaction = session.beginTransaction();
      session.delete(lecturer);
      beginTransaction.commit();
   }
}