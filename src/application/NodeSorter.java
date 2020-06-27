package application;

public class NodeSorter {
	private HuffmanTree[] nodes;

	public NodeSorter(HuffmanTree[] nodes) {
		this.nodes = new HuffmanTree[100];
		this.nodes = nodes;
	}

	public void ordered() {
		int z = nodes.length-1;
		int indexLastElement = 0;
		while(nodes[z]==null) {
			if(nodes[z-1]!=null) {
				indexLastElement = z-1;
			}
			z--;
		}
		
		if(nodes[indexLastElement] instanceof HuffmanNode) {
			while(nodes[indexLastElement-1] != null && nodes[indexLastElement].frequency <= nodes[indexLastElement-1].frequency) {
				HuffmanTree smallerNode = nodes[indexLastElement-1];
				nodes[indexLastElement-1] = nodes[indexLastElement];
				nodes[indexLastElement] = smallerNode;
				indexLastElement--;
			}
		}
	}
}
