/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universityapp;

/**
 *
 * @author satranga
 */
public class Prompt {
    private String prompt;
    private int wordCount;
    private String notes;
    private boolean completed;
    private College college;
    
    public Prompt(String prompt, int wordCount, String notes, boolean completed, College college){
        this.prompt = prompt;
        this.wordCount = wordCount;
        this.notes = notes;
        this.completed = completed;
        this.college = college;
    }
    
    public String getPrompt(){
        return prompt;
    }
    
    public void setPrompt(String prompt){
        this.prompt = prompt;
    }
    
    public int getWordCount(){
        return wordCount;
    }
    
    public void setWordCount(int wordCount){
        this.wordCount = wordCount;
    }
    
    public String getNotes(){
        return notes;
    }
    
    public void setNotes(String notes){
        this.notes = notes;
    }
    
    public boolean getCompleted(){
        return completed;
    }
    
    public void setCompleted(boolean completed){
        this.completed = completed;
    }
    
    public College getCollege(){
        return college;
    }
    
    public void setCollege(College college){
        this.college = college;
    }
}
