/**
 * file description
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
package de.uks.se.seminar.persistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * class description.
 * 
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
@Entity(name = "SEMESTERS")
@Table(name = "SEMESTERS")
public class Semester
{
   @Id
   @GeneratedValue
   private Long id;

   @Version
   private Long version;

   @NotNull
   private String name;

   @NotNull
   private Date expiryDate;

   @OneToMany(mappedBy = "semester")
   private List<Topic> topics;

   /**
    * 
    */
   public Semester()
   {
      // TODO Auto-generated constructor stub
   }

   /**
    * @param title
    * @param expiryDate
    */
   public Semester(String title, Date expiryDate)
   {
      setName(title);
      setExpiryDate(expiryDate);
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
    * @return the expiryDate
    */
   public String getExpiryDate()
   {
      return new SimpleDateFormat("dd. MMMM yyyy").format(expiryDate);
   }

   /**
    * @param expiryDate the expiryDate to set
    */
   public void setExpiryDate(Date expiryDate)
   {
      this.expiryDate = expiryDate;
   }

   /**
    * @return the topics
    */
   public List<Topic> getTopics()
   {
      if (this.topics == null)
      {
         this.topics = new ArrayList<Topic>();
      }
      return topics;
   }

   /**
    * @param topic
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
      topic.setSemester(this);
   }

   /**
    * @param topic
    */
   public void removeTopic(Topic topic)
   {
      if (getTopics().contains(topic) == true)
      {
         this.topics.remove(topic);
      }
      else
      {
         return;
      }
      topic.setSemester(null);
   }

   public boolean isValid()
   {
      return name != null && !name.isEmpty() && expiryDate != null;
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return "Semester{" +
            "id=" + id +
            ", version=" + version +
            ", name='" + name + '\'' +
            ", expiryDate='" + new SimpleDateFormat("dd. MMMM yyyy").format(expiryDate) + '\'' +
            '}';
   }
}