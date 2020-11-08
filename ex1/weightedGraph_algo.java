package ex1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


public class weightedGraph_algo implements weighted_graph_algorithms{
	
	
	private final String VISITED="VISITED", NOT_YET="NOT YET",NOT_VISITED="NOT VISITED";
	private Queue<Integer> q;
	private weighted_graph myGraph;
	private int source;
	private final int START=-1; 
	
	/**
	 * Basic constructor
	 * @param g0 - to g the graph
 	 */
	public weightedGraph_algo(weighted_graph g0) {
		this.myGraph = g0;
	}

	/**
	 * Basic constructor
	 */
	public weightedGraph_algo() {
		q = new PriorityQueue<Integer>();
		this.myGraph = new weightedGraph_ds();
	}


	@Override
	public void init(weighted_graph g) {
		q = new PriorityQueue<Integer>();
		this.myGraph = g;
	}

	@Override
	public weighted_graph getGraph() {
		return myGraph;
	}

	@Override
	public weighted_graph copy() {
		weighted_graph g = new weightedGraph_ds();
		// add g1 nodes to graph g
		for (node_info n : myGraph.getV()) {
			if (n != null) {
				nodeInfo n1 = new nodeInfo(n.getKey()); // create new node for deep copy
				g.addNode(n1.getKey());
			}
		}
		// add g1 edges to graph g
		Iterator<node_info> n2 = myGraph.getV().iterator();
		for(node_info n1 : myGraph.getV()){
			while(n2.hasNext()) {
				node_info temp = n2.next();
				double w = ((weightedGraph_ds) myGraph).getW(temp.getKey(),n1.getKey());
				if(myGraph.getV(temp.getKey()).contains(n1))
					g.connect(n1.getKey(), temp.getKey(), w);
			}
		}
		return g;
	}

	@Override
	public boolean isConnected() {
		Collection<node_info>   n = myGraph.getV(); //  all vertices in the graph
		if(n.isEmpty()) return true;
		source = n.iterator().next().getKey(); // change the source to be the first node-key in the graph to avoid null pointer..
		bfs(); // do bfs algo to check if visited all connected vertices 
		for(node_info   n1 : myGraph.getV()) {
			//return false  if the node not connected
			if(n1.getInfo() != VISITED ) return false; 
		}
		return true;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		source =src;
		bfs_dist();
		double t=  ((nodeInfo) myGraph.getNode(dest)).getW();
		if(t==Integer.MAX_VALUE) return -1;
		return t;
	}

	@Override
	public List<node_info> shortestPath(int src, int dest) {
			List<node_info> path =  new ArrayList<node_info>(); // the path we want to return
			if (dest==src) { // return the path with only source node
			path.add(new nodeInfo(src));
			return path;
			}
			source = src;
			bfs();
			if (((nodeInfo) myGraph.getNode(dest)).getW()==START) return null; 
			else{ 
				path.add(new nodeInfo(dest)); // adds the dest node
				int t= (int) myGraph.getNode(dest).getTag();
				while (t != START){  // adds from the last node in the path
					path.add(new nodeInfo(t));
					t =  (int) myGraph.getNode(t).getTag();
				}
			}
			return path;// reverse the list then return it
			}
	

	@Override
	public boolean save(String file) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean load(String file) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public static void main(String[] args) {
		
		weighted_graph g = new weightedGraph_ds();
		
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		g.connect(0, 1, 2);
		g.connect(1, 4, 1);
		g.connect(0, 2, 3);
		g.connect(1, 4, 3);
		weighted_graph_algorithms gg = new weightedGraph_algo();
		gg.init(g);
		System.out.println(gg.shortestPathDist(0, 4));
//		System.out.println(gg.shortestPath(0, 2).get(1).getKey());
//		System.out.println(gg.shortestPath(0, 2).get(2).getKey());
	}
	
	public void bfs() {
		for (node_info n : myGraph.getV()) {
				n.setInfo(NOT_VISITED); // color 
		}

		myGraph.getNode(source).setInfo(NOT_YET);
		q.add(source);
		while (!q.isEmpty()) { // check if we finished modifed all nieghbors..
		int u = q.poll();
		if(myGraph.getV(u) != null && !myGraph.getV(u).isEmpty())
		for(node_info n1 : myGraph.getV(u)) { // check every node with nieghbors
			if(myGraph.getNode(n1.getKey()).getInfo() == NOT_VISITED) {
				myGraph.getNode(n1.getKey()).setInfo(NOT_YET);
				q.add(n1.getKey());
			}
		}
		myGraph.getNode(u).setInfo(VISITED);
		}
	}
	public void bfs_dist() {
		for (node_info n : myGraph.getV()) {
				((nodeInfo) n).setW(Integer.MAX_VALUE);
		}
		((nodeInfo) myGraph.getNode(source)).setW(0);

		q.add(source);
		while (!q.isEmpty()) { // check if we finished modifed all nieghbors..
		int u = q.poll();
		if(myGraph.getV(u) != null && !myGraph.getV(u).isEmpty())
		for(node_info n1 : myGraph.getV(u)) { // check every node with nieghbors
				if(((nodeInfo) myGraph.getNode(u)).getW()+myGraph.getEdge(n1.getKey(), u) <((nodeInfo) myGraph.getNode(n1.getKey())).getW()) {
					((nodeInfo) myGraph.getNode(n1.getKey())).setW(((nodeInfo) myGraph.getNode(u)).getW()+myGraph.getEdge(n1.getKey(), u));
					q.add(n1.getKey());
				}				
			}
		}
	}

}
