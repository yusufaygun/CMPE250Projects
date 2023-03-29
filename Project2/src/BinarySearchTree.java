import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class BinarySearchTree {

	
	public Node root;
	
	private final FileWriter bstWriter;
	
	public BinarySearchTree(FileWriter bstWriter ) {
		this.bstWriter = bstWriter;
	}
	
	
	
	public int height(Node N) {
        if (N == null)
            return 0;
 
        return N.height;
    }
 
    public int max(int first, int second) {
        return (first > second) ? first : second;
    }

	
	public Node addNode(Node node, String ip) throws IOException {
		
		if (node == null) {
			
			return new Node(ip);
		}
		
		if (ip.compareTo(node.ip) < 0) {
			
			bstWriter.write(node.ip + ": New node being added with IP:" + ip + "\n");
			node.leftChild = addNode(node.leftChild, ip);
		}
		
		if (ip.compareTo(node.ip) > 0) {
			
			bstWriter.write(node.ip + ": New node being added with IP:" + ip + "\n");
			node.rightChild = addNode(node.rightChild, ip);
		}
		
		node.height = 1 + max(height(node.leftChild), height(node.rightChild));
		
		return node;
	}
	
	public Node delete(Node node, Node parentNode, String ip) throws IOException {
		
		if (node == null) {
			return null;
		}
		
		if (ip.compareTo(node.ip) < 0) {
			node.leftChild = delete(node.leftChild, node, ip);
		}
		
		if (ip.compareTo(node.ip) > 0) {
			node.rightChild = delete(node.rightChild, node, ip);
		}
		
		else {
			if(node.leftChild == null || node.rightChild == null) {
				Node temp = null;
				
				if(node.leftChild == null) {
					temp = node.rightChild;
				} else {
					temp = node.leftChild;
				}
				
				if (temp == null) {
					
					bstWriter.write(parentNode.ip + ": Leaf Node Deleted: " + ip + "\n");
					return null;
					
				} else {
					
					bstWriter.write(parentNode.ip + ": Node with single child Deleted: " + ip + "\n");
					return node;
				}
			} else {
				
				Node successor = getSuccessor(node);
				node.ip = successor.ip;
				bstWriter.write(parentNode.ip + ": Non Leaf Node Deleted; removed:" + ip + " replaced: " + node.ip +  "\n");
				node.rightChild = delete(node.rightChild, node, successor.ip);
			}
		}
		
		node.height = 1 + max(height(node.leftChild), height(node.rightChild));
		
		return node;
		
	}
	
	public Node getSuccessor(Node node) {
		if (node == null) {
			return null;
		}
		
		Node temp = node.rightChild;
		
		while (temp != null) {
			temp = temp.leftChild;
		}
		
		return temp;
	}
	
	
	
	
	public void send(Node sender, Node receiver) throws IOException {
		
		Node parent = parent(root, sender, receiver);
		LinkedList<Node> linkedList = new LinkedList<Node>();
		linkedList.addFirst(parent);
				
		senderWing(parent, linkedList, sender);
		receiverWing(parent, linkedList, receiver);
		
		bstWriter.write(sender.ip + ": Sending message to: " + receiver.ip + "\n");
		
		
		for (int i = 1; i < linkedList.size(); i++) {
			
			bstWriter.write(linkedList.get(i).ip + ": Transmission from: " + linkedList.get(i - 1).ip + "receiver: " + receiver.ip + " sender:" + sender.ip + "\n" );
			
		}
		
		bstWriter.write(receiver.ip + " Received message from: " + sender.ip + "\n" );
		
		
	}
	
	
	public Node parent(Node root, Node sender, Node receiver) {
		
		Node parent = root;
		
		if (sender.ip.compareTo(parent.ip) > 0 && receiver.ip.compareTo(parent.ip) > 0) {
			
			parent = parent(parent.rightChild, sender, receiver);
			
		}
		
		if (sender.ip.compareTo(parent.ip) < 0 && receiver.ip.compareTo(parent.ip) < 0) {
			
			parent = parent(parent.leftChild, sender, receiver);
			
		}
		
		return parent;
	}
	
	public void senderWing(Node parent, LinkedList<Node> linkedList, Node sender) {
		
		Node current = parent;
		
		while (current != sender) {
			
			if(current.ip.compareTo(sender.ip) < 0) {
				
				current = current.leftChild;
				linkedList.addFirst(current);
				
			} else {
				
				current = current.rightChild;
				linkedList.addFirst(current);
			}	
		}
	}
	
	public void receiverWing(Node parent, LinkedList<Node> linkedList, Node receiver) {
		
		Node current = parent;
		
		while (current != receiver) {
			
			if(current.ip.compareTo(receiver.ip) < 0) {
				
				current = current.leftChild;
				linkedList.addLast(current);
				
			} else {
				
				current = current.rightChild;
				linkedList.addLast(current);
			}	
		}
	}
	
	
}










