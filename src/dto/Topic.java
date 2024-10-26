/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author maity
 */
public class Topic {
    private String id;
    private String name;
    private boolean type; // long-term or short-term
    private String title;
    private int duration; // in hours

    public Topic(String id, String name, boolean type, String title, int duration) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.title = title;
        this.duration = duration;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }



    @Override
    public String toString() {
           StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s|", id));
        sb.append(String.format("%-20s|", name));
        sb.append(String.format("%-20s|", (type) ? "Long-term" : "Short-term"));
        sb.append(String.format("%-20s|", title));
        sb.append(String.format("%-20s|", duration));
         
        
        
        return sb.toString();


    }

}
