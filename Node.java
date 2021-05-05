import java.util.*;

public class Node implements Comparable<Node> {

    private String id;
    private double longitude;
    private double latitude;
    private boolean isVisited;
    private double distance;
    public ArrayList<Edge> edge;
    private ArrayList<Node> neighbour;
    private ArrayList<Node> shortestPath;
    private Node previous;

    public Node(){
        this.distance = Double.MAX_VALUE;
    }
    public Node(String id, double latitude, double longitude) {
        this.neighbour = new ArrayList<>();
        this.shortestPath = new ArrayList<>();
        this.longitude = longitude;
        this.latitude = latitude;
        this.isVisited = false;
        this.previous = null;
        this.distance = Double.MAX_VALUE;
        this.edge = new ArrayList<>();
        this.id = id;
    }

    public String getId() { return id; }

    public void setIsVisited(boolean isVisited) { this.isVisited = isVisited; }

    public void setWeight(double distance) { this.distance = distance; }

    public void addNeighbor(Node neighbor) { this.neighbour.add(neighbor); }

    public void setPathList(ArrayList<Node> path) { this.shortestPath = path; }

    public void setPrevious(Node previous) { this.previous = previous; }

    public double getLon() { return longitude; }

    public double getLat() { return latitude; }

    public boolean getIsVisited() { return isVisited; }

    public double getDistance() { return distance; }

    public ArrayList<Node> getNeighbour() { return neighbour; }

    public ArrayList<Node> getPathList() { return shortestPath; }

    public Node getPrevious() { return previous; }

    public static double computeEdge(Node start, Node end) {
        int EARTH_RADIUS = 3959;
        double startLat = start.getLat();
        double startLon = start.getLon();
        double endLat = end.getLat();
        double endLon = end.getLon();
        double dLat = Math.toRadians(endLat - startLat);
        double dLon = Math.toRadians(endLon - startLon);
        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(startLat) * Math.cos(endLat) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    @Override
    public int compareTo(Node node) {
        if(this.getDistance() > node.getDistance()) {
            return 1;
        } else if(this.getDistance() == node.getDistance()) {
            return 0;
        } else {
            return -1;
        }
    }
    @Override
    public String toString() {
        String result = "";
        for(Node i : shortestPath) {
            result += (i.getId() + " ");
        }
        return result;
    }
}
