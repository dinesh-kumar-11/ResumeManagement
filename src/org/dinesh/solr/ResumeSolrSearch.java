package org.dinesh.solr;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;

import org.dinesh.ResumeModel.Resume;

public class ResumeSolrSearch {

    private String searchString;

    private String searchSolrField = "description";

    private String hlSolrField = "description";

    public QueryResponse response = new QueryResponse();

    public ResumeSolrSearch(String searchString) {
	this.searchString = searchString;
    }

    public static ResumeSolrSearch getResumeSolrSearch(String searchString) {
	return new ResumeSolrSearch(searchString);
    }

    public String getSearchString() {
	return searchString;
    }

    public void setSearchString(String searchString) {
	this.searchString = searchString;
    }

    public QueryResponse searchSolr() throws SolrServerException,
	    MalformedURLException {
	SolrServer server = getSolrServer();
	SolrQuery query = new SolrQuery();
	query.setQuery(this.searchSolrField + ":" + this.searchString);
	query.setHighlight(true).setHighlightSnippets(3);
	query.setParam("hl.fl", this.hlSolrField);
	query.setHighlightSimplePost("</em>");
	query.setHighlightSimplePre("<em>");

	this.response = server.query(query);
	return this.response;
    }

    public String getThemedOutput() {
	List<String> highlightSnippets = null;
	String htmlOutput = "The results are </br>";
	// SolrDocumentList docs = rsp.getResults();

	/*
	 * Iterator<SolrDocument> iter = this.response.getResults().iterator();
	 * 
	 * while (iter.hasNext()) { SolrDocument resultDoc = iter.next(); String
	 * id = (String) resultDoc.getFieldValue("id"); // id is the //
	 * uniqueKey // field htmlOutput += "<div class='item'><h3>" + id +
	 * "</h3>"; if (this.response.getHighlighting().get(id) != null) {
	 * highlightSnippets = this.response.getHighlighting().get(id)
	 * .get("description"); } for (int i = 0; i < highlightSnippets.size();
	 * i++) { htmlOutput += "<span class='body'>" + highlightSnippets.get(i)
	 * + "</span></div>"; } }
	 */
	List<Resume> beans = this.response.getBeans(Resume.class);

	for (Iterator<Resume> iterator = beans.iterator(); iterator.hasNext();) {
	    Resume bean = iterator.next();
	    //htmlOutput += "<div class='item'><h3>" + bean.getId() + "</h3>"; //
	    System.out.println(bean.getDescription());
	    if (this.response.getHighlighting().get(bean.getId()) != null) {
		highlightSnippets = this.response.getHighlighting()
			.get(bean.getId()).get("description");
	    }
	    for (int i = 0; i < highlightSnippets.size(); i++)
		htmlOutput += "<div class='item'><span class='body'>" + highlightSnippets.get(i)
			+ "</span></div>";
	}

	return htmlOutput;
    }

    public String getThemeBody() {
	return null;
    }

    public static void main(String args[]) throws SolrServerException,
	    MalformedURLException {
	/*
	 * SolrServer server = getSolrServer(); SolrQuery query = new
	 * SolrQuery(); query.setQuery("description:php Analytical");
	 * query.setHighlight(true).setHighlightSnippets(1);
	 * query.setParam("hl.fl", "description");
	 * query.setHighlightSimplePost("</em>");
	 * query.setHighlightSimplePre("<em>");
	 * 
	 * QueryResponse rsp = server.query(query); List<String>
	 * highlightSnippets = null;
	 * 
	 * // SolrDocumentList docs = rsp.getResults(); List<Resume> beans =
	 * rsp.getBeans(Resume.class); for (Iterator<Resume> iterator =
	 * beans.iterator(); iterator.hasNext();) { Resume bean =
	 * iterator.next(); System.out.println("The ID is " + bean.getId()); //
	 * System.out.println(bean.getDescription()); if
	 * (rsp.getHighlighting().get(bean.getId()) != null) { highlightSnippets
	 * = rsp.getHighlighting().get(bean.getId()) .get("description"); } for
	 * (int i = 0; i < highlightSnippets.size(); i++)
	 * System.out.println("The highlisht is " + highlightSnippets.get(i)); }
	 */

	ResumeSolrSearch resumeSearchObj = ResumeSolrSearch
		.getResumeSolrSearch("php");
	resumeSearchObj.searchSolr();
	String output = resumeSearchObj.getThemedOutput();
	System.out.println("The out is " + output);
    }

    public static SolrServer getSolrServer() throws MalformedURLException {
	return new HttpSolrServer("http://localhost:8983/solr");
    }
}
