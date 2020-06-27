package application;

public class LeafSorter {
	private Map leafs;

	public LeafSorter(Map leafs) {
		this.leafs = new Map(leafs.getSize());
		this.leafs = leafs;
	}

	public void ordered() {
		int c=0;
		while(leafs.getFrequency(c)!=0 && c < (leafs.getSize() -1)) {
			for(int i=0; i < leafs.getSize() -1 && leafs.getFrequency(i+1)!=0; i++) {
				if(leafs.frequency[i]>leafs.frequency[i+1]) {
					char smallerChar = leafs.key[i+1];
					int smallerFreq = leafs.frequency[i+1];
					leafs.key[i+1] = leafs.key[i];
					leafs.frequency[i+1] = leafs.frequency[i];
					leafs.key[i] = smallerChar;
					leafs.frequency[i] = smallerFreq;
				}
			}
			c++;
		}
	}
}
