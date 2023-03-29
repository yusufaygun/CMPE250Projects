import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		try {
			FileWriter bstWriter = new FileWriter(args[1] + "_bst.txt");
			FileWriter avlWriter = new FileWriter(args[1] + "_avl.txt");
			
			try {
				File input = new File(args[0]);
				Scanner scan = new Scanner(input);
				String initialIp = scan.nextLine();
				
				BinarySearchTree bst = new BinarySearchTree(bstWriter);
				AVLTree avl = new AVLTree(avlWriter);
		
				String inputfile = "";
				while(scan.hasNextLine()) {
					inputfile = inputfile.concat(scan.nextLine() + "\n");
				}
		
				String[][] allLines = new String[inputfile.split("\n").length][3];	
		

				for (int i = 1; i < inputfile.split("\n").length; i++ ) {
			
					allLines[i] = inputfile.split("\n")[i].split(" ");
			
				} 
		
				bst.root = bst.addNode(bst.root, initialIp);
				avl.root = avl.addNode(avl.root, initialIp);
		
			
		
				for (String[] line : allLines) {
			
					switch(line[0]) {
			
					case "ADDNODE" :
				
						bst.addNode(bst.root, line[1]);
						avl.addNode(avl.root, line[1]);
						break;
				
					case "DELETE" :
			
						bst.delete(bst.root, null, line[1]);
						avl.delete(avl.root, null, line[1]);
						break;
				
					case "SEND" :
			
						Node sender = new Node(line[1]);
						Node receiver = new Node(line[2]);
				
						bst.send(sender, receiver);
						avl.send(sender, receiver);
						break;
				
					}
				}
		
				scan.close();
		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		
			bstWriter.close();
			avlWriter.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
