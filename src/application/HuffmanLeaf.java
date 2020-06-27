package application;

import application.HuffmanTree;

class HuffmanLeaf extends HuffmanTree{
	
	public final char value;
	public int frequency;

	public HuffmanLeaf(int freq, char value) {
		super(freq);
		this.value = value;
		this.frequency = freq;
	}
	
	public int getFrequency() {
		return this.frequency;
	}
	
	public char getValue() {
		return this.value;
	}

}
