package org.dinesh.ResumeParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

//import org.dinesh.ResumeModel.Resume;

public class ResumeParserDoc extends ResumeParserAbstract {

    private WordExtractor we;

    public ResumeParserDoc() {
	skillList.add("drupal");
	skillList.add("php");
	skillList.add("mysql");
	skillList.add("c++");
	skillList.add("sql");
	skillList.add("c");
	skillList.add("java");
    }

    public static void main(String[] args) throws IOException {

    }

    public static ResumeParserDoc getResumeParser() {
	// TODO Auto-generated method stub
	ResumeParserDoc resume = new ResumeParserDoc();
	// resume.getFilesFolder();
	// resume.listFilesForFolder(folder);
	return resume;
    }

    private void displayText(String fileName) throws FileNotFoundException,
	    IOException {
	String fileLocation = directoryLocation + fileName;
	POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
		fileLocation));
	HWPFDocument doc = new HWPFDocument(fs);
	WordExtractor we = new WordExtractor(doc);
	// log.info(we);
	// we.getText();
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

    @Override
    public List<String> getResumeSkills() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String[] getDocumentText() {
	if (we != null) {
	    String[] para = we.getParagraphText();
	    return para;
	}
	return null;
    }

    public boolean checkMimeType(String fileName) {
	String fileLocation = directoryLocation + fileName;
	POIFSFileSystem fs = null;
	try {
	    fs = new POIFSFileSystem(new FileInputStream(fileLocation));
	} catch (IOException e) {
	    e.printStackTrace();
	    return false;
	}
	HWPFDocument doc = null;
	try {
	    doc = new HWPFDocument(fs);
	} catch (IOException e) {
	    e.printStackTrace();
	    return false;
	}
	we = new WordExtractor(doc);
	return true;
    }


    @Override
    public String getFileMimeType(String fileName) {
	// TODO Auto-generated method stub
	return null;
    }

    public void clearAll() {
	this.emailList.clear();
	this.skillList.clear();
    }


}