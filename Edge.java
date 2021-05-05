import java.util.*;

public class Edge implements Comparable<Edge>{
    private double edge;
    private Node start;
    private Node end;
    private String ID;
    public ArrayList<Edge> path;

    public Edge(String ID, Node start, Node end, double weight){
        this.edge = weight;
        this.start = start;
        this.end = end;
        this.ID = ID;
        this.path = new ArrayList<>();
    }
    public double getEdge(){return this.edge; };
    public Node getStartNode(){ return this.start; }
    public Node getEndNode(){ return this.end; }
    public String getID(){ return this.ID; }


    @Override
    public int compareTo(Edge edge1) {
        if(this.getEdge() > edge1.getEdge()) {
            return 1;
        } else if(this.getEdge() == edge1.getEdge()) {
            return 0;
        } else {
            return -1;
        }
    }
    @Override
    public String toString() {
        String result = "";
        for(Edge i : path) {
            result += (i.getID() + " ");
        }
        return result;
    }
}
