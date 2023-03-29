
public class Node {

	public String ip;
	public Node leftChild;
	public Node rightChild;
	public int height;
	
	
	Node(String ip) {
		
		this.ip = ip;
		leftChild = null;
		rightChild = null;
		height = 1;
			
	}
	
}
