import java.io.*;
import java.util.*;


public class Graph {
    static HashMap<String, Node> vertex = new HashMap<>();
    static ArrayList<Edge> edgeList = new ArrayList<>();
    double maxLongitude;
    double minLongitude;
    double maxLatitude = maxLongitude = -Double.MAX_VALUE;
    double minLatitude = minLongitude = Double.MAX_VALUE;

    public Graph(String filename) {
        //The following code will help to read each line and split by "\n" in the file
        try {
            File obj = new File(filename);
            Scanner reader = new Scanner(obj);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] content = line.split("\\s+");
                if (content[0].equals("i")) {
                    this.addVertex(content[1], Double.parseDouble(content[2]), Double.parseDouble(content[3]));
                } else if (content[0].equals("r")) {
                    this.addEdge(content[1],content[2], content[3]);
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to read the file");
        }
    }

    public void addVertex(String id, double latitude, double longitude) {
        Node newNode = new Node(id, latitude, longitude);
        vertex.put(id, newNode);

        if (latitude > maxLatitude) {
            maxLatitude = latitude;
        } else if (latitude < minLatitude) {
            minLatitude = latitude;
        }
        if (longitude > maxLongitude) {
            maxLongitude = longitude;
        } else if (longitude < minLongitude) {
            minLongitude = longitude;
        }
    }

    public void addEdge(String road, String id1, String id2) {
        Node a = vertex.get(id1);
        Node b = vertex.get(id2);
        a.addNeighbor(b);
        b.addNeighbor(a);
        double weight = Node.computeEdge(a, b);
        Edge newEdge = new Edge(road, a, b, weight);
        edgeList.add(newEdge);
        a.edge.add(newEdge);
        b.edge.add(newEdge);
    }


    public ArrayList<Node> shortestPath(Node source, Node destination) {
        ArrayList<Node> result = new ArrayList<Node>();
        Node current = new Node();
        source.setWeight(0);
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(source);
        if(source == destination){
            System.out.println("Oh, no!!! The start place is the end place");
            result.add(source);
            source.setPathList(result);
            return result;
        }
        while (!queue.isEmpty() && current != destination) {
            current = queue.poll();
            for (Node each : current.getNeighbour()) {
                double distance = current.getDistance() + Node.computeEdge(current, each);
                if ( distance < each.getDistance()) {
                    queue.remove(each);
                    each.setWeight(distance);
                    each.setPrevious(current);
                    queue.add(each);
                }
            }
        }

        if (destination != null && source != destination) {
            result.add(destination);
            Node previous = destination.getPrevious();
            while (previous != source) {
                result.add(previous);
                previous = previous.getPrevious();
            }
            result.add(source);
            Collections.reverse(result);
            source.setPathList(result);
            return source.getPathList();
        }

        System.out.println("Can not find the destination");
        return null;
    }

    public ArrayList<Edge> MinimumSpanningTree() {
        Node source = edgeList.get(100).getStartNode();
        source.setIsVisited(true);
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<Edge>();
        priorityQueue.addAll(source.edge);
        ArrayList<Node> span= new ArrayList<>();
        ArrayList<Edge> path = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            Edge e = priorityQueue.remove();
            Node startNode = e.getStartNode();
            Node endNode = e.getEndNode();
            if (startNode.getIsVisited() && endNode.getIsVisited()) {
                continue;
            }
            if (!startNode.getIsVisited() && endNode.getIsVisited()) {
                startNode.setIsVisited(true);
                priorityQueue.addAll(startNode.edge);
            }

            if (startNode.getIsVisited() && !endNode.getIsVisited()) {
                endNode.setIsVisited(true);
                priorityQueue.addAll(endNode.edge);
            }
            path.add(e);

        }
        path.get(0).path = path;
        return path;
    }
}



