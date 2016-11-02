
public class MainClass {
	public static void main(String[] args) {
		FramePrincipale g = new FramePrincipale();
		Thread t = new Thread(g);
		t.start();
	}
}
