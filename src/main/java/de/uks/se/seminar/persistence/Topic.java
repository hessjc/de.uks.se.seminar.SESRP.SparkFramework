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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * class description
 * 
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
@Entity(name = "TOPICS")
@Table(name = "TOPICS")
public class Topic
{
   @Id
   @GeneratedValue
   @Column(name = "TOPIC_ID", unique = true, nullable = false)
   private Long id;

   @Version
   private Long version;

   @NotNull
   private String title;

   private String description;

   @ManyToOne
   private Semester semester;

   @OneToOne
   private Lecturer lecturer;

   @OneToMany(mappedBy = "topic")
   private List<User> users;

   @ManyToMany(mappedBy = "choices")
   private List<User> usersChoices;

   /**
    * 
    */
   public Topic()
   {
      // TODO Auto-generated constructor stub
   }

   /**
    * 
    */
   public Topic(String title, String description, Semester semester, Lecturer lecturer)
   {
      setTitle(title);
      setDescription(description);
      setSemester(semester);
      setLecturer(lecturer);
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
    * @return the title
    */
   public String getTitle()
   {
      return title;
   }

   /**
    * @param title the title to set
    */
   public void setTitle(String title)
   {
      this.title = title;
   }

   /**
    * @return the description
    */
   public String getDescription()
   {
      return description;
   }

   /**
    * @param description the description to set
    */
   public void setDescription(String description)
   {
      this.description = description;
   }

   /**
    * @return the semester
    */
   public Semester getSemester()
   {
      return semester;
   }

   /**
    * @param semester the semester to set
    */
   public void setSemester(Semester semester)
   {
      if (this.semester == null || this.semester.equals(semester) == false)
      {
         this.semester = semester;
      }
      else
      {
         return;
      }
      semester.addTopic(this);
   }

   /**
    * @return the lecturer
    */
   public Lecturer getLecturer()
   {
      return lecturer;
   }

   /**
    * @param lecturer the lecturer to set
    */
   public void setLecturer(Lecturer lecturer)
   {
      if (this.lecturer == null || this.lecturer.equals(lecturer) == false)
      {
         this.lecturer = lecturer;
      }
      else
      {
         return;
      }
      lecturer.addTopic(this);
   }

   /**
    * @return the users
    */
   public List<User> getUsers()
   {
      if (this.users == null)
      {
         users = new ArrayList<User>();
      }
      return users;
   }

   /**
    * @param media the media to add
    */
   public void addUser(User user)
   {
      if (getUsers().contains(user) == false)
      {
         this.users.add(user);
      }
      else
      {
         return;
      }
      user.setTopic(this);
   }

   /**
    * @return the usersChoices
    */
   public List<User> getUsersChoices()
   {
      if (usersChoices == null)
      {
         usersChoices = new ArrayList<User>();
      }
      return usersChoices;
   }

   /**
    * @param users the users to set
    */
   public void setUsers(List<User> users)
   {
      this.users = users;
   }

   /**
    * @param user the user to set
    */
   public void addUsersChoices(User user)
   {
      if (usersChoices == null)
      {
         usersChoices = new ArrayList<User>();
      }
      usersChoices.add(user);
   }

   public boolean isValid()
   {
      return title != null && !title.isEmpty() && semester != null;
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
            ", title='" + title + '\'' +
            ", description'" + description + '\'' +
            '}';
   }
}