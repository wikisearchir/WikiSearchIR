package com.uci.ir.wikiSearch.parser;

import net.htmlparser.jericho.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class ExtractText {
	public void parseHtml(String[] args) throws Exception {
		String sourceUrlString="data/test.html";
		if (args.length==0)
		  System.err.println("Using default argument of \""+sourceUrlString+'"');
		else
			sourceUrlString=args[0];
		if (sourceUrlString.indexOf(':')==-1) sourceUrlString="file:"+sourceUrlString;
		MicrosoftConditionalCommentTagTypes.register();
		PHPTagTypes.register();
		PHPTagTypes.PHP_SHORT.deregister();
		MasonTagTypes.register();
		Source source=new Source(new URL(sourceUrlString));

		source.fullSequentialParse();
		
		FileWriter fstream = null;
		try {
			fstream = new FileWriter(args[1]);
		} catch (Exception e) {
			// TODO: handle exception
			fstream = new FileWriter("out.txt");
		}
		
				
		TextExtractor te = new TextExtractor(source);
		te.excludeElement(source.getFirstStartTag("a"));
		te.writeTo(fstream);
  }

}