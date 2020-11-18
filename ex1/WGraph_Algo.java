package ex1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


public class WGraph_Algo implements weighted_graph_algorithms, Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1074869663530623414L;
	private final String VISITED="VISITED", NOT_YET="NOT YET",NOT_VISITED="NOT VISITED";
	private Queue<Integer> q;
	private weighted_graph myGraph= new WGraph_DS();
	private int source;
	private final int START=-1; 


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
	public weighted_graph copy() { //deep copy..
		weighted_graph g = new WGraph_DS();
		// add g1 nodes to graph g
		for (node_info n : myGraph.getV()) {
			if (n != null) {
				nodeInfo n1 = new nodeInfo(n.getKey()); // create new node for deep copy
				g.addNode(n1.getKey());
			}
		}
		
		// add g1 edges to graph g
		for(node_info n1 : myGraph.getV()){
			for(node_info n2 : myGraph.getV(n1.getKey())) {
				if(myGraph.getV(n1.getKey()).contains(n2)) {
					double w = myGraph.getEdge(n2.getKey(),n1.getKey());
					g.connect(n1.getKey(), n2.getKey(), w);
				}

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
		if(myGraph.getNode(src) == null ||myGraph.getNode(dest) == null ) return -1;
		source =src;
		dijikstra_dist(); 
		double t=  ((nodeInfo) myGraph.getNode(dest)).getW();
		if(t==Integer.MAX_VALUE) return -1; // if theres no path between src and dest nodes
		return t;
	}

	@Override
	public List<node_info> shortestPath(int src, int dest) {
		List<node_info> ans_path = new LinkedList<node_info>();
		if(myGraph.getNode(src) == null ||myGraph.getNode(dest) == null ) return null;
		double dest_w = ((nodeInfo) myGraph.getNode(dest)).getW();
			source=src;
			dijikstra_dist();
			if(dest_w==-1) return ans_path;
			ans_path.add(myGraph.getNode(dest)); // adds the dest node
			int t= (int) myGraph.getNode(dest).getTag();
			while (t != START){  // adds from the last node in the path
				ans_path.add(myGraph.getNode(t));
				t = (int) myGraph.getNode(t).getTag();
			}		
		return  list_reverse(ans_path);
			}
	

	@Override
	public boolean save(String file) {
		try {
			FileOutputStream out_file = new FileOutputStream(file);
			ObjectOutputStream oout = new ObjectOutputStream(out_file);
			weighted_graph myGraph2= new WGraph_DS();
			oout.writeObject(myGraph2); // save the graph to file
			
			oout.close();
			out_file.close();
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}

	
	}


	@Override
	public boolean load(String file) {
		try {
			ObjectInputStream object=new ObjectInputStream(new FileInputStream(file));
			 // load/copy the graph from the file
			this.myGraph=  (weighted_graph) object.readObject();		
			object.close();
			return true;
		}
        catch(IOException e)  { 
            return false; 
        } 
 
        catch(ClassNotFoundException e) { 
           return false;
        } 

	    }

	
	
	
	
///////////////////////////////////////////// constructors ////////////////////////////////////////
	
	/**
	 * Basic constructor
	 * @param g0 - to g the graph
 	 */
	public WGraph_Algo(weighted_graph g0) {
		this.myGraph = g0;
	}

	/**
	 * Basic constructor
	 */
	public WGraph_Algo() {
		q = new PriorityQueue<Integer>();
		this.myGraph = new WGraph_DS();
	}
	
	
	
	
	
///////////////////////////////////////////// private functions ////////////////////////////////////////
	
	
	/**
	 * bfs function to find if the graph is connected 
	 */
	private void bfs() {
		for (node_info n : myGraph.getV()) {
				n.setInfo(NOT_VISITED); // color 
		}

		myGraph.getNode(source).setInfo(NOT_YET);
		q.add(source);
		while (!q.isEmpty()) { // check if we finished modified all nieghbors..
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
	
	/**
	 * updated my bfs function to dijisktra with adding check weight condtion 
	 * and set the dest in every local node weight 
	 */
	private void dijikstra_dist() {
		for (node_info n : myGraph.getV()) {
				((nodeInfo) n).setW(Integer.MAX_VALUE);
				n.setTag(START);
		}
		((nodeInfo) myGraph.getNode(source)).setW(0);

		q.add(source);
		while (!q.isEmpty()) { // check if we finished modified all nieghbors..
		int u = q.poll();
		if(myGraph.getV(u) != null && !myGraph.getV(u).isEmpty())
		for(node_info n1 : myGraph.getV(u)) { // check every node with nieghbors
				if(((nodeInfo) myGraph.getNode(u)).getW()+myGraph.getEdge(n1.getKey(), u) <((nodeInfo) myGraph.getNode(n1.getKey())).getW()) {
					((nodeInfo) myGraph.getNode(n1.getKey())).setW(((nodeInfo) myGraph.getNode(u)).getW()+myGraph.getEdge(n1.getKey(), u));
					q.add(n1.getKey());
					myGraph.getNode(n1.getKey()).setTag(u);
				}				
			}
		}
	}
	
	/**
	 * reverse list of node_data
	 * @param a - the list to reverse 
	 * @return reversed list
	 */
	private List<node_info> list_reverse(List<node_info> a) {
		List<node_info> b =  new ArrayList<node_info>();
		for (int i = 0; i < a.size(); i++) {
			b.add(a.get(a.size()-i-1));// copy from the end of the list
		}
		return b;
		
	}
	
	}
