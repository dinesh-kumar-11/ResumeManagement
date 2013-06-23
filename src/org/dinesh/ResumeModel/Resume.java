/**
 * 
 */
package org.dinesh.ResumeModel;

import java.awt.List;
import java.util.ArrayList;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @author dinesh
 * 
 */
public class Resume {
    @Field
    String id;

    @Field
    ArrayList<String> email_txt;

    @Field
    String description;

    ArrayList<String> skills;

    @Field
    ArrayList<String> skills_txt;

    public String getDescription() {
	return description;
    }
    
    public Resume() {
	System.out.println("Hash is " + hashCode());
    }

    public void setDescription(String[] description) {
	for (int i = 0; i < description.length; i++) {
	    description[i].trim();
	    this.description = this.description + description[i];
	}
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public static Resume getResume() {
	return new Resume();
    }

    public void setEmail(ArrayList<String> emailList) {
	this.email_txt = emailList;
	System.out.println("Hash is " + hashCode() + " and Email is " + this.email_txt);
	
    }

    public void setId(String string) {
	this.id = string;
    }

    public ArrayList<String> getEmail() {
	return this.email_txt;
    }

    public String getId() {
	return this.id;
    }

    public void setSkills(ArrayList<String> skillList) {
	this.skills = skillList;
	this.skills_txt = skillList;
    }

    public void clearAll() {
	this.skills = this.skills_txt = null;
    }

}
