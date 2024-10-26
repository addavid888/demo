/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.time.LocalDate;

/**
 *
 * @author maity
 */
public class Learner {
    
    private String id;
    private String name;
    private LocalDate dob; // date of birth
    private double score;
    private String courseId; // enrolled course
    
    public Learner(String id, String name, LocalDate dob, double score, String courseId) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.score = score;
        this.courseId = courseId;
    }

    public Learner(String id, String name, LocalDate dob, String courseId) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.courseId = courseId;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus(){
        return (score>=5?"Pass":"Fail");
    }
    

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
@Override
    public String toString() {
           StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s|", id));
        sb.append(String.format("%-20s|", name));
        sb.append(String.format("%-20s|", dob));
        sb.append(String.format("%-20s|", score));
        sb.append(String.format("%-20s|", courseId));
         
        
        
        return sb.toString();
     


    }
}
