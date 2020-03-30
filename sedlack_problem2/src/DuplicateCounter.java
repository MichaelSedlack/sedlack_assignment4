import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DuplicateCounter {
	private static ArrayList<String> uniqueWordList = new ArrayList<String>();
	private static ArrayList<Integer> wordCounter = new ArrayList<Integer>();
	
	
	public void count(String input)
	{
		fileTester(input);
		File inPath = new File(input);
		try {
			Scanner scan = new Scanner(inPath);
			
			while(scan.hasNext())
			{
				uniqueTest(scan.next());
			}
			
			scan.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found.");
			System.exit(0);
		}
	}
	
	public void write(String output) {
		String temp;
		
		createFile(output);
		Path outPath = Paths.get(output);
		temp = uniqueWordList.get(0) + " " + wordCounter.get(0);
		for(int i = 1; i < uniqueWordList.size(); i++)
		{
			temp = temp + " " + uniqueWordList.get(i) + " " + wordCounter.get(i);	
		}
		try {
			Files.write(outPath, temp.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void uniqueTest(String newWord)
	{	
		boolean wordIsUnique = true;
		wordTester:
			for(int i = 0; i < uniqueWordList.size(); i++)
			{
				if(removePunctuation(newWord).equalsIgnoreCase(uniqueWordList.get(i)))
				{
					wordIsUnique = false;
					wordCounter.set(i, wordCounter.get(i)+1);
					break wordTester;
				}
			}
		
		if (wordIsUnique)
		{
			uniqueWordList.add(newWord);
			wordCounter.add(1);
		}
	}
	private static String removePunctuation(String in)
	{
		in = in.replaceAll("\\p{Punct}", "");
		return in;
	}
	private void fileTester(String input)
	{
		File inputTest  = new File(input);
		if(!inputTest.exists())
		{
			System.out.println(input + " doesn't exist.");
			System.exit(0);
		}
	}
	
	private void createFile(String input)
	{
		Path outPath = Paths.get(input);
		try {
			Files.deleteIfExists(outPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Something went wrong, sorry");
			System.exit(0);
		}
		try {
			Files.createFile(outPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("A file deleted literally 10 lines above somehow exists.");
			System.exit(0);
		}	
	}
}
