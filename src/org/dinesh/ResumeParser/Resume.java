/**
 * 
 */
package org.dinesh.ResumeParser;

import java.awt.List;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @author dinesh
 * 
 */
public class Resume {
    @Field
    String id;

    @Field
    String[] email;

    @Field
    String resumeText;
    
    @Field
    String description;
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Resume getResume() {
	return new Resume();
    }
    
    public void setEmail(String[] email) {
	this.email = email;
    }
    
    public void setResume(String resumeText) {
	this.resumeText = resumeText;
    }
    
    public void setId(String id) {
	this.id = id;
    }
    
    public String[] getEmail() {
	return this.email;
    }
    
    public String getResumeText() {
	return  this.resumeText;
    }
    
    public String getId() {
	return this.id;
    }
    

}
