import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class AVLTree {

	 public Node root;
	 private final FileWriter avlWriter;
	 
	 
	 public AVLTree(FileWriter avlWriter) {
		 
		 this.avlWriter = avlWriter;
	 }

	 public int height(Node N) {
		 if (N == null) {
			 return 0;
		 }
	        return N.height;
	 }

	 public int max(int first, int second) {
		 return (first > second) ? first : second;
	 }

	 public Node rightRotate(Node node) {
		 Node left1 = node.leftChild;
	     Node right2 = left1.rightChild;

	     left1.rightChild = node;
	     node.leftChild = right2;

	     node.height = max(height(node.leftChild), height(node.rightChild)) + 1;
	     left1.height = max(height(left1.leftChild), height(left1.rightChild)) + 1;

	     return left1;
	 }
	 

	 public Node leftRotate(Node node) {
		 Node right1 = node.rightChild;
		 Node left2 = right1.leftChild;

		 right1.leftChild = node;
		 node.rightChild = left2;

		 node.height = max(height(node.leftChild), height(node.rightChild)) + 1;
		 right1.height = max(height(right1.leftChild), height(right1.rightChild)) + 1;

		 return right1;
	 }

	 public int getBalance(Node N) {
		 if (N == null) {
			 return 0;
		 }
		 return height(N.leftChild) - height(N.rightChild);
	 }
	    
	 public Node addNode(Node node, String ip) throws IOException {
		 
		 

		 if (node == null) {
			 return new Node(ip);		
		 }
			
		if (ip.compareTo(node.ip) < 0) {
			
			avlWriter.write(node.ip + ": New node being added with IP:" + ip + "\n");
			node.leftChild = addNode(node.leftChild, ip);
		}
			
		if (ip.compareTo(node.ip) > 0) {
			
			avlWriter.write(node.ip + ": New node being added with IP:" + ip + "\n");
			node.rightChild = addNode(node.rightChild, ip);
		}

		node.height = 1 + max(height(node.leftChild), height(node.rightChild));
	 

		int balanceFactor = getBalance(node);
	 

		if (balanceFactor > 1 && ip.compareTo(node.leftChild.ip) < 0)
			return rightRotate(node);

		if (balanceFactor < -1 && ip.compareTo(node.leftChild.ip) > 0)
			return leftRotate(node);

		if (balanceFactor > 1 && ip.compareTo(node.leftChild.ip) > 0) {
			node.leftChild = leftRotate(node.leftChild);
			return rightRotate(node);
		}

		if (balanceFactor < -1 && ip.compareTo(node.leftChild.ip) < 0) {
			node.rightChild = rightRotate(node.rightChild);
			return leftRotate(node);
		}

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
					 
					 avlWriter.write(parentNode.ip + ": Leaf Node Deleted: " + ip + "\n");
					 node = null;
					 
				 } else {
					 
					 avlWriter.write(parentNode.ip + ": Node with single child Deleted: " + ip + "\n");
					 node = temp;
				 }
			 } else {
				 Node successor = getSuccessor(node);
				 node.ip = successor.ip;
				 avlWriter.write(parentNode.ip + ": Non Leaf Node Deleted; removed:" + ip + " replaced: " + node.ip +  "\n");
				 node.rightChild = delete(node.rightChild, node, successor.ip);
			 }
		 }
			
		 if (node == null) {
			 return null;
		 }
			
		 node.height = 1 + max(height(node.leftChild), height(node.rightChild));
			
		 int balanceFactor = getBalance(node);
			
		 if (balanceFactor > 1 && ip.compareTo(node.leftChild.ip) < 0) {
			 
			 avlWriter.write("Rebalancing: right rotation" + "\n");
			 return rightRotate(node);
		 }
		 
		 if (balanceFactor < -1 && ip.compareTo(node.leftChild.ip) > 0) {
			 
			 avlWriter.write("Rebalancing: left rotation" + "\n");
			 return leftRotate(node);
		 }
		    	       	
		 if (balanceFactor > 1 && ip.compareTo(node.leftChild.ip) > 0) {
			 
			 node.leftChild = leftRotate(node.leftChild);
			 avlWriter.write("Rebalancing: left-right rotation" + "\n");
			 return rightRotate(node);
		 }
		 
		 if (balanceFactor < -1 && ip.compareTo(node.leftChild.ip) < 0) {
			 
			 node.rightChild = rightRotate(node.rightChild);
			 avlWriter.write("Rebalancing: right-left rotation" + "\n");
			 return leftRotate(node);
		 }
			
		 return node;
			
	    }
	    
	 public Node getSuccessor (Node node) {
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
		 
		 avlWriter.write(sender.ip + ": Sending message to: " + receiver.ip + "\n");
			
			
			for (int i = 1; i < linkedList.size(); i++) {
				
				avlWriter.write(linkedList.get(i).ip + ": Transmission from: " + linkedList.get(i - 1).ip + "receiver: " + receiver.ip + " sender:" + sender.ip + "\n" );
				
			}
			
			avlWriter.write(receiver.ip + " Received message from: " + sender.ip + "\n" );
			
			
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






