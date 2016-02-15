/**
 * file description
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
package de.uks.se.seminar.persistence;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import de.uks.se.seminar.utils.MD5Util;

/**
 * class description.
 * 
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
@Entity(name = "LECTURERS")
@Table(name = "LECTURERS")
public class Lecturer
{
   @Id
   @GeneratedValue
   private Long id;

   @Version
   private Long version;

   @NotNull
   private String name;

   @Email
   @NotNull
   private String email;

   private String gravatar;

   @OneToMany(mappedBy = "lecturer")
   private Set<Topic> topics;

   /**
    * 
    */
   public Lecturer()
   {
      // TODO Auto-generated constructor stub
   }

   /**
    * 
    */
   public Lecturer(String name, String email)
   {
      setName(name);
      setEmail(email);
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
    * @param gravtar the gravtar to set
    */
   public void setGravatar(String gravatar)
   {
      this.gravatar = gravatar;
   }

   /**
    * @return the topics
    */
   public Set<Topic> getTopics()
   {
      if (this.topics == null)
      {
         this.topics = new LinkedHashSet<Topic>();
      }
      return topics;
   }

   /**
    * @param topics the topics to add
    */
   public void addTopic(Topic topic)
   {
      if (getTopics().contains(topic) == false)
      {
         this.topics.add(topic);
      }
      else
      {
         return;
      }
      topic.setLecturer(this);
   }

   public boolean isValid()
   {
      return name != null && !name.isEmpty() && email != null && !email.isEmpty();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return "Lecturer{" +
            "id=" + id +
            ", version=" + version +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
   }
}