package puzzle_lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainClass {
	
	public static void main(String[] args) {
		//Game game = new Game();
		
		FramePrincipale g = new FramePrincipale();
		Thread t = new Thread(g);
		t.start();
	}
}
