/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universityapp;
import java.util.*;
/**
 *
 * @author satranga
 */
public class Event {
    private String name;
    private String type;
    private String description;
    private String college;
    private Date eventDate;
    
    public Event(String name, String type, String description, String college, Date eventDate){
        this.name = name;
        this.type = type;
        this.description = description;
        this.college = college;
        this.eventDate = eventDate;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public String getType(){
        return type;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setCollege(String college){
        this.college = college;
    }
    
    public String getCollege(){
        return college;
    }
    
    public void setEventDate(Date eventDate){
        this.eventDate = eventDate;
    }
    
    public Date getEventDate(){
        return eventDate;
    }
    
    public void addToList(){

    }
}
