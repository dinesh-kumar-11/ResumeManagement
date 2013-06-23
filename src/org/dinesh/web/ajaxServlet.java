package org.dinesh.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrServerException;
import org.dinesh.solr.ResumeSolrSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class ActionServlet
 */

public class ajaxServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory
	    .getLogger(ajaxServlet.class);

    public ajaxServlet() {
	// TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	String searchString = null;
	searchString = request.getParameter("user");
	if (request.getParameter("user").toString().equals("")) {
	    searchString = "*";
	}

	ResumeSolrSearch resumeSearchObj = ResumeSolrSearch
		.getResumeSolrSearch(searchString);
	logger.info(searchString);
	try {
	    if (resumeSearchObj.searchSolr() != null) {
		String output = resumeSearchObj.getThemedOutput();
		logger.debug(output);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(output);
	    }
	} catch (SolrServerException e) {
	    e.printStackTrace();
	}

    }

    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub

    }

}
