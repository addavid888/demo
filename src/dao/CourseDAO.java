/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dto.Course;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maity
 */

public interface CourseDAO  {
    public final String COURSE_FILE_NAME = "COURSE.txt";

    public static List<Course> courses = new ArrayList<>();
    
  
    

   
}