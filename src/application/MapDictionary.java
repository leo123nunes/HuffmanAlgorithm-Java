package application;

public class MapDictionary {
	private char key[];
	private String code[];
	private int indexToAdd;
	private int minBits;
	
	public MapDictionary(int quantity) {
		this.key = new char[255];
		this.code = new String[255];
		this.indexToAdd = 0;
		this.minBits = 255;
	}
	
	public int getMinBits() {
		return this.minBits;
	}
	
	public void addElement(char key, String code) {
		if(searchKey(key)==0) {
			this.key[indexToAdd] = key;
			this.code[indexToAdd] = code;
			if(this.code[indexToAdd].length()<minBits) {
				minBits = this.code[indexToAdd].length();
			}
			indexToAdd++;
		}	
	}
	
	public char getKeyFromCode(String c) {
		//for(int i=0; i<indexToAdd; i++) {
		//	if(code[i]==c) {
		//		return key[i];
		//	}
		//}
		for(int i=0; i<indexToAdd; i++) {
			if(code[i].equals(c)) {
				return key[i];
			}
		}
		return '\0';
	}
	
	public int searchKey(char key) {
		for(int c=0; c<indexToAdd; c++) {
			if(this.key[c]==key) {
				return 1;
			}
		}
		return 0;
	}
	
	public String searchForCode(char c) {
		for(int i=0; i<indexToAdd; i++) {
			if(this.key[i]==c) {
				return this.code[i];
			}
		}
		int i=0;
		return this.code[i];
	}
	
	
}
