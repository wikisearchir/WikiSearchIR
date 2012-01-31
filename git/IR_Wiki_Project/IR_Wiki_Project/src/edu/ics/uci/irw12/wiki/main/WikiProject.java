package edu.ics.uci.irw12.wiki.main;

import edu.ics.uci.irw12.wiki.xml.ParseXMLFiles;
import edu.ics.uci.irw12.wiki.indexer.Indexer;
import edu.ics.uci.irw12.wiki.search.SearchIndex;
import org.apache.log4j.*;
import org.apache.log4j.PropertyConfigurator;

public class WikiProject {

	Indexer indexer;
	ParseXMLFiles PXF;
	Logger logger;
	SearchIndex SI;
	
	
	public WikiProject() {
		// Put init stuff here
	}
	public Indexer getIndexer() {
		return indexer;
	}
	public Logger getLogger() {
		return logger;
	}
	public ParseXMLFiles getPXF() {
		return PXF;
	}
	public SearchIndex getSI() {
		return SI;
	}
	public void initialize() {
		try {
			long start,end;
			PropertyConfigurator.configure("log4j.properties");
			logger = Logger.getLogger("edu.ics.uci.irw12.wiki.WikiProject.class");
			indexer = new Indexer();
			logger.info("Indexer created");
			PXF = new ParseXMLFiles();
			logger.info("Parse object created");
			start=System.currentTimeMillis();
			//PXF.parseXML(logger);
			end=System.currentTimeMillis();
			logger.info("Time needed to parse XMLs is - "+(end-start)+" milliseconds");
			start=System.currentTimeMillis();
			indexer.createIndex(logger);
			end=System.currentTimeMillis();
			logger.info("Time needed to index is - "+(end-start)+" milliseconds");
			SI = new SearchIndex();
			logger.info("Search object created");
		} catch (Exception e) {
			/*Make exceptions clear - Ambitious to have an exception framework*/
			logger.error(e.getMessage());
		}
	}
	
	public void search() {
		try {
			//THIS IS A HACK AND NEEDS TO BE CORRECTED
			String options[]=null;
			SI.searchIndex(options, logger);
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	

	public static void main(String[] args) {
		WikiProject wp = new WikiProject();
		
		try {
			System.out.println("Initializing system");
			wp.initialize();
			System.out.println("System has been initialized");
			wp.search();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
