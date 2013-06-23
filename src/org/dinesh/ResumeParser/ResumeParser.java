package org.dinesh.ResumeParser;

import java.io.File;
import org.apache.commons.logging.*;
import org.apache.poi.hwpf.extractor.Word6Extractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.OldWordFileFormatException;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hwpf.extractor.WordExtractor;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResumeParser {

    public static final String RESUME_Dir_LOCATION = "/home/dinesh/Documents/resumes/";

    public static ArrayList<String> skillList = new ArrayList<String>();

    private Log log = LogFactory.getLog(ResumeParser.class);

    public static ArrayList<String> emailList = new ArrayList<String>();

    public ResumeParser() {
	skillList.add("drupal");
	skillList.add("php");
	skillList.add("mysql");
	skillList.add("c++");
	skillList.add("sql");
	skillList.add("c");
	skillList.add("java");
    }

    public void listFilesForFolder(final File folder) {
	for (final File fileEntry : folder.listFiles()) {
	    if (fileEntry.isDirectory()) {
		listFilesForFolder(fileEntry);
	    } else {
		System.out.println(fileEntry.getName());
		try {
		    // log.info(fileEntry);
		    displayText(fileEntry.getName());
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	}
    }

    public static void main(String[] args) {
	ResumeParser resumes = new ResumeParser();
	final File folder = new File(RESUME_Dir_LOCATION);
	resumes.listFilesForFolder(folder);
    }

    private void displayText(String fileName) throws FileNotFoundException,
	    IOException {
	String fileLocation = RESUME_Dir_LOCATION + fileName;
	POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
		fileLocation));
	HWPFDocument doc = new HWPFDocument(fs);
	WordExtractor we = new WordExtractor(doc);
	// log.info(we);
	String[] para = we.getParagraphText();
	System.out.println("Word Document has " + para.length + " paragraphs");
	for (int i = 0; i < para.length; i++) {
	    para[i] = para[i].trim();
	    getEmail(para[i]);
	    getSkills(para[i], i);
	    // System.out.println(para[i]);
	}
    }

    private void getSkills(String line, int lineNo) {
	for (Iterator<String> iterator = skillList.iterator(); iterator
		.hasNext();) {
	    String s = iterator.next();
	    if (Pattern.compile(Pattern.quote(line), Pattern.CASE_INSENSITIVE)
		    .matcher(s).find()) {
		System.out.println("Found " + s + " in line no. " + lineNo);
	    }
	}
    }

    private static void getEmail(String line) {
	final String RE_MAIL = "([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})";
	Pattern p = Pattern.compile(RE_MAIL);
	Matcher m = p.matcher(line);

	while (m.find()) {
	    if (!emailList.contains(m.group(1))) {
		System.out.println(m.group(1));
		emailList.add(m.group(1));
	    }
	}

    }

}