/**
 * 
 */
package edu.ics.uci.irw12.wiki.xml;

/**
 * @author anirudh
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.util.Iterator;
import org.jdom.Namespace;

import info.bliki.wiki.filter.PlainTextConverter;
import info.bliki.wiki.model.WikiModel;
import java.io.FileWriter;
import org.apache.log4j.*;

public class ParseXMLFiles {

	public ParseXMLFiles() {
		// Insert properties here for initialization
	}
	
	public void parseXML(Logger logger) throws Exception{

		SAXBuilder builder = new SAXBuilder();
		// Make this a property
		Properties prop = new Properties();
		
		try {
	        //load a properties file
	 		prop.load(new FileInputStream("config.properties"));
	 	} catch (IOException ex) {
	 		logger.fatal("Exception loading properties file");
	 		ex.printStackTrace();
	    }
		
		//Error checks
		if (prop.isEmpty()) {
			logger.fatal("Properties file is empty or not defined");
			return;
		} else if (!prop.containsKey("parse.file.xml")){
			logger.fatal("parser.file.xml is not defined");
			return;
		}
		
		File xmlFile = new File(prop.getProperty("parse.file.xml"));

		try {
			// Start parsing by loading doc and getting root element of doc
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			Namespace ns = rootNode.getNamespace();
			@SuppressWarnings("unchecked")
			List<Element> list = rootNode.getChildren("page", ns);
			FileWriter fw = null;

			// For all page elements within the doc iterate and get title,text
			for (int i = 0; i < list.size(); i++) {
				Element node = (Element) list.get(i);
				//System.out.println("Element : " + node.getName());
				//System.out.println(" Its children are:");
				
				// Trying to get all children here
				@SuppressWarnings("unchecked")
				List<Element> l = node.getChildren();
				Iterator<Element> it = l.iterator();
				Element child;

				// Every page has title, revision->text which need to be indexed
				while (it.hasNext()) {
					child = (Element) it.next();
					String s = child.getName();
					//System.out.println("Child : " + s);
					if (s.equalsIgnoreCase("title")) {
						String file = child.getText();
						// This is needed as title contained awards/best which
						// is not acceptible as dir/file
						file = file.replace("/", "");
						File fi = new File("parsed/", file);
						fw = new FileWriter(fi);
						fw.write(s);
					}
					if (s.equalsIgnoreCase("revision")) {
						// System.out.println("text" +
						// child.getChildText("text",ns));
						String p = child.getChildText("text", ns);
						// This is from bliki to parse the wiki markup language
						WikiModel wikiModel = new WikiModel(
								"http://www.mywiki.com/wiki/${image}",
								"http://www.mywiki.com/wiki/${title}");
						String plainStr = wikiModel.render(
								new PlainTextConverter(), p);
						if (fw != null)
							fw.write(plainStr);
						// System.out.println(plainStr);
					}
				}
				if (fw != null)
					fw.close();
				// System.out.println("Type : " + node.getChildrenText("title",
				// ns));
			}

		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
	}

}
