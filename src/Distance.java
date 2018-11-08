import java.util.*;

public class Distance {

	public static void main(String[] args) {
		List<Cluster> clusterList = new ArrayList<>();
		int numClusters = 2;
		
		// Create a cluster for each city in the problem statement
		clusterList.add(new Cluster(new City("Madison", -89, 43, true)));
		clusterList.add(new Cluster(new City("Seattle", -122, 48, true)));
		clusterList.add(new Cluster(new City("Boston", -71, 42, true)));
		clusterList.add(new Cluster(new City("Vancouver", -123, 49, false)));
		clusterList.add(new Cluster(new City("Winnipeg", -97, 50, false)));
		clusterList.add(new Cluster (new City("Montreal", -74, 46, false)));
		
		int iteration = 1;
		while (clusterList.size() > numClusters) {
			double currMinDistance = 1000000;
			List<Cluster> currClosestClusters = new ArrayList<>();
			
			for (Cluster clusterA : clusterList) {
				for(Cluster clusterB : clusterList) {
					if (clusterA != clusterB) {
                        double maxDistanceBetweenClusters = 0;

                        for(City cityA : clusterA.cities) {
							for(City cityB : clusterB.cities) {
								// Euclidean distance between cities
								double distance =
										Math.sqrt(Math.pow((cityA.x - cityB.x), 2) + Math.pow((cityA.y - cityB.y), 2));

								// Logic for clustering by complete linkage - single linkage would be the opposite
								if (distance > maxDistanceBetweenClusters) {
                                    maxDistanceBetweenClusters = distance;
								}
							}
						}

						if (maxDistanceBetweenClusters < currMinDistance) {
                            currClosestClusters.clear();
                            currClosestClusters.add(clusterA);
                            currClosestClusters.add(clusterB);
                            currMinDistance = maxDistanceBetweenClusters;
                        }
					}
				}
			}
			
			// Combine the two closest clusters
			Cluster keep = currClosestClusters.get(0);
			Cluster delete = currClosestClusters.get(1);
			
			keep.cities.addAll(delete.cities);
			clusterList.remove(delete);
			
			System.out.println("Iteration " + iteration);
			System.out.println("Min distance: " + currMinDistance);
			
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
			this.cities = new ArrayList<>(Arrays.asList(city));
		}
	}
	
}
