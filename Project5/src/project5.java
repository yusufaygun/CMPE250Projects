import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class project5 {

	public static void main(String[] args) throws FileNotFoundException {
		
		try {
			FileWriter output = new FileWriter(args[1]);
			
			try {
				
				File input = new File(args[0]);
				Scanner scan = new Scanner(input);
				
				int cityNum = Integer.parseInt(scan.nextLine());
				
				Graph graph = new Graph(cityNum);
				
				for (int i = 0; i < 6; i++) {
					
					graph.addRegion(new City("r" + i, Integer.parseInt(scan.next())) , i);
					
				}
				scan.nextLine();
				
				
				String[] regionLines = new String[6];
				
				for (int i = 0; i < 6; i++) {

					regionLines[i] = scan.nextLine();					
					
				}
				
				
				while(scan.hasNextLine()) {
					
					String[] line = scan.nextLine().split(" ");
					graph.addLine(line);
					
				}
				
				for (int i = 0; i < 6; i++) {
					
					graph.addRegionAdjacents(regionLines[i], i);
					
				}
				
				graph.createGraph();
				
				String maxFlow = Integer.toString(graph.maxFlowFF());
				
				output.write(maxFlow);
				output.write(graph.minCut());
				
				
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


