
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Graph {
	
	public int size;
	public int flagNum;
	
	public HashSet<Vertice> verticeList;
	PriorityQueue<Vertice> unsettled;
	
	public HashSet<Vertice> flagList;
	
	public Graph(int size, int flagNum) {
		
		this.size = size;
		this.flagNum = flagNum;
		this.flagList = new HashSet<Vertice>(flagNum);
		this.verticeList = new HashSet<Vertice>(size);
		this.unsettled = new PriorityQueue<Vertice>(size);
		
	}
	
	public void addVertice(Vertice vertice) {
		
		verticeList.add(vertice);
		
	}
	
	public void addFlag(Vertice vertice) {
		
		flagList.add(vertice);
	}
	
	public void assignAdjacents() {
		
		Object[] verticeArray = verticeList.toArray();
		
		for(int i = 0; i < verticeArray.length; i++) {
			
			Vertice vertice = (Vertice) verticeArray[i];
			
			for (int j = i + 1; j < verticeArray.length; j++) {
				
				Vertice otherVertice = (Vertice) verticeArray[j];
				
				if (vertice.isConnectedWith(otherVertice)) {
					int distance = vertice.adjLineDist(otherVertice);
					vertice.addAdjacents(otherVertice, distance);
					otherVertice.addAdjacents(vertice, distance);
					continue;
				}
				
				continue;
				
			}
		}
	}
	
	public void resetDistances() {
		
		for (Vertice vertice : verticeList) {
			vertice.resetDistance();
		}
		
	}
	
	public int dijkstra(Vertice start, Vertice finish) {
	
		this.resetDistances();	
		this.unsettled.clear();
	
		Set<Vertice> settled = new HashSet<>();
		
		start.setDistance(0, null);
		unsettled.add(start);
		
		while (!(settled.contains(finish)) && !(unsettled.isEmpty() && settled.size() != verticeList.size())) {
			
			
			Vertice current = unsettled.poll();
			
			if (settled.contains(current)) {
				continue;
			}
			
			
			for (Map.Entry<Vertice, Integer> adjacentDist : current.adjacentDists.entrySet()) {
				Vertice adjacentVertice = adjacentDist.getKey();
				Integer distance = adjacentDist.getValue();
					
					if (!(settled.contains(adjacentVertice))) {
						adjacentVertice.setDistance(current.getDistancetoSrc() + distance, current);
					}
					
					if(!(unsettled.contains(adjacentVertice))) {
						unsettled.add(adjacentVertice);
					}	
			}
						
			settled.add(current);
	
		}
				
		if(!(settled.contains(finish))) {
			return -1;
		}
		return finish.getDistancetoSrc();
			
	}
	
	public int flagShortestDistance() {
				
		PriorityQueue<Integer> distancepq = new PriorityQueue<Integer>();
		
		for (Vertice vertice : flagList) {
			
			for (Vertice otherVertice : flagList) {
				
				if ((vertice != null || otherVertice != null) && vertice != otherVertice && otherVertice.hasFlag)  {
			
					int dist = dijkstra(vertice, otherVertice);
					vertice.flagDist += dist;
					otherVertice.flagDist += dist; 				
				}
			}
			
			vertice.hasFlag = false;
			distancepq.add(vertice.flagDist);
			
		}
		
		return distancepq.poll();
	}
}
