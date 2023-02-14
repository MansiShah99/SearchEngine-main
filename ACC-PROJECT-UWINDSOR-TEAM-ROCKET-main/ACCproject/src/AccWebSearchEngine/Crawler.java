package AccWebSearchEngine;
import java.util.*;
import java.net.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import java.util.regex.*;


public class Crawler extends Thread{

	//HashSet in java is a collection of items where each item is unique
	public static HashSet<String> crawledSites = new HashSet<String>();
	//Max depth till the crawler will search for links
	private static int maxSearchDepth;
	public static boolean flg;
	//private static String regex = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";

	private static String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	private static Pattern pattern = Pattern.compile(regex);
	
	public static String searchURL;
	
	public Crawler(String s, int i)
	{
		searchURL = s;
		maxSearchDepth = i;
		flg = true;
	}
	
	public static void Crawl(int depthLevel, String url)
	{	
		if(depthLevel<=maxSearchDepth)
		{
			try 
			{
				Connection con = Jsoup.connect(url);
				Document doc = con.get();
				saveFiles.saveHTML(doc, url);
				depthLevel++;
				if(depthLevel<maxSearchDepth)
				{
					org.jsoup.select.Elements links = doc.select("a[href]");
					for (Element page : links) 
					{
						if(checkURL(page.attr("abs:href")) && pattern.matcher(page.attr("href")).find())
						{
							//System.out.println(page.attr("abs:href"));
							Crawl(depthLevel, page.attr("abs:href"));
							crawledSites.add(page.attr("abs:href"));
						}
					}
				}
			}
			
			catch(Exception e)
			{
			}
		}
	}
	
	//This method checks URL, if it is valid then it goes ahead and crawls
	public void run()
	{
		try
		{
			URL obj = new URL(searchURL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            if(con.getResponseCode()!=200) 
            {
            	System.out.println("\n!--------------Invalid URL--------------!");
            	return;
            }
            else 
            {
        		System.out.println("\n!--------------Start Searching--------------!");
        		flg = false;
            	Crawl(0, searchURL);	
            }
		}
		catch (Exception e)
		{
			System.out.println(e);
			System.out.println("\nInvalid URL");
			flg=false;
			return;
		}
	}
	
	
	private static boolean checkURL(String url)
	{
		if (crawledSites.contains(url)) {
			return false;
		}
		if (url.endsWith(".jpeg") || url.endsWith(".jpg") || url.endsWith(".png")
				|| url.endsWith(".pdf") || url.contains("#") || url.contains("?")
				|| url.contains("mailto:") || url.startsWith("javascript:") || url.endsWith(".gif")
				||url.endsWith(".xml")) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {

	}

}
