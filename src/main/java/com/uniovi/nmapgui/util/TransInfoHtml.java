package com.uniovi.nmapgui.util;


import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class TransInfoHtml {
	public static String transformToHtml(String xml){
		try {
			
	        //InputStream xsl = new URL("https://svn.nmap.org/nmap/docs/nmap.xsl").openStream();
	        InputStream xsl = TransInfoHtml.class.getClassLoader().getResourceAsStream("static/nmap.xsl");

	        StringWriter writer = new StringWriter();
	
	        Source xmlDoc =  new StreamSource(new StringReader(xml));
	        Source xslDoc =  new StreamSource(xsl);
	        Result result =  new StreamResult(writer);
	
	        TransformerFactory factory = TransformerFactory.newInstance();            
	        Transformer trans = factory.newTransformer(xslDoc);
	        trans.transform(xmlDoc, result); 
	
	        String outputString = writer.toString();
	        return outputString;
	        
	    }catch (Exception e) 
	    {
	        e.printStackTrace();
	        return "";
	    }
	    
	}
}
