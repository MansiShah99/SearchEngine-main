package AccWebSearchEngine;
import java.util.*;
import java.io.*;

public class updateFiles extends Thread{
	
	//public Hashtable<String, Integer> FileList = new Hashtable<String, Integer>();
	
	public File Files;
	public File[] FilesArray;
	
	public void run()
	{
		while(true)
		{
			Files = new File(saveFiles.txtPath);
			FilesArray = Files.listFiles();
			//System.out.println(FilesArray.length);
		}	
	}

}
