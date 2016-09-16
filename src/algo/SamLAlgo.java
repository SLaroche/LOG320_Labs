package algo;

import java.util.Arrays;
import java.util.Hashtable;

public class SamLAlgo extends AnagramAlgo{
	
	final int[] nombrePremier = {
			2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 
			31, 37, 41, 43, 47, 53, 59, 61, 67,
			71, 73, 79, 83, 89, 97, 101};
	final char[] alphabet = {
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

	@Override
	protected void run() {
		// TODO Auto-generated method stub
		
		System.out.println("SamL");
		Hashtable<Character, Integer> source = fillUp();
		int a = convertToKey("testhhdhdhdhdhdhdhdhddhtesthhdhdhdhdhdhdhdhddhtesthhdhdhdhdhdhdhdhddhtesthhdhdhdhdhdhdhdhddhtesthhdhdhdhdhdhdhdhddh", source);
		int b = convertToKey("sthhdhdhdhdhdhdhdhddhtesthhdhdhdhdhdhdhdhddhtesthhdhdhdhdhdhdhdhddhtesthhdhdhdhdhdhdhdhddhtesthhdhdhdhdhdhdhdhddhte", source);
		if (a == b) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
	
	public Hashtable<Character, Integer> fillUp() {
		Hashtable<Character, Integer> source = new Hashtable<Character, Integer>();
		for (int i = 0; i < alphabet.length; i++) {
			source.put(alphabet[i], nombrePremier[i]);
		}
		return source;
	}
	
	public String reordonne(String chaine) {
		char[] chars = chaine.toCharArray();
		Arrays.sort(chars);
		chaine = new String(chars);
		return chaine;
	}
	
	public int convertToKey(String chaine, Hashtable<Character, Integer> source) {
		int key = 1;
		chaine = chaine.replace(" ", "");
		chaine = chaine.toLowerCase();
		System.out.println(chaine);
		for (int i = 0; i < chaine.length(); i++) {
			key *= source.get(chaine.charAt(i));
		}
		return key;
	}
	
	public int convertToKey1(String chaine) {
		int key = 1;
		chaine = chaine.replace(" ", "");
		chaine = chaine.toLowerCase();
		
		for (int i = 0; i < chaine.length(); i++) {
			switch(chaine.charAt(i)) {
				case 'a' :
					key *= nombrePremier[0];
					break;
				case 'b' :
					key *= nombrePremier[1];
					break;
				case 'c' :
					key *= nombrePremier[2];
					break;
				case 'd' :
					key *= nombrePremier[3];
					break;
				case 'e' :
					key *= nombrePremier[4];
					break;
				case 'f' :
					key *= nombrePremier[5];
					break;
				case 'g' :
					key *= nombrePremier[6];
					break;
				case 'h' :
					key *= nombrePremier[7];
					break;
				case 'i' :
					key *= nombrePremier[8];
					break;
				case 'j' :
					key *= nombrePremier[9];
					break;
				case 'k' :
					key *= nombrePremier[10];
					break;
				case 'l' :
					key *= nombrePremier[11];
					break;
				case 'm' :
					key *= nombrePremier[12];
					break;
				case 'n' :
					key *= nombrePremier[13];
					break;
				case 'o' :
					key *= nombrePremier[14];
					break;
				case 'p' :
					key *= nombrePremier[15];
					break;
				case 'q' :
					key *= nombrePremier[16];
					break;
				case 'r' :
					key *= nombrePremier[17];
					break;
				case 's' :
					key *= nombrePremier[18];
					break;
				case 't' :
					key *= nombrePremier[19];
					break;
				case 'u' :
					key *= nombrePremier[20];
					break;
				case 'v' :
					key *= nombrePremier[21];
					break;
				case 'w' :
					key *= nombrePremier[22];
					break;
				case 'x' :
					key *= nombrePremier[23];
					break;
				case 'y' :
					key *= nombrePremier[24];
					break;
				case 'z' :
					key *= nombrePremier[25];
					break;
			}
		}
		System.out.println(key + "");
		return key;
	}

}
