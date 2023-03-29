
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Project1 {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		PrintStream output = null;
		try {
			output = new PrintStream(args[1]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setOut(output);
		
		
		
		FactoryImpl factoryline = new FactoryImpl();
			
		File input = new File(args[0]);
		Scanner scan = new Scanner(input);
		
		String inputfile = "";
		while(scan.hasNextLine()) {
			inputfile = inputfile.concat(scan.nextLine() + "\n");
		}
		String[][] allLines = new String[inputfile.split("\n").length][4];	
		

		for (int i = 0; i < inputfile.split("\n").length; i++ ) {
			
			allLines[i] = inputfile.split("\n")[i].split(" ");
			
		}
			
		
		for (String[] line : allLines) {
			
			switch(line[0]) {
			
			case "AF":
				
				Product productAF = new Product(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
				factoryline.addFirst(productAF);
				break;
				
			case "AL":
				
				Product productAL = new Product(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
				factoryline.addLast(productAL);
				break;
				
			case "A":
				
				int indexA = Integer.parseInt(line[1]);
				Product productA = new Product(Integer.parseInt(line[2]), Integer.parseInt(line[3]));
				try {
					factoryline.add(indexA, productA);
				} catch (IndexOutOfBoundsException e) {
					System.out.println(e.getMessage());
				}
				break;
			
			case "RF":
				
				try {
					System.out.println(factoryline.removeFirst());
				} catch (NoSuchElementException e) {
					System.out.println(e.getMessage());
				}
				
				break;
			
			case "RL":
				
				try {
					System.out.println(factoryline.removeLast());
				} catch (NoSuchElementException e) {
					System.out.println(e.getMessage());
				}
				break;
			
			case "RI":
				
				int indexRI = Integer.parseInt(line[1]);
				try {
					System.out.println(factoryline.removeIndex(indexRI));
				} catch (IndexOutOfBoundsException e) {
					System.out.println(e.getMessage());
				}
				break;
			
			case "RP":
				
				int valueRP = Integer.parseInt(line[1]);
				try {
					System.out.println(factoryline.removeProduct(valueRP));
				} catch (NoSuchElementException e) {
					System.out.println(e.getMessage());
				}
				break;
				
			case "F":
				
				int idF = Integer.parseInt(line[1]);
				try {
					System.out.println(factoryline.find(idF));
				} catch (NoSuchElementException e) {
					System.out.println(e.getMessage());
				}
				break;
			
			case "G":
				
				int indexG = Integer.parseInt(line[1]);
				try {
					System.out.println(factoryline.get(indexG));
				} catch (IndexOutOfBoundsException e) {
					System.out.println(e.getMessage());
				}
				break;
				
			case "U":
				
				int idU = Integer.parseInt(line[1]);
				int valueU = Integer.parseInt(line[2]);
				try {
					System.out.println(factoryline.update(idU, valueU));
				} catch (NoSuchElementException e) {
					System.out.println(e.getMessage());
				}
				break;
				
			case "FD":
				
				System.out.println(factoryline.filterDuplicates());
				break;
				
			case "R":
				
				factoryline.reverse();
				System.out.println(factoryline.toString());
				break;
				
			case "P":
				
				System.out.println(factoryline.toString());
				break;
					
			}	
		}	
	}
}