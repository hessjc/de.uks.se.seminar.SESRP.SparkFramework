/**
 * file description
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
package de.uks.se.seminar.service;

import java.util.List;

import org.hibernate.Session;

import de.uks.se.seminar.persistence.Topic;
import de.uks.se.seminar.persistence.User;
import de.uks.se.seminar.utils.HibernateUtil;

/**
 * UserService class for user services.
 * 
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
public class UserService
{
   private static Session session = HibernateUtil.getSessionFactory().openSession();

   /**
    * @param id
    * @return user
    */
   public User find(Long id)
   {
      return (User)session.get(User.class, id);
   }

   /**
    * @param
    * @return list of users
    */
   @SuppressWarnings("unchecked")
   public List<User> findAll()
   {
      return session.createCriteria(User.class).list();
   }

   /**
    * @param name
    * @param email
    * @param topics
    * @return
    */
   public User createUser(String name, String email, List<Topic> topics)
   {
      User user = new User(name, email, topics);
      save(user);
      return user;
   }

   public User updateUser(User user)
   {
      update(user);
      return user;
   }

   public void save(User user)
   {
      session.beginTransaction();
      session.save(user);
      session.getTransaction().commit();
   }

   public void update(User user)
   {
      session.beginTransaction();
      session.update(user);
      session.getTransaction().commit();
   }

   public void remove(User user)
   {
      session.beginTransaction();
      session.delete(user);
      session.getTransaction().commit();
   }
}