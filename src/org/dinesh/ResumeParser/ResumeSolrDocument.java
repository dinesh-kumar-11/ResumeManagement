package org.dinesh.ResumeParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
 

public class ResumeSolrDocument {

    public static SolrServer getSolrServer() throws MalformedURLException {
	return new HttpSolrServer("http://localhost:8983/solr");
    }
    
    

    public static void main(String args[]) throws SolrServerException, IOException {
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
