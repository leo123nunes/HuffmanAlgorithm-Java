package application;

import application.HuffmanTree;

public abstract class HuffmanTree implements Comparable<HuffmanTree>{
	public int frequency;
	
	public HuffmanTree(int freq) {
		this.frequency = freq;
	}
	
	@Override
	public int compareTo(HuffmanTree three) {
		return frequency - three.frequency;
	}
	
	public int getFrequency() {
		return this.frequency;
	}
}
