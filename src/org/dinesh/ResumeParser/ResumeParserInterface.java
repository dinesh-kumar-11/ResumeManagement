package org.dinesh.ResumeParser;

import java.util.ArrayList;
import java.util.List;

/**
 * A Interface for the resume parsers
 * 
 * @author dinesh.sarangapani
 * 
 */
public interface ResumeParserInterface {
    /**
     * A method to get the document text, Implementers can use the Apache Poi to
     * get the complete Document Text
     * 
     * @return String Text
     */
    public String[] getDocumentText();

    /**
     * A List of String Skills that can be set by any method but should be able
     * to return
     * 
     * @return List<String>
     */
    public List<String> getResumeSkills();

    /**
     * A Email List that is parsed from the document text
     * 
     * @return List<String>
     */
    public ArrayList<String> getEmail(String line);

    /**
     * A File Name of the document that is parsed
     * 
     * @return String
     */
    public String getFileName();

    /**
     * Get the file name
     * 
     * @return String
     */
    public String getDirectoryLocation();

    /**
     * Get the mime type
     * 
     * @param fileName
     * @return String
     */
    public String getFileMimeType(String fileName);
}
