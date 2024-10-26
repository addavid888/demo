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
public class Course {
    private String id;
    private String name;
    private boolean type; 
    private String title;
    private LocalDate beginDate;
    private LocalDate endDate;
    private double tuitionFee;
    private String topicId;

    public Course(String id, String name, boolean type, String title, LocalDate beginDate, LocalDate endDate, double tuitionFee, String topicId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.title = title;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.tuitionFee = tuitionFee;
        this.topicId = topicId;
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

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(double tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s|", id));
        sb.append(String.format("%-20s|", name));
        sb.append(String.format("%-20s|", (type) ? "online" : "offline"));
        sb.append(String.format("%-20s|", title));
        sb.append(String.format("%-20s|", beginDate));
        sb.append(String.format("%-20s|", endDate));
        sb.append(String.format("%-20s|", tuitionFee));
        sb.append(String.format("%-20s|", topicId));
         
        
        
        return sb.toString();
    }
}
