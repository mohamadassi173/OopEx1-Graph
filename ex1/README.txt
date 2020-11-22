Second assignment in object oriented programming course
This project is about building weighted undirected graph to operate some of the algorithms (check if the graph is connected, return the shortest path and shortest path dest).

classes list -
node_info:interface that represent the set of operations applicable on a vertex in an undirectional unweighted graph.

NodeInfo: class that implements "node_info" interface.

weighted_graph: interface that represent an undirectional unweighted graph.

WGraph_DS :class that implements "weighted_graph" interface.

weighted_graph_algorithms:interface represents Graph Theory algorithms.

WGraph_Algo: class that implements "weighted_graph_algorithms" interface.



Data Structures: in "WGraph_DS"

HashMap<Integer, node_info> : represents the vertices in graph.

HashMap<Integer, HashMap<node_info, Double>>: represent the vertices and his neighbours.
the key of the first HashMap is key of the node,and each node Have a HashMap,
the key of this HashMap is the neighbours and the Value is the weight.



Algorithms:

BFS is a traversing algorithm where you should start traversing from a selected node 
(source or starting node) and traverse the graph layerwise thus exploring the neighbour nodes.
and must then move towards the next-level neighbour nodes.

isConnected function: by doing BFS on any vertex in graph,if all vertices is visited that 
mean the graph is connected if one of vertices not visited the graph is not connected.

Shortestpathdist: I improve the bfs algorthim by adding weight while the algorithm search about the shortestpath,it's like dijkstra algorithm

Shortestpath: doing bfs on src vertex,then we have a distance array of src vertex to all vertices in graph, 
for printing the the path we use the pred array that present immediate predecessor of the each vertex.


github link:
https://github.com/mohamadassi173/OopEx1-Graph.git
