
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Vertice implements Comparable<Vertice>{

	public String line;
	public String name;
	
	public String[] adjLineArray;
	public Map<Vertice, Integer> adjacentDists;
	
	private Integer distToSrc;
	
	public boolean hasFlag = false;
	public int flagDist = 0;
		
	public Vertice(String name) {
		
		this.name = name;
		adjacentDists = new HashMap<>();
		this.distToSrc = Integer.MAX_VALUE;
		
	}
	
	public void addAdjLine(String[] line) {
		
		this.adjLineArray = Arrays.copyOfRange(line, 1, line.length);
		
	}
	
	public int adjLineDist(Vertice vertice) {
		
		if (Arrays.asList(adjLineArray).contains(vertice.name)) {
			
			int idx = Arrays.asList(adjLineArray).indexOf(vertice.name);
			return Integer.parseInt(adjLineArray[idx + 1]);
			
		}
		
		int idx = Arrays.asList(vertice.adjLineArray).indexOf(this.name);
		return Integer.parseInt(vertice.adjLineArray[idx + 1]);		
		
	}
	
	
	
	public void addAdjacents(Vertice vertice, Integer distance) {
		
		adjacentDists.put(vertice, distance);
		
	}
	
	public boolean isConnectedWith(Vertice vertice) {
		
		if (vertice == null) {
			
			return false;
			
		}
		
		if (Arrays.asList(this.adjLineArray).contains(vertice.name) || Arrays.asList(vertice.adjLineArray).contains(this.name)) {
						
			return true;
			
		}
		
		return false;
		
	}
	
	public int distanceWith(Vertice vertice) {
	
		return adjacentDists.get(vertice);
			
	}
	
	public void setDistance(int dist, Vertice vertice) {
		
		if (this.distToSrc > dist) {
			
			distToSrc = dist;
			
		}
	}
	
	public void resetDistance() {
		
		this.distToSrc = Integer.MAX_VALUE;
		
	}
	
	public Integer getDistancetoSrc() {
		
		if (distToSrc == null) {
			return -1;
		}
		
		return distToSrc;
	}
	
	
	@Override
	public int compareTo(Vertice vertice) {
		if (this.distToSrc.compareTo(vertice.distToSrc) != 0) {
			return this.distToSrc.compareTo(vertice.distToSrc);
		} else {
			return this.name.compareTo(vertice.name);
		}
	}
	
}
