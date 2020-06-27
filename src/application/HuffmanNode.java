package application;

import application.HuffmanTree;

class HuffmanNode extends HuffmanTree{
	
	public final HuffmanTree left, right;

	public HuffmanNode(HuffmanTree left, HuffmanTree right) {
		super(left.getFrequency() + right.getFrequency());
		this.left = left;
		this.right = right;
	}
	
}
