import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.*;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        String file = args[0];
        Graph g = new Graph(file);
        ArrayList<String> terminal = new ArrayList<>();
        for(String s: args) {
            terminal.add(s);
        }

        if(terminal.contains("-directions")) {
            String to = args[(args.length-1)];
            String from = args[args.length -2];
            Node origin = g.vertex.get(from);
            Node destination = g.vertex.get(to);
            ArrayList<Node> n = g.shortestPath(origin, destination);
            System.out.println("Path: " + origin.toString());
            System.out.println("Distance: " + destination.getDistance() + " Miles");

            if(terminal.contains("-show")) {
                JFrame frame = new JFrame();
                Draw show = new Draw(g, n);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 700);
                frame.setResizable(true);
                frame.add(show);
                frame.setVisible(true);
            }
        }
        else if (terminal.contains("-meridianmap")){
            ArrayList<Edge> n = g.MinimumSpanningTree();
            if(terminal.contains("-show")) {
                JFrame frame = new JFrame();
                Draw show = new Draw(g, n, "method");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 700);
                frame.setResizable(true);
                frame.add(show);
                frame.setVisible(true);
                System.out.println("Path: ");
                for(Edge each : n)
                    System.out.print(each.getID() + " ");
            }
        }
        else if (terminal.contains("-show")) {
            JFrame frame2 = new JFrame();
            Draw show = new Draw(g);
            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame2.setSize(600, 700);
            frame2.setResizable(true);
            frame2.add(show);
            frame2.setVisible(true);
        }
    }
}

class Draw extends JPanel {
    double length;
    double width;
    Graph graph;

    HashMap<Node, Node> sp = new HashMap<>();
    List<Node> path;
    ArrayList<Edge> input;

    public Draw(Graph g) {
        this.graph = g;
        repaint();
    }

    public Draw(Graph g, List<Node> l) {
        if (l == null) {
            System.out.print("There is no such path");
        } else {
            this.graph = g;
            path = l;
            Queue<Node> nodes = new LinkedList<Node>();
            nodes.addAll(l);
            while (nodes.size() != 1) {
                Node origin = nodes.poll();
                Node dest = nodes.peek();
                sp.put(origin, dest);
            }
            repaint();
        }
    }

    public Draw(Graph g, ArrayList<Edge> l, String method) {
        if (l == null) {
            System.out.println("There is no such path");
        }
        else {
            this.graph = g;
            input = new ArrayList<>();
            input.addAll(l);
            repaint();
        }
    }

    //generates x and y coordinates based on the given latitude and longitude
    public double generateX(Node n) {
        double padding = 0.0;
        if(getWidth() > getHeight()) {
            padding = (getWidth() - getHeight())/2;
        }
        return ((n.getLon()-graph.minLongitude)/(graph.maxLongitude - graph.minLongitude) * width) + padding;
    }

    public double generateY(Node n) {
        double padding = 0.0;
        if(getHeight() > getWidth()) {
            padding = (getHeight() - getWidth())/2;
        }
        return (length - (n.getLat()-graph.minLatitude)/(graph.maxLatitude - graph.minLatitude)*length)+ padding;
    }

    //renders the map
    @Override
    public void paintComponent(Graphics g) {
        length = getHeight();
        width = getWidth();
        Graphics2D g2 = (Graphics2D) g;
        if (getWidth() > getHeight()) {
            length = width = getHeight();
        } else {
            length = width = getWidth();
        }

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("map.png"));
        } catch (IOException e) {}
        int i = 0;
        for (String s : graph.vertex.keySet()) {
            Node node = graph.vertex.get(s);
            int x1 = (int) generateX(node);
            int y1 = (int) generateY(node);

            for (Node destination : node.getNeighbour()) {
                int x2 = (int) generateX(destination);
                int y2 = (int) generateY(destination);

                if(sp.containsKey(node) && sp.get(node).equals(destination)) {
                    i++;
                    g2.setColor(Color.BLUE);
                    g2.setStroke(new BasicStroke(5));
                    g2.drawLine(x1, y1, x2, y2);
                } else {
                    g2.setColor(Color.BLACK);
                    g2.setStroke(new BasicStroke(1));
                    g2.drawLine(x1, y1, x2, y2);
                }
            }

        }
        if(this.input!=null) {
            for (Edge each : input) {
                Node start = each.getStartNode();
                Node end = each.getEndNode();
                int A1 = (int) generateX(start);
                int B1 = (int) generateY(start);
                int A2 = (int) generateX(end);
                int B2 = (int) generateY(end);
                g2.setColor(Color.ORANGE);
                g2.setStroke(new BasicStroke(2));
                g2.drawLine(A1, B1, A2, B2);
            }
        }
    }
}