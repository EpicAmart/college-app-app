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
public class College {
    private String name;
    private Date deadline;
    private String classification;
    private float progress;
    private String status;
    
    public College(String name, Date deadline, String classification, float progress, String status){
        this.name = name;
        this.deadline = deadline;
        this.classification = classification;
        this.progress = progress;
        this.status = status;
    }
    
    public String getCollegeName(){
        return name;
    }
    
    public void setCollegeName(String name){
        this.name = name;
    }
    
    public Date getDeadline(){
        return deadline;
    }
    
    public void setDeadline(Date deadline){
        this.deadline = deadline;
    }
    
    public String getClassification(){
        return classification;
    }
    
    public void setClassification(String classification){
        this.classification = classification;
    }
    
    public float getProgress(){
        return progress;
    }
    
    public void setProgress(float progress){
        this.progress = progress;
    }
    
    public String getStatus(){
        return status;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    
}
    
