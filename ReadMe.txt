------------------------------------------------------------------------

*** Important for Running ***

When testing this program, be sure, in the command line, add "-" in front of "show", "directions" and "meridianmap".


*** Big O analysis ***
For Dijkstra algorithm, it has a O(E log V). The PriorityQueue can make the time use to be logV, as there are V number of vertices.
Then, It needs to check the each edge. As a result, eventually, it takes a E log V time to implement.

For Minimum Spanning Tree algorithm, I use the lazy version of Prim's MST. It has a O(E log E) time span. It is because I am using the PriorityQueue to store the edges, which makes the run time to be log E when seaching the smallest edge to poll out. Then, since there are in total E number of edges I need to check. So it takes much time to finish the process of finding all spanning paths.

As the data set is becoming larger, this algorithm will take much more time to run because it process needs a lot of time processing all data and add them together to find the minimum spanning tree. 

------------------------------------------------------------------------
There are 5 class for running this project.

1. Main (use the command line to implement the whole project)
2. Node
3. Edge
4. Graph (two algorithms are in this class)
5. Draw (it is contained in the main class)

------------------------------------------------------------------------

For this project, I first make sure all the nodes are stored in the hashmap and all edges are in the edgelist for future implement.
Then, I tried to draw the graph. I problem comes when I was trying to draw the graph because it takes much time. I then tried to use Hashmap instead of linkedlis to find each edge to draw the graph.

Then, I searched the book for the shorest path algorithm, I was trying to use the priority queue but found some errors. I believe that is because I forgot to first remove the nodes in the priority queue and then add them back. Eventually, it worked.









