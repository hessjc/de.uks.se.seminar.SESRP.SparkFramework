/**
 * file description
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
package de.uks.se.seminar.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import de.uks.se.seminar.persistence.Semester;
import de.uks.se.seminar.utils.HibernateUtil;

/**
 * class description
 * 
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
public class SemesterService
{
   private static Session session = HibernateUtil.getSessionFactory().openSession();

   /**
    * @param id
    * @return semester
    */
   public Semester find(Long id)
   {
      return (Semester)session.get(Semester.class, id);
   }

   /**
    * @param
    * @return list of semesters
    */
   @SuppressWarnings("unchecked")
   public List<Semester> findAll()
   {
      return session.createCriteria(Semester.class).addOrder(Order.asc("expiryDate")).list();
   }

   @SuppressWarnings("unchecked")
   public Semester lastSemester()
   {
      List<Semester> semesters = session.createCriteria(Semester.class).addOrder(Order.asc("expiryDate")).list();
      return (Semester)semesters.get(semesters.size() - 1);
   }

   /**
    * @param name
    * @param expiryDate
    * @return
    */
   public Semester createSemester(String name, Date expiryDate)
   {
      Semester semester = new Semester(name, expiryDate);
      save(semester);
      return semester;
   }

   /**
    * @param semester
    * @return
    */
   public Semester deleteSemester(Semester semester)
   {
      remove(semester);
      return semester;
   }

   /**
    * @param semester
    * @param name
    * @param expiryDate
    * @return
    */
   public Semester updateSemester(Semester semester, String name, Date expiryDate)
   {
      semester.setName(name);
      semester.setExpiryDate(expiryDate);
      update(semester);
      return semester;
   }

   public void save(Semester semester)
   {
      Transaction beginTransaction = session.beginTransaction();
      session.save(semester);
      beginTransaction.commit();
   }

   public void update(Semester semester)
   {
      Transaction beginTransaction = session.beginTransaction();
      session.update(semester);
      beginTransaction.commit();
   }

   public void remove(Semester semester)
   {
      Transaction beginTransaction = session.beginTransaction();
      session.delete(semester);
      beginTransaction.commit();
   }
}