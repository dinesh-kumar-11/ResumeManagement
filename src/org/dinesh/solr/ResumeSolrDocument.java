package org.dinesh.solr;

import java.io.IOException;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import org.dinesh.ResumeModel.Resume;

public class ResumeSolrDocument {

    private SolrServer server;

    public List<Resume> resumeBeans = new ArrayList<Resume>();

    public static SolrServer getSolrServer() throws MalformedURLException {
	return new HttpSolrServer("http://localhost:8983/solr");
    }

    public static ResumeSolrDocument getResumeSolrDocument()
	    throws MalformedURLException {
	return new ResumeSolrDocument();
    }

    public ResumeSolrDocument() throws MalformedURLException {
	setServer(getSolrServer());
    }

    public void addResumeToServer(List<Resume> beans)
	    throws SolrServerException, IOException {
	System.out.println(beans);
	if (!beans.isEmpty()) {
	    server.addBeans(beans);
	}
    }

    public void addResumeToServer() throws SolrServerException, IOException {
	if (!this.resumeBeans.isEmpty()) {
	    server.addBeans(this.resumeBeans);
	}
    }

    public void addResumeBean(Resume resumeBean) {
	this.resumeBeans.add(resumeBean);
    }

    public void commitResume() throws SolrServerException, IOException {
	server.commit();
    }

    public SolrServer getServer() {
	return server;
    }

    public void setServer(SolrServer server) {
	this.server = server;
    }

    public static void main(String args[]) throws SolrServerException,
	    IOException {
	SolrServer server = ResumeSolrDocument.getSolrServer();
	SolrInputDocument doc1 = new SolrInputDocument();
	doc1.addField("id", "id123");
	doc1.addField("description", "some body11");
	Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
	docs.add(doc1);
	server.add(docs);
	server.commit();

    }
}
