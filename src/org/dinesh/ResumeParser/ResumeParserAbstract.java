package org.dinesh.ResumeParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import org.dinesh.ResumeModel.Resume;

public abstract class ResumeParserAbstract implements ResumeParserInterface {

    protected static String directoryLocation = null;

    protected ArrayList<String> skillList = new ArrayList<String>();

    protected ArrayList<String> emailList = new ArrayList<String>();

    protected static File folder = null;

    protected String fileName;

    public abstract String[] getDocumentText();

    public ArrayList<String> getResumeSkills(String line) {
	ArrayList<String> skills = new ArrayList<String>();
	for (Iterator<String> iterator = skillList.iterator(); iterator
		.hasNext();) {
	    String skill = iterator.next();
	    if (Pattern.compile(Pattern.quote(line), Pattern.CASE_INSENSITIVE)
		    .matcher(skill).find()) {
		// System.out.println("Found " + skill + " in line no. ");
		if (!skills.contains(skill)) {
		    skills.add(skill);
		}
	    }
	}
	return skills;
    }

    public ArrayList<String> getResumeSkills(String[] wordText) {
	ArrayList<String> skills = new ArrayList<String>();
	for (Iterator<String> iterator = skillList.iterator(); iterator
		.hasNext();) {
	    String skill = iterator.next();
	    System.out.println(wordText);
	    for (int i = 0; i < wordText.length; i++) {
		wordText[i] = wordText[i].trim();
		if (Pattern
			.compile(Pattern.quote(wordText[i]),
				Pattern.CASE_INSENSITIVE).matcher(skill).find()) {
		    System.out.println("Found " + skill + " in line no. ");
		    if (!skills.contains(skill)) {
			skills.add(skill);
		    }
		}
	    }

	}
	return skills;
    }

    public File getFilesFolder() {
	if (folder == null) {
	    createFilesFolder();
	}
	return this.folder;
    }

    public void createFilesFolder() {
	File folder = new File(directoryLocation);
	this.folder = folder;
    }

    /**
     * A method which extracts the email address from the given string
     * 
     * @param line
     * @return arrayList of Email address
     */
    public ArrayList<String> getEmail(String line) {
	final String RE_MAIL = "([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})";
	Pattern p = Pattern.compile(RE_MAIL);
	Matcher m = p.matcher(line);

	while (m.find()) {
	    if (!this.emailList.contains(m.group(1))) {
		// System.out.println(m.group(1));
		this.emailList.add(m.group(1));
	    }
	}
	return this.emailList;
    }

    /**
     * A method which extracts the email address from the given string
     * 
     * @param line
     * @return arrayList of Email address
     */
    public ArrayList<String> getEmail(String[] wordText) {
	final String RE_MAIL = "([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})";
	Pattern p = Pattern.compile(RE_MAIL);

	// System.out.println(wordText);
	for (int i = 0; i < wordText.length; i++) {
	    wordText[i] = wordText[i].trim();
	    Matcher m = p.matcher(wordText[i]);
	    while (m.find()) {
		if (!this.emailList.contains(m.group(1))) {
		    // System.out.println(m.group(1));
		    this.emailList.add(m.group(1));
		}
	    }
	}

	return this.emailList;
    }

    /*
     * public List<Resume> getResumeDetails() throws IOException { final File
     * resumeDir = new File(ResumeParserAbstract.directoryLocation); //
     * listFilesForFolder(resumeDir); return null; }
     */

    public String getDirectoryLocation() {
	return ResumeParserAbstract.directoryLocation;
    }

    public static void setDirectoryLocation(String directoryLocation) {
	ResumeParserAbstract.directoryLocation = directoryLocation;
    }

    public String[] parseFile() throws IOException {
	String fileName = getFileFromFolder(this.getFilesFolder());
	if(fileName == null) {
	    return null;
	}
	setFileName(fileName);
	if (!checkMimeType(fileName)) {
	    return null;
	}
	String[] documentText = getDocumentText();
	return documentText;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public abstract boolean checkMimeType(String fileName);

    public String getFileFromFolder(File folder) throws IOException {
	if (folder == null) {
	    folder = this.getFilesFolder();
	}
	String fileName = null;
	for (final File fileEntry : folder.listFiles()) {
	    if (fileEntry.isDirectory()) {
		getFileFromFolder(fileEntry);
	    } else {
		fileName = fileEntry.getName();
		// System.out.println(fileName);
		// getDocumentText(fileEntry.getName());
		return fileName;
	    }
	}
	return null;
    }


    public void moveParsedFile(String resumeDirLocationParsed) {
	String exactLocation = this.getDirectoryLocation() + this.getFileName();
	File tempFile = new File(exactLocation);
	if(tempFile.renameTo(new File (resumeDirLocationParsed + this.getFileName()))) {
	    System.out.println("File is moved successful!");
	}
    }

}
