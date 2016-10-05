package puzzle_lab2;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	Point[][] tabGame; 
	public Game(){
		String filePath = "test.puzzle";
		//tabGame = getListFile(filePath);
		System.out.println(tabGame[2][2]);
	}
	
	public void makeMove(Point pion, Point target){
		
	}
	private static int[][] getListFile(String filePath)
	{
		Scanner textInFile;
		int[][] tab = new int[7][7];
		
		try {
			textInFile = new Scanner(new File(filePath)).useDelimiter(",\\s*");
			int i = 0;
			while (textInFile.hasNextLine()) 
			{
				String line = textInFile.nextLine();
				for(int j=0;j<line.length();j++)
				{
					tab[i][j] = (int)line.charAt(j);
					System.out.print(line.charAt(j));
				}
				System.out.println("");
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tab;
	}
}
