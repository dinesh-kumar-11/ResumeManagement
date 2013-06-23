package org.dinesh.ResumeParser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.dinesh.ResumeModel.Resume;
import org.dinesh.ResumeModel.ResumeMysqlDao;
import org.dinesh.solr.ResumeSolrDocument;

/**
 * @author dinesh
 * 
 */
public class ResumeParserController {

    public static final String RESUME_Dir_LOCATION = "/home/dinesh/Documents/resumes/";
    public static final String RESUME_Dir_LOCATION_PARSED = "/home/dinesh/Documents/resumes_processed/";

    /**
     * @param args
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws IOException
     * @throws SolrServerException
     */
    public static void main(String[] args) throws ClassNotFoundException,
	    InstantiationException, IllegalAccessException, IOException,
	    SolrServerException {
	Class parserClass = Class
		.forName("org.dinesh.ResumeParser.ResumeParserDoc");
	ResumeParserDoc parser = (ResumeParserDoc) parserClass.newInstance();
	parser.setDirectoryLocation(RESUME_Dir_LOCATION);

	ResumeSolrDocument resumeSolrObj = ResumeSolrDocument
		.getResumeSolrDocument();

	ResumeMysqlDao resume = null;
	// Get the Resume DAO Model
	// Bi-directionally save in DB as well as Collect it for Solr in
	// Batch
	List<Resume> resumeBeans =  new ArrayList<Resume>();

	try {
	    // System.out.println("Done!" + parser.getFileName());
	    resume = ResumeMysqlDao.getResumeDao();
	    int i = 100;

	    while (i >= 0) {
		Resume resumeObject = Resume.getResume();
		ArrayList<String> emailList = new ArrayList<String>();
		ArrayList<String> skillList = new ArrayList<String>();
		// Parse the document and get the required
		String[] wordText = parser.parseFile();
		if (wordText == null) {
		    System.out.println("No Files to process!!");
		    break;
		}
		emailList.clear();
		skillList.clear();
		parser.clearAll();
		emailList = parser.getEmail(wordText);
		skillList = parser.getResumeSkills(wordText);

		try {
		    resume.startTransaction();
		    int lastid = resume.insertResume(parser.getFileName());
		    resumeObject.setId("Resume/" + lastid);
		    System.out.println("Done!" + lastid);
		    resume.insertResumeEmail(emailList, lastid);
		    resume.endTrasaction();

		} catch (SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

		// move files to other directory
		parser.moveParsedFile(RESUME_Dir_LOCATION_PARSED);

		System.out.println(skillList);
		System.out.println(emailList);

		resumeObject.setEmail(emailList);
		resumeObject.setSkills(skillList);
		resumeObject.setDescription(wordText);

		//resumeSolrObj.addResumeBean(resumeObject);
		resumeBeans.add(resumeObject);
		
	    }
	} finally {
	    resume.closeConnection();
	}

	resumeSolrObj.addResumeToServer(resumeBeans);
	//resumeSolrObj.addResumeToServer();
	resumeSolrObj.commitResume();

	System.out.println("Done all");
    }

}
