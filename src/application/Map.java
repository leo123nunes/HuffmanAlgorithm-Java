package application;

public class Map {
	public char[] key;
	public int[] frequency;
	public String[] codes;

	public Map(int quantity) {
		this.key = new char[quantity];
		this.frequency = new int[quantity];
	}
	
	public char getKey(int index) {
		return key[index];
	} 

	public int getFrequency(int index) {
		return frequency[index];
	}
	
	public int getSize() {
		return key.length;
	}
	
	public char[] getKeys() {
		return key;
	}

	public int[] getFrequencies() {
		return frequency;
	}

	public void addElement(char x) {
		if (getIndex(x) == -1) {
			int c = 0;
			while (key[c] != 0) {
				c++;
			}
			key[c] = x;
			frequency[c]++;
		} else {
			frequency[getIndex(x)]++;
		}
	}

	public int getIndex(char x) {
		for (int c = 0; c < key.length; c++) {
			if (key[c] == x) {
				return c;
			}
		}
		return -1;
	}

	public void printElements() {
		System.out.println();
		System.out.println("FOLHAS DESORDENADAS");
		System.out.print("FREQUENCIAS: ");
		for(int c=0; c<frequency.length; c++) {
			if(frequency[c]!=0) {
				System.out.print(frequency[c]+"/");
			}		
		}
		System.out.println();
		System.out.print("CARACTERES: ");
		for(int c=0; c<frequency.length; c++) {
			if(frequency[c]!=0) {
				if(key[c]=='\n') {
					System.out.print("Quebra de linha/");
				}else if(key[c]==' '){
					System.out.print("Espaço/");
				}else {
					System.out.print(key[c]+"/");
				}
			}
		}
		System.out.println();
		System.out.println();
		
		System.out.println("FOLHAS ORDENADAS");
		
		LeafSorter bubble = new LeafSorter(this);
		bubble.ordered();
		
		System.out.print("FREQUENCIAS: ");
		for(int c=0; c<frequency.length; c++) {
			if(frequency[c]!=0) {
				System.out.print(frequency[c]+"/");
			}		
		}
		System.out.println();
		System.out.print("CARACTERES: ");
		for(int c=0; c<frequency.length; c++) {
			if(frequency[c]!=0) {
				if(key[c]=='\n') {
					System.out.print("Quebra de linha/");
				}else if(key[c]==' '){
					System.out.print("Espaço/");
				}else {
					System.out.print(key[c]+"/");
				}
			}
		}
		System.out.println();
		System.out.println();
	}
	
	public String searchForKey(char k) {
		for(int i=0; i<frequency.length; i++) {
			if(key[i]=='k') {
				return codes[i];
			}
		}
		return "nothing found";
	}
}
