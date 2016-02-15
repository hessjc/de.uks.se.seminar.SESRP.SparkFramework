/**
 * file description
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
package de.uks.se.seminar.controller;

/**
 * abstract HttpController class with template constants and http status codes.
 * 
 * @author Jan-Christopher Hess <jan-chr.hess@student.uni-kassel.de>
 */
public class AbstractHttpController
{
   protected static final int HTTP_OK_REQUEST = 200;
   protected static final int HTTP_BAD_REQUEST = 400;

   protected static final String INDEX_TEMPLATE = "index";
   protected static final String REGISTRATION_TEMPLATE = "registration";
   protected static final String SEMESTER_TEMPLATE = "semester";
   protected static final String SEMESTER_EDIT_TEMPLATE = "semester.edit";
   protected static final String LECTURER_TEMPLATE = "lecturer";
   protected static final String LECTURER_EDIT_TEMPLATE = "lecturer.edit";
   protected static final String TOPIC_TEMPLATE = "topic";
   protected static final String TOPIC_EDIT_TEMPLATE = "topic.edit";
   protected static final String USER_TEMPLATE = "user";
   protected static final String USER_EDIT_TEMPLATE = "user.edit";
}