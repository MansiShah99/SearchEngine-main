package AccWebSearchEngine;

/*import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;*/
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Operations {
	
	
	public static int FindWord(String data, String Word, String FileName)
	{
		int count = 0;
		int offset = 0;
		BoyerMoore bm = new BoyerMoore(Word);
		
		for (int location = 0; location <= data.length(); location += offset + Word.length()) {
			offset = bm.search(Word, data.substring(location));
			if ((offset + location) < data.length()) {
				count++;
			}
		}	
		
		
		return count;
	}
	
	public static void sortedSearches(Hashtable<?, Integer> files, int totalFiles)
	{
		Set<Integer> s = new HashSet<>(files.values());
		
		List<Integer> values = new ArrayList<>(s);
		Collections.sort(values);
		Collections.reverse(values);
				
		System.out.println("!--------------Sorted By Top Occurences--------------!");
		for(int i = 0; i < values.size(); i++)
		{
			for (String key : getKeys((Map<String, Integer>) files, values.get(i))) {

				System.out.println(key +" ---> "+values.get(i)+" Times");
	      }
		}
	}
	
	
	public static ArrayList<String> SuggestWord(String word, String data)
	{
		ArrayList<String> foundWords = new ArrayList<String>();		
		int maxDist = 1;
		
		Pattern p = Pattern.compile("\\s");
		String[] totalWords = p.split(data);
		
		for(int i = 0; i < totalWords.length; i++)
			{
				int k = Operations.editDistance(word, totalWords[i]);
				if(k==maxDist)
				{
					foundWords.add(totalWords[i]);
				}
			}				
		return foundWords;
	}
	
	
	public static int editDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();
	 
		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];
	 
		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}
	 
		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}
	 
		//iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);
	 
				//if last two chars equal
				if (c1 == c2) {
					//update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;
	 
					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}	 
		return dp[len1][len2];
	}
	
	private static HashSet<String> getKeys(Map<String, Integer> map, Integer value) {

	      HashSet<String> result = new HashSet<>();
	      if (map.containsValue(value)) {
	          for (Map.Entry<String, Integer> entry : map.entrySet()) {
	              if (Objects.equals(entry.getValue(), value)) {
	                  result.add(entry.getKey());
	              }
	          }
	      }
	      return result;

	  }

}
