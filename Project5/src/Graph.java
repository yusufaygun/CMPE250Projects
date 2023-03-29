import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Graph {
	
	City[] regionList;
	Map<String, City> citiesName;
	Integer cityNum;
	City source = new City("source");
	City KL;
	Integer size;
	
	City[] graph;
	
	
	Graph(Integer cityNum) {
	
		this.cityNum = cityNum + 1;
		this.regionList = new City[6];
		this.citiesName = new HashMap<>(this.cityNum);
		this.size = cityNum + 8;
		
	}
	
	
	public void addRegion(City region, Integer index) {
		
		this.regionList[index] = region;
		source.adjacentCities.put(region, region.troops);
		source.initialdists.put(region, region.troops);
		
	}
	
	
	
	public void addLine(String[] line) {
		
		if (citiesName.containsKey(line[0])) {
			
			City city = citiesName.get(line[0]);
			
			for (int i = 1; i < line.length; i = i + 2) {
				
				if (citiesName.containsKey(line[i])) {
					
					City adjacentCity = citiesName.get(line[i]);
					city.adjacentCities.put(adjacentCity, Integer.parseInt(line[i+1]));
					city.initialdists.put(adjacentCity, Integer.parseInt(line[i+1]));
					
				} else {
					City adjacentCity = new City(line[i]);
					city.adjacentCities.put(adjacentCity, Integer.parseInt(line[i+1]));
					city.initialdists.put(adjacentCity, Integer.parseInt(line[i+1]));
					citiesName.put(line[i], adjacentCity);
				}	
			}
		} else {
			
			City city = new City(line[0]);
			citiesName.put(line[0], city);
			
			for (int i = 1; i < line.length; i = i + 2) {
				
				if (citiesName.containsKey(line[i])) {
					
					City adjacentCity = citiesName.get(line[i]);
					city.adjacentCities.put(adjacentCity, Integer.parseInt(line[i+1]));
					city.initialdists.put(adjacentCity, Integer.parseInt(line[i+1]));
					
				} else {
					
					City adjacentCity = new City(line[i]);
					city.adjacentCities.put(adjacentCity, Integer.parseInt(line[i+1]));
					city.initialdists.put(adjacentCity, Integer.parseInt(line[i+1]));
					citiesName.put(line[i], adjacentCity);
					
				}	
			}
		}
	}
	
	public void addRegionAdjacents(String line, int index) {
		
		String[] lineArray = line.split(" ");
		
		City region = regionList[index];
		
		for (int i = 1; i < lineArray.length; i = i + 2) {
			
			City city = citiesName.get(lineArray[i]);
			
			region.adjacentCities.put(city, Integer.parseInt(lineArray[i+1]));
			region.initialdists.put(city, Integer.parseInt(lineArray[i+1]));
			
		}
	}
	
	public void createGraph() {
		
		this.graph = new City[size];
		
		graph[0] = source;
		source.arrayIndex = 0;
		
		for (int i = 0; i < 6; i++) {
			
			graph[i + 1] = regionList[i]; 
			regionList[i].arrayIndex = i + 1;
			
		}
		
		City kl = citiesName.get("KL");
		this.KL = kl;
		
		graph[size - 1] = kl;
		kl.arrayIndex = size - 1;
		
		int j = 7;
		
		for (Map.Entry<String, City> cityPair : citiesName.entrySet()) {
			
			if (cityPair.getValue().equals(kl)) {
				continue;
			}
			
			City city = cityPair.getValue();
			graph[j] = city;
			city.arrayIndex = j;
			j++;
			
		}	
	}
	
	public boolean isValidPath(City[] rGraph, City source, City sink, City[] parent) {
		
		
		boolean[] visited = new boolean[size];
		
		LinkedList<City> queue = new LinkedList<City>();
		
		queue.add(source);
		visited[0] = true;
		parent[0] = null;
		
		
		while (queue.size() != 0) {
			
			City current = queue.poll();
			
			for (int v = 0; v < size; v++) {
				
				if (visited[v] == false && current.getDist(rGraph[v]) > 0) {
					
					if (rGraph[v] == sink) {
						
						parent[rGraph[v].arrayIndex] = current;
						return true;
						
					}
					queue.add(rGraph[v]);
					parent[rGraph[v].arrayIndex] = current;
					visited[rGraph[v].arrayIndex] = true;
					
				}	
			}
		}
		
		return false;
		
	}
	
	public void dfs(City source, boolean[] visited) {
		
		visited[source.arrayIndex] = true;
		
		for (int i = 0; i < size; i++) {
			
			if(graph[source.arrayIndex].getDist(graph[i]) > 0 && !visited[i]) {
				
				dfs(graph[i], visited);
				
			}
			
		}
		
	}
	
	
	public int maxFlowFF() {
		
		City source = this.source;
		City sink = this.KL;
		
		City[] rGraph = this.graph;
		
		
		City[] parent = new City[size];
		
		int maxFlow = 0;
		
		while (isValidPath(rGraph, source, sink, parent)) {
			
			int pathFlow = Integer.MAX_VALUE;
			
			for (City v = sink; v != source; v = parent[v.arrayIndex]) {
				
				City u = parent[v.arrayIndex];
				pathFlow = Math.min(pathFlow, rGraph[u.arrayIndex].getDist(v));
				
			}
			
			for (City v = sink; v != source; v = parent[v.arrayIndex]) {
				
				City u = parent[v.arrayIndex];
				rGraph[u.arrayIndex].setDist(v, -pathFlow);
				rGraph[v.arrayIndex].setDist(u, pathFlow);
				
			}
			
			maxFlow += pathFlow;
			
		}
		
		return maxFlow;
		
	}
	
	public String minCut() {
		
		String result = "";
				
		boolean[] isVisited = new boolean[size];
		
		dfs(source, isVisited);
		
		
		for (int i = 0; i < size; i++) {
			
			for (int j = 0; j < size; j++) {
				
				if(graph[i].getInitialDist(graph[j]) > 0 && isVisited[i] && !isVisited[j]) {
					
					if (i == 0) {
					
						result += "\n" + graph[j].name;
						
						continue;
					}
	
					result += "\n" + graph[i].name + " " + graph[j].name;
					
				}			
			}
		}
		
	return result;
	
	}

}
