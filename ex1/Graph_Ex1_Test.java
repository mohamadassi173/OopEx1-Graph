package ex1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Graph_Ex1_Test {
	weighted_graph graph;
	weighted_graph_algorithms algo;
	List<node_info> check_path,shortestPath;
	private static Random _rnd = null;
	boolean t,f;
	double path_dest;

	
	
	/**
	 * Empty graph
	 */
	@Test
	void test0() {
        algo.init(graph);
        assertTrue(algo.isConnected());
    }
	
    /**
     * Graph with single node
     */
	@Test
	void test1() {
		add_vertices(1);
        algo.init(graph);
        assertTrue(algo.isConnected());
    }
	
    /**
     * graph with two vertices and no edges
     */
	@Test
	void test2() {
		add_vertices(2); // adds two nodes with 0,1 keys
        algo.init(graph);
        t = algo.isConnected();
        assertFalse(t);
    }
    /**
     * graph with two nodes and a single edge - connected
     */
	@Test
	void test3() {
		add_vertices(2); // adds two nodes with 0,1 keys
		graph.connect(0, 1, 8);
        algo.init(graph);
        t = algo.isConnected();
        assertTrue(t);
        
    }
	
    /**
     * graph with 7 vertices 
     * test connectivity when remove edge or node from the graph 
     * with copy and save/load functions 
     */
	@Test
	void test4() {

		build_graph();
		// testing copy 
		graph = algo.copy(); // deep copy the graph 
		algo.init(graph);  // init the graph after deep copy
       algo.save("savedGraph"); 

		t = algo.isConnected();
		graph.removeEdge(1, 2);
		t &= algo.isConnected();

		// testing if it has been saved the graph before editing and check if still connected
        algo.load("savedGraph");
        t&=algo.isConnected(); 
        
        
        assertTrue(t);

    }
	
	/**
	 * node with key 6 is connected to the any node in the graph
	 * so it must be not connected
	 */
	void test4_2(){
		build_graph();
		graph.removeEdge(3, 6); // now node with key 6 is not connected to any node..
		f = algo.isConnected(); 
		graph.removeNode(0); // still false
		f |= algo.isConnected();
        assertFalse(f);
	}
	
	
	/**
	 * sample test for shortest path and shortest path dest functions.
	 * the graph is empty 
	 */
	@Test
	void test5() {
		check_path = new LinkedList<node_info>();
        algo.init(graph);  
		// find path with not exist vertices in the graph
        assertTrue(algo.shortestPathDist(0, 1) == -1);
        assertTrue(algo.shortestPath(1, 0) == null);
	}
	
	void test5_2() {
		check_path = new LinkedList<node_info>();
        algo.init(graph);  
        
		// path with one node
		add_vertices(1);
		check_path.add(graph.getNode(0));
		assertTrue(algo.shortestPath(0, 0).equals(check_path));
        assertTrue(algo.shortestPathDist(0, 0) == 0);
	}
	
	
    /**
     * graph with 7 vertices 
     * testing shortest path and shortest path dest functions 
     */
	@Test
	void test6() {
		build_graph();
		check_path.add(graph.getNode(0));
		check_path.add(graph.getNode(5));
		check_path.add(graph.getNode(4));
		check_path.add(graph.getNode(3));
		check_path.add(graph.getNode(6));


        // check the path from 0 to 6   (6 <-> 3 <-> 4 <-> 5 <-> 0) 
        shortestPath = algo.shortestPath(0, 6);
        path_dest = 26.0; // 5 + 7 + 20
        t = shortestPath.equals(check_path);
        t &= algo.shortestPathDist(0, 6) == path_dest;
        

        assertTrue(t);
    }
	/**
	 * test shortest path after update the weight to edge 
	 * with connect 2 vertices with different weight
	 */
	@Test
	void test7() {
		build_graph();
        graph.connect(0, 4, 2); // update the weight to 2
        check_path.add(graph.getNode(2));
        check_path.add(graph.getNode(3));
        check_path.add(graph.getNode(4));
        check_path.add(graph.getNode(0));
        
        // check the path from 2 to 0   (0 <-> 4 <-> 3 <-> 2)
        shortestPath = algo.shortestPath(2, 0);
        path_dest = 17.0; // 2 + 5
        t &= shortestPath.equals(check_path);
        t &= algo.shortestPathDist(2, 0) == path_dest;
        
        assertTrue(t);
	}
	
	/**
	 * build graph with 1m nodes and 30m edges 
	 */
	@Test
	 void test8() {
	        long start = new Date().getTime();
	        int no = 1000*1000, ed = 10000*1000;
	        graph = graph_creator(no, ed, 3);
	        long end = new Date().getTime();
	        double dt = (end-start)/1000.0;
	        boolean t = dt<10;
	        assertEquals(t, true);
	 }
	
	
	
/////////////////////////////////// private functions ////////////////////////////////////////
	
	
	/**
	 * reset the graph after each test
	 */
	@BeforeEach
	void init_graph() {
		 graph = new WGraph_DS();
		algo = new  WGraph_Algo();		
		t=f=true;
	}
	
	
	/**
	 * adds the vertices with keys 0 to n to the graph
	 * @param n - number of keys
	 */
	void add_vertices(int n) {
		for (int i = 0; i < n; i++) {
			graph.addNode(i);
		}
	}
	
	/**
	 * add 7 vertices to the graph 
	 * then connect the graph with complex edges and weight 
	 * 
	 */
	void build_graph() {
		shortestPath = new LinkedList<node_info>();
		check_path = new LinkedList<node_info>();
		path_dest =0;
		
		add_vertices(7);
		graph.connect(0, 1, 50);
		graph.connect(0, 4, 20);
		graph.connect(1, 2, 0);
		graph.connect(2, 3, 8);
		graph.connect(3, 4, 7);
		graph.connect(4, 5, 9);
		graph.connect(3, 6, 5);
		graph.connect(5, 0, 5);
        algo.init(graph);  
	}  

	
	
	
	
	
    private static WGraph_DS graph_creator(int v_size, int e_size, int seed) {
    	WGraph_DS g = new WGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            node_info n = new nodeInfo();
            g.addNode(n.getKey());
        }
        	int[] nodes = nodes(g);
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            double c = nextRnd(1,999);
            int i = nodes[a];
            int j = nodes[b];
            g.connect(i,j,c);
        return g;
    }
    
    
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    
    private static int[] nodes(WGraph_DS g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }

}
