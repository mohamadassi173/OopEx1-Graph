package ex1.src;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;



public class WGraph_DS implements weighted_graph, Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1074869663530623414L;
	private HashMap<Integer, node_info> nodes;
	private HashMap<Integer, HashMap<node_info, Double>> edges;
	private int MC;
	private int edges_size;	
	
	
	
	
	//// Basic constructor
	public WGraph_DS() {
		nodes = new HashMap<Integer, node_info>();
		edges =  new HashMap<Integer, HashMap<node_info, Double>>();
		edges_size=0;
		MC=0;
	}
	
	@Override
	public node_info getNode(int key) {
		return nodes.get(key);
	}

	@Override
	public boolean hasEdge(int node1, int node2) {
		if(!edges.containsKey(node1)) return false;
		return edges.get(node1).containsKey(nodes.get(node2));
	}

	@Override
	public double getEdge(int node1, int node2) {
		if(!edges.containsKey(node1)) return -1;
		else if(!edges.get(node1).containsKey(nodes.get(node2))) return -1; // if there's no edge between node1 and node2
		return edges.get(node1).get(nodes.get(node2));
	}

	@Override
	public void addNode(int key) {
		node_info add = new nodeInfo(key);
		nodes.put(key, add);
		
	}

	@Override
	public void connect(int node1, int node2, double w) {
		if(node1==node2) return;
		if(nodes.get(node1)==null || nodes.get(node2)==null) return;
		if(!hasEdge(node1, node2)) edges_size++;
		else {
			// to not increase MC without making any change(update or connect)
			if(edges.get(node1).get(nodes.get(node2))!=w) MC++;
		}
		
//		// if node1 not in edges -> put node1 to the edges hash with node2 and weigt w
		if(!edges.containsKey(node1)) {
			HashMap<node_info, Double> node2_e = new HashMap<node_info, Double>();
			node2_e.put(nodes.get(node2), w);
			edges.put(node1, node2_e);
		}
		
		if(!edges.containsKey(node2)) {
			HashMap<node_info, Double> node1_e = new HashMap<node_info, Double>();
			node1_e.put(nodes.get(node1), w);
			edges.put(node2, node1_e);
		}
		edges.get(node1).put(nodes.get(node2), w);
		edges.get(node2).put(nodes.get(node1), w);	
	}

	@Override
	public Collection<node_info> getV() {
		return nodes.values();
	}

	@Override
	public Collection<node_info> getV(int node_id) {
		if(edges.get(node_id)==null) return null;
		return edges.get(node_id).keySet();
	}

	@Override
	public node_info removeNode(int key) {
		node_info n=null;
		if(nodes.containsKey(key)) n = nodes.get(key); // To return it after remove
		else return n; // key is not exist in graph
		
			// remove all edges with the node we removed from the graph
			Collection<node_info> connectedNodes = getV(key); // all jeran(Neighbours)
			if(connectedNodes!=null)
			for(node_info r : connectedNodes) {
				if(r!=null)
				edges.get(r.getKey()).remove(nodes.get(key));
			}
			
			// decrease the edges size
			if(edges.get(key)!=null) {
			int edges_removed =  edges.get(key).size();
			edges_size -= edges_removed;
			}
			// remove the node from the graph.
			nodes.remove(key);
			MC++;
		return n;
	}

	@Override
	public void removeEdge(int node1, int node2) {
		// check if node1 and node2 connected to decrease edge size and remove the edge
		if(hasEdge(node1, node2)) {
			MC++;
			edges_size--;
			edges.get(node1).remove(nodes.get(node2));
			edges.get(node2).remove(nodes.get(node1));
		}
	}

	@Override
	public int nodeSize() {
		
		return nodes.size();
	}

	@Override
	public int edgeSize() {

		return edges_size;
	}

	@Override
	public int getMC() {
		return MC;
	}
	@Override
	public String toString() {
		String g = "";
		for(node_info n : nodes.values()) {
			g+=n.toString()+",";
		}
		g+="\n";
		
		for(node_info n1 : nodes.values()) {
			for(node_info n2 : getV(n1.getKey())) {
				g+=n1.getKey()+"," + n2.getKey() + "," + getEdge(n1.getKey(), n2.getKey()) + "\n";
			}
		}
		return g;
	}
	
	@Override
	public boolean equals(Object g2) {
		boolean bool = true;
		if(g2 instanceof weighted_graph) {
		WGraph_DS g1 = (WGraph_DS) g2;//to cast the object one time..
		
		// checking if both graphs have the same vertices
		bool &=	eq_vertices(getV(), g1.getV());
		bool &=	eq_vertices(g1.getV(), getV());
		
		// checking if both graphs have the same edges
		bool &= eq_edges(this.edges,g1);
		bool &= eq_edges(g1.edges,this);
		}
		
		return bool;
	}

	
	
	
	
	
	
	
	
///////////////////////////////////////////// private functions ////////////////////////////////////////

	
	/**
	 * check if all e1 hash-map values are in g2 graph edges
	 * @param e1 - edges of the first graph
	 * @param g2 - graph of the second graph
	 * @return
	 */
	private boolean eq_edges(HashMap<Integer, HashMap<node_info, Double>> e1, WGraph_DS g2) {
		for(int k1 : e1.keySet()) {
			for(node_info n : e1.get(k1).keySet()) {
				int k2 = n.getKey();
				double w = e1.get(k1).get(n);
				if(!g2.hasEdge(k1, k2)) return false;
				if(g2.getEdge(k1, k2)!=w) return false;
			}	
		}
		return true;
	}

	
	/**
	 * check if all v1 vertices are in v2 
	 * @param v1 - vertices in graph 1
	 * @param v2 - vertices in graph 2 
	 * @return
	 */
	private boolean eq_vertices(Collection<node_info> v1, Collection<node_info> v2) {
		for(node_info n1 : v1) {
			if(!v2.contains(n1)) return false;
		}	
	return true;	
	}

}
