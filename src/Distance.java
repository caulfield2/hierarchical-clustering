import java.util.*;

public class Distance {

	public static void main(String[] args) {
		List<Cluster> clusterList = new ArrayList<Cluster>();
		int numClusters = 2;
		
		// Create a cluster for each city in the problem statement
		clusterList.add(new Cluster(new City("Madison", -89, 43, true)));
		clusterList.add(new Cluster(new City("Seattle", -122, 48, true)));
		clusterList.add(new Cluster(new City("Boston", -71, 42, true)));
		clusterList.add(new Cluster(new City("Vancouver", -123, 49, false)));
		clusterList.add(new Cluster(new City("Winnipeg", -97, 50, false)));
		clusterList.add(new Cluster (new City("Montreal", -71, 46, false)));
		
		int iteration = 1;
		while (clusterList.size() > numClusters) {
			double currMaxDistance = 0;
			List<Cluster> currFarthestClusters = new ArrayList<Cluster>();
			
			for (Cluster clusterA : clusterList) {
				for(Cluster clusterB : clusterList) {
					if (clusterA != clusterB) {
						for(City cityA : clusterA.cities) {
							for(City cityB : clusterB.cities) {
								// Euclidean distance between cities
								double distance = Math.sqrt(Math.pow((cityA.x - cityB.x), 2) + Math.pow((cityA.y - cityB.y), 2));
								// Logic for clustering by complete linkage - single linkage would be the opposite
								if (distance > currMaxDistance) {
									currMaxDistance = distance;
									currFarthestClusters.clear();
									currFarthestClusters.add(clusterA);
									currFarthestClusters.add(clusterB);
								}
							}
						}
					}
				}
			}
			
			// Combine the two farthest clusters
			Cluster keep = currFarthestClusters.get(0);
			Cluster delete = currFarthestClusters.get(1);
			
			keep.cities.addAll(delete.cities);
			clusterList.remove(delete);
			
			System.out.println("Iteration " + iteration);
			System.out.println("Max distance: " + currMaxDistance);
			
			for (Cluster cluster : clusterList) {
				for (City city : cluster.cities) {
					System.out.println(city.name);
				}
				System.out.print("\n");
			}
			iteration++;
		}
	}

	public static class City {
		String name;
		boolean isAmerican;
		double x;
		double y;
		
		public City(String name, double x, double y, boolean isAmerican) {
			this.name = name;
			this.x = x;
			this.y = y;
			this.isAmerican = isAmerican;
		}
	}
	
	public static class Cluster {
		List<City> cities;
		
		public Cluster(City city) {
			this.cities = new ArrayList<City>(Arrays.asList(city));
		}
	}
	
}
