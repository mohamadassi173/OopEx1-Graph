package ex1;

import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Graph_Ex1_Test {
	weighted_graph graph;
	weighted_graph_algorithms algo;
	List<node_info> check_path,shortestPath;
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

		connect_graph();
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
	void test4_1(){
		connect_graph();
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
		t &= algo.shortestPathDist(0, 1) == -1;
		t &= algo.shortestPath(1, 0) == null;
		
		// path with one node
		add_vertices(1);
		check_path.add(graph.getNode(0));
		t &= algo.shortestPathDist(0, 0) == 0;
		t &= algo.shortestPath(0, 0).equals(check_path);  
        
        assertTrue(t);
	}
	
	
    /**
     * graph with 7 vertices 
     * testing shortest path and shortest path dest functions 
     *  with extra b3rfesh sho akteb..
     */
	@Test
	void test6() {
		connect_graph();
		check_path.add(graph.getNode(6));
		check_path.add(graph.getNode(3));
		check_path.add(graph.getNode(4));
		check_path.add(graph.getNode(5));
		check_path.add(graph.getNode(0));


        // check the path from 0 to 6   (6 <-> 3 <-> 4 <-> 5 <-> 0) 
        shortestPath = algo.shortestPath(0, 6);
        path_dest = 26.0; // 5 + 7 + 20
        t = shortestPath.equals(check_path);
        t &= algo.shortestPathDist(0, 6) == path_dest;
        

        assertTrue(t);
    }
	
	@Test
	void test7() {
		connect_graph();
        graph.connect(0, 4, 2); // update the weight to 2
        check_path.add(graph.getNode(0));
        check_path.add(graph.getNode(4));
        check_path.add(graph.getNode(3));
        check_path.add(graph.getNode(2));
        
        // check the path from 2 to 0   (0 <-> 4 <-> 3 <-> 2)
        shortestPath = algo.shortestPath(2, 0);
        path_dest = 17.0; // 2 + 5
        t &= shortestPath.equals(check_path);
        t &= algo.shortestPathDist(2, 0) == path_dest;
        
        assertTrue(t);
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
	void connect_graph() {
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

}
