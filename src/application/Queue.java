package application;

public class Queue {
	private HuffmanTree[] leafs;
	private int indexAdd;
	private int indexRemove;
	private int elements;

	public Queue(int caracteres) {
		this.leafs = new HuffmanTree[255];
		this.indexAdd = 0;
		this.indexRemove = 0;
		this.elements = 0;
	}

	public HuffmanTree[] getLeafs() {
		return leafs;
	}

	public void addElement(HuffmanTree leaf) {
		this.leafs[indexAdd] = leaf;

		NodeSorter od = new NodeSorter(this.leafs);
		od.ordered();

		this.indexAdd++;
		this.elements++;
		
		
		if (this.elements >= 1) {
			int i = indexRemove;
			while (leafs[i] != null) {
				System.out.print(leafs[i].getFrequency() + "/");
				i++;
			}
			System.out.println();
		}
		
	}

	public HuffmanTree removeElement() {
		HuffmanTree leaf = leafs[indexRemove];
		this.leafs[indexRemove] = null;
		this.indexRemove++;
		this.elements--;
		return leaf;
	}

	public int getElements() {
		return this.elements;
	}
}
