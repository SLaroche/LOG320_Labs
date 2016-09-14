package algo;

public class PdfAlgo extends AnagramAlgo{

	@Override
	public void run() {
		boolean test = algo("test", "tset");
		System.out.println(test);
	}
	
	public boolean algo(String c1, String c2) {
		c1 = c1.replace(" ", "");
		c2 = c2.replace(" ", "");
		
		System.out.println("start loop " + c1 + " " + c2);
		for (int i = 0; i < c1.length(); i++) {
			boolean trouve = false;
			for (int j = 0; j < c2.length() && trouve == false; j++) {
				char c = c2.charAt(j);
				if (c1.charAt(i) == c) {
					c2 = c2.replaceFirst(c + "", "");
					trouve = true;
				}
			}
			if (!trouve) {
				System.out.println(c2 + "");
				return false;
			}
		}
		System.out.println(c2 + "");
		if (c2 != "") {
			return false;
		}
		return true;
	}
}
