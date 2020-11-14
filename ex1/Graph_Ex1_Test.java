package ex1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

class Graph_Ex1_Test {

	
	
	/**
	 * Testing graph class..............
	 */
	
	@Test
	void test0() {
		weighted_graph empty_graph = new WGraph_DS();
		weighted_graph_algorithms g = new  WGraph_Algo();
        g.init(empty_graph);
        boolean b = g.isConnected();
        assertTrue(b);
    }
	
    /**
     * Simple single node graph
     */
	@Test
	void test1() {
		weighted_graph graph = new WGraph_DS();
		graph.addNode(2);
		graph.addNode(2); // the graph wont add this node because the key already exist..
		weighted_graph_algorithms g = new  WGraph_Algo();
        g.init(graph);
        boolean b = g.isConnected();
        assertTrue(b);
    }
	
    /**
     * graph with two nodes and no edges - not connected
     */
	@Test
	void test2() {
		weighted_graph graph = new WGraph_DS();
		graph.addNode(2);
		graph.addNode(1);
		weighted_graph_algorithms g = new  WGraph_Algo();
        g.init(graph);
        boolean b = g.isConnected();
        assertFalse(b);
    }
    /**
     * graph with two nodes and a single edge - connected
     */
	@Test
	void test3() {
		weighted_graph graph = new WGraph_DS();
		graph.addNode(2);
		graph.addNode(1);
		graph.connect(2, 1, 41);
		weighted_graph_algorithms g = new  WGraph_Algo();
        g.init(graph);
        boolean b = g.isConnected();
        assertTrue(b);
    }
	
    /**
     * graph with 5 vertices 
     * test connectivity when remove edge or node from the graph 
     */
	@Test
	void test4() {
		boolean t = true;
		boolean f = true;
		weighted_graph graph = new WGraph_DS();
		weighted_graph_algorithms g = new  WGraph_Algo();

		for (int i = 0; i < 5; i++) { // add 10 vertices to the graph
			graph.addNode(i);
		}
		graph.connect(0, 1, 41);
		graph.connect(0, 4, 32);
		graph.connect(1, 2, 21);
		graph.connect(2, 3, 8);
		graph.connect(3, 4, 5);
		
		// testing copy 
        g.init(graph);  
		graph = g.copy();
		g.init(graph);  
		
		t = g.isConnected();
		graph.removeEdge(1, 2);
		t &= g.isConnected();
		
		graph.removeEdge(0, 4);
		f &= g.isConnected();
		graph.removeNode(0);
		f &= g.isConnected();
        assertTrue(t);
        assertFalse(f);
    }
	
	
	/**
	 * sample test for shortest path and hsortest path dest functions 
	 */
	@Test
	void test5() {
		boolean t = true;
		weighted_graph graph = new WGraph_DS();
		weighted_graph_algorithms g = new  WGraph_Algo();
		List<node_info> check_path = new LinkedList<node_info>();
		double path_dest =-1;
        g.init(graph);  
		// find path between vertices are not exist in the graph
		t &= g.shortestPathDist(0, 1) == path_dest;
		t &= g.shortestPath(1, 0) == null;
		
		// path with one node
		graph.addNode(0);
		path_dest = 0;
		check_path.add(graph.getNode(0));
		t &= g.shortestPathDist(0, 0) == path_dest;
		t &= g.shortestPath(0, 0).equals(check_path);  
        
        assertTrue(t);
	}
	
	
    /**
     * graph with 5 vertices 
     * testing shortest path and shortest path dest functions 
     *  with extra b3rfesh sho akteb..
     */
	@Test
	void test6() {
		boolean t = true;
		weighted_graph graph = new WGraph_DS();
		weighted_graph_algorithms g = new  WGraph_Algo();
		List<node_info> shortestPath = new LinkedList<node_info>();
		List<node_info> check_path = new LinkedList<node_info>();
		double path_dest =0;
		
		for (int i = 0; i < 5; i++) { // add 5 vertices to the graph
			graph.addNode(i);
			if(i==0 || i==1 || i==2 || i==3 || i==4) check_path.add(graph.getNode(i));
		}
		graph.connect(0, 1, 11);
		graph.connect(0, 4, 41);
		graph.connect(1, 2, 3);
		graph.connect(2, 3, 8);
		graph.connect(3, 4, 5);
        g.init(graph);  
        
        // check the path from 0 to 4   (0 -> 1 -> 2 -> 3 -> 4) 
        shortestPath = g.shortestPath(4, 0);
        path_dest = 27.0; // 11 + 3 + 8 + 5
        t &= shortestPath.equals(check_path);
        t &= g.shortestPathDist(0, 4) == path_dest;
        
        
        check_path = new LinkedList<node_info>();
        graph.connect(0, 4, 2); // update the weight to 2
        check_path.add(graph.getNode(3));
        check_path.add(graph.getNode(4));
        check_path.add(graph.getNode(0));
        
        // check the path from 0 to 3   (0 -> 4 -> 3)
        shortestPath = g.shortestPath(0, 3);
        path_dest = 7.0; // 2 + 5
        t &= shortestPath.equals(check_path);
        t &= g.shortestPathDist(0, 3) == path_dest;
        
        assertTrue(t);
    }

}
