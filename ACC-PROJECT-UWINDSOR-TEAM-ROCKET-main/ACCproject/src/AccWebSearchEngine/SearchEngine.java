package AccWebSearchEngine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SearchEngine extends Thread{
	static Scanner s = new Scanner(System.in);
	static updateFiles uF = new updateFiles();
	
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		boolean y = true;
		Crawler cr;
		uF.start();
		do
		{
			System.out.println("!--------------Enter URL--------------!");

			String url = s.next();
			url = "https://"+url+"/";
			cr = new Crawler(url,10);
			cr.start();
			System.out.println("!--------------Starting Crawler Please Wait--------------!");
			
			while(cr.flg == true)
			{
				System.out.print("");
			}
			if(cr.isAlive())
			{			
				EnterWordToSearch();
			}
			
			if(AskUserChoice("\n!--------------Do you want to continue with another URL?(y/n)--------------!") == false)
			{
				System.out.println("!--------------Closing Search Engine--------------!");
				deleteFiles();
				break;
			}
			
		}while(y);	
		deleteFiles();
		cr.stop();
		uF.stop();
		
	}
	
	public static void EnterWordToSearch() throws IOException
	{
		System.out.println("\nPlease Enter Word to Search: ");
		String word = s.next(); 
		SearchWord(word);
		if(AskUserChoice("\n!--------------Do you want to search another word?(y/n)--------------!") == true)
		{
			EnterWordToSearch();
		}
		else
		{
			return;
		}
	}
	
	
	static Hashtable<String, Integer> linksList = new Hashtable<String, Integer>();
	
	public static void SearchWord(String word) throws IOException
	{
		linksList.clear();
		int wordCount = 0;
		int fileCount = 0;
		File[] storedFiles = uF.FilesArray;

		for(int i = 0; i < storedFiles.length; i++)
		{
			File file = storedFiles[i];
			if(file.isFile() && file.getName().endsWith(".txt"))
			{
				String txt = new String(Files.readAllBytes( Paths.get(storedFiles[i].getPath())));
				Pattern p = Pattern.compile("::");
				String[] wordAtURL = p.split(txt);
				wordCount = Operations.FindWord(txt, word.toLowerCase(), wordAtURL[0]);
				if(wordCount!=0)
				{
					linksList.put(wordAtURL[0], wordCount);
					fileCount++;
				}
			}	
		}
		
		if(fileCount > 0)
		{
			System.out.println("\nTotal files Containing "+word+" are: "+ fileCount);
			Operations.sortedSearches(linksList, fileCount);
		}
		else
		{
			ArrayList<String> suggestedWords = new ArrayList<String>();
			ArrayList<String> totalSuggestedWords= new ArrayList<String>();
			for(int i = 0; i < storedFiles.length; i++)
			{
				File file = storedFiles[i];
				if(file.isFile() && file.getName().endsWith(".txt"))
				{
					String txt = new String(Files.readAllBytes( Paths.get(storedFiles[i].getPath())));	
					suggestedWords = Operations.SuggestWord(word, txt);
					totalSuggestedWords.addAll(suggestedWords); 
				}
			}
			
			HashSet<String> hs = new HashSet<String>(totalSuggestedWords);
			System.out.println("\n!--------------Could not find the word you are looking for. Did you mean: --------------!");
			for(String s: hs)
			{
				System.out.println(s);
			}
		
		}
		
	}
	
	//______________________________________________________________________________________________________
	// deletes all files created while crawling and word search.
	private static void deleteFiles() {
		
		File Files1 = new File(saveFiles.txtPath);
		File[] storedFiles1 = Files1.listFiles();
		
		for (int i = 0; i < storedFiles1.length; i++) {
			storedFiles1[i].delete();
		}
		
		File Files2 = new File(saveFiles.htmlPath);
		File[] storedFiles2 = Files2.listFiles();
		
		for (int i = 0; i < storedFiles2.length; i++) {
			storedFiles2[i].delete();
		}
	}
	//______________________________________________________________________________________________________
	
	public static boolean AskUserChoice(String st)
	{
		while(true)
		{
			System.out.println(st);
			char yon = s.next().charAt(0);
			if(yon == 'n')
				return false;
			else if(yon == 'y')
				return true;
			else
				System.out.println("\nInvalid Choice!");
			continue;
		}		
	}
}
