/**
 * file description
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
package de.uks.se.seminar.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import de.uks.se.seminar.utils.MD5Util;

/**
 * class description
 * 
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
@Entity(name = "USERS")
@Table(name = "USERS")
public class User
{
   @Id
   @GeneratedValue
   @Column(name = "USER_ID", unique = true, nullable = false)
   private Long id;

   @Version
   private Long version;

   @NotNull
   private String name;

   @NotNull
   @Email
   private String email;

   private String gravatar;

   @ManyToOne
   private Topic topic;

   @ManyToMany
   @JoinTable(name = "USER_TOPIC", joinColumns = { @JoinColumn(name = "TOPIC_ID") }, inverseJoinColumns = {
         @JoinColumn(name = "USER_ID") })
   private List<Topic> choices;

   /**
    * 
    */
   public User()
   {
      // TODO Auto-generated constructor stub
   }

   /**
    * @param name
    * @param email
    * @param choices
    * 
    */
   public User(String name, String email, List<Topic> choices)
   {
      setName(name);
      setEmail(email);
      setChoices(choices);
      setGravatar(MD5Util.md5Hex(email));
   }

   /**
    * @return the id
    */
   public Long getId()
   {
      return id;
   }

   /**
    * @return the version
    */
   public Long getVersion()
   {
      return version;
   }

   /**
    * @return the name
    */
   public String getName()
   {
      return name;
   }

   /**
    * @param name the name to set
    */
   public void setName(String name)
   {
      this.name = name;
   }

   /**
    * @return the email
    */
   public String getEmail()
   {
      return email;
   }

   /**
    * @param email the email to set
    */
   public void setEmail(String email)
   {
      this.email = email;
      setGravatar(MD5Util.md5Hex(email));
   }

   /**
    * @return the gravatar
    */
   public String getGravatar()
   {
      return gravatar;
   }

   /**
    * @param gravatar the gravatar to set
    */
   public void setGravatar(String gravatar)
   {
      this.gravatar = gravatar;
   }

   /**
    * @return the topic
    */
   public Topic getTopic()
   {
      return topic;
   }

   /**
    * @param topic the topic to set
    */
   public void setTopic(Topic topic)
   {
      if (this.topic == null || this.topic.equals(topic) == false)
      {
         this.topic = topic;
      }
      else
      {
         return;
      }
      topic.addUser(this);
   }

   /**
    * @return the choices
    */
   public List<Topic> getChoices()
   {
      if (choices == null)
      {
         choices = new ArrayList<Topic>();
      }
      return choices;
   }

   /**
    * @param choices the choices to set
    */
   public void setChoices(List<Topic> choices)
   {
      this.choices = choices;
      for (Topic topic : choices)
      {
         topic.addUsersChoices(this);
      }
   }

   public boolean isValid()
   {
      return email != null && !email.isEmpty() && name != null & !name.isEmpty();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return "User{" +
            "id=" + id +
            ", version=" + version +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
   }
}