package org.dinesh.solrSearch;


import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.dinesh.ResumeParser.Resume;

public class ResumeSolrSearch {

    public ResumeSolrSearch() {

    }

    public static void main(String args[]) throws SolrServerException,
	    MalformedURLException {
	SolrServer server = getSolrServer();
	SolrQuery query = new SolrQuery();
	query.setQuery("*:*");
	QueryResponse rsp = server.query(query);
	//SolrDocumentList docs = rsp.getResults();
	List<Resume> beans = rsp.getBeans(Resume.class);
	for (Iterator<Resume> iterator = beans.iterator(); iterator.hasNext();) {
	    Resume bean = iterator.next();
	    System.out.println("The ID is " + bean.getId());
	    System.out.println(bean.getDescription());
	}
	  // Print the name from the list....
        /*for(SolrDocument doc : docs) {
            System.out.println(doc.getId());
        }*/

    }

    public static SolrServer getSolrServer() throws MalformedURLException {
	return new HttpSolrServer("http://localhost:8983/solr");
    }
}
