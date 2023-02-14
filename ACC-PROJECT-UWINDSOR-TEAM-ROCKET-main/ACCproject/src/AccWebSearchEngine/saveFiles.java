package AccWebSearchEngine;
import java.io.*;

import org.jsoup.*;
import org.jsoup.nodes.*;

public class saveFiles {
	
	public static String htmlPath = "htmlFiles/";
	public static String txtPath= "textFiles/";
	
	public static void saveHTML(Document doc, String url)
	{
		try
		{
			String htmlTitle = doc.title() + ".html";
			String textTitle = doc.title() + ".txt";
			PrintWriter html = new PrintWriter(new FileWriter(htmlPath+htmlTitle));
			html.write(doc.toString());
			html.close();
			saveText(htmlPath+htmlTitle, url, textTitle);
			
		}
		catch(Exception e)
		{
			
		}
	}
	
	public static void saveText(String htmlfile, String url, String filename)
	{
		try 
		{
			File file = new File(htmlfile);
			Document doc = Jsoup.parse(file, "UTF-8");
			String data = doc.text().toLowerCase();
			data = url + "::" + data;
			PrintWriter writer = new PrintWriter(txtPath + filename);
			writer.println(data);
			writer.close();
		}
		catch(Exception e)
		{}
	}

	public static void main(String[] args) {
		
	}

}
