import java.util.HashMap;
import java.util.Map;

public class City {

	String name;
	Integer troops;
	Integer arrayIndex = null;

	
	Map<City, Integer> adjacentCities = new HashMap<>();
	Map<City, Integer> initialdists = new HashMap<>();
	
	City(String name) {
		
		this.name = name;
		
	}
	
	City(String name, Integer troops) {
		
		this.name = name;
		this.troops = troops;
		
		
	}
	
	public Integer getDist(City city) {
		
		if (this.adjacentCities.containsKey(city)) {
			
			return adjacentCities.get(city);
			
		} else {
			
			return 0;
			
		}
		
	}
	
	public void setDist(City city, Integer dist) {
		
		int initialdist = getDist(city);
		int newdist = initialdist += dist;
		adjacentCities.replace(city, newdist);
		
		
	}
	
	public int getInitialDist(City city) {
		
		if (this.initialdists.containsKey(city)) {
			
			return this.initialdists.get(city);
			
		}
		
		return -1;
		
	}
		
}
