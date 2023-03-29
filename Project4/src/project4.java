import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;


public class project4 {


	public static void main(String[] args) throws FileNotFoundException {
		
		try {
			FileWriter output = new FileWriter(args[1]);
			
			try {
				
				File input = new File(args[0]);
				Scanner scan = new Scanner(input);
				
				int verticeNum = Integer.parseInt(scan.nextLine());
				int flagNum = Integer.parseInt(scan.nextLine());
				String points = scan.nextLine();
				String start = points.split(" ")[0];
				String finish = points.split(" ")[1];
				Vertice startVertice = null;
				Vertice finishVertice = null;
				String[] flagPoints = scan.nextLine().split(" ");

				Graph graph = new Graph(verticeNum, flagNum);
				
				while (scan.hasNextLine()) {
					
					String[] line = scan.nextLine().split(" ");
					
					Vertice vertice = new Vertice(line[0]);
					
					vertice.addAdjLine(line);
					
					graph.addVertice(vertice);
					
					if (vertice.name.equals(start)) {
						
						startVertice = vertice;
						
					}
					
					if (vertice.name.equals(finish)) {
						
						finishVertice = vertice;
						
					}
					
					if (Arrays.asList(flagPoints).contains(vertice.name)) {
						
						vertice.hasFlag = true;
						graph.addFlag(vertice);
							
					}	
				}
				
				graph.assignAdjacents();
				
				String firstOutput = Integer.toString(graph.dijkstra(startVertice, finishVertice));
				String secondOutput = Integer.toString(graph.flagShortestDistance());

				output.write(firstOutput + "\n");
				output.write(secondOutput);
				
				scan.close();
				
				
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
				
			}
			
			output.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
	}
}
