package puzzle_lab2;

public class MainClass {
	
	public static void main(String[] args) {
		//Game game = new Game();
		
		FramePrincipale g = new FramePrincipale();
		Thread t = new Thread(g);
		t.start();
	}
}
