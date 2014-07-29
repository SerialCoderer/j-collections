/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcomm.datastructures;

import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author jova
 */
public class Graph {

    private final int graph[][];
    private final int ROWS;
    private final int COLS;
    private Queue<Vertex> queueOfNodes = new java.util.LinkedList<>();
    private Stack<Vertex> visitedNodes = new Stack<>();
    private Vertex[] vertices;

    public Graph(int size) {

        vertices = new Vertex[size];
        this.ROWS = size;
        this.COLS = size;
        this.graph = new int[this.ROWS][this.COLS];
        int x = 0;
        while (x < size) {
            vertices[x] = new Vertex(((char) (x + 65)));
            x++;
        }
    }
    
    public void createVertices(int n){
    	
    	int x =0;
    	while (0 < n) {
    		Vertex v = new Vertex(((char) (x + 65)));
            vertices[x] = v;
            System.out.println("Created vertex : "+v);
            x++;
        }
    }

    public static void main(String args[]) {
        Graph graph = new Graph(9);
        graph.addEdge('A', 'B');
        graph.addEdge('B', 'F');
        graph.addEdge('F', 'H');
        graph.addEdge('A', 'C');
        graph.addEdge('A', 'D');
        graph.addEdge('D', 'G');
        graph.addEdge('G', 'I');
        graph.addEdge('A', 'E');
        graph.printGraph();

        //graph.bfs('A');
        //graph.dfs('A');
        
        graph.recursiveBFS('A');
    }

    public void addEdge(char start, char end) {
        addEdge(start - 65, end - 65);
    }

    private void addEdge(int start, int end) {
        graph[start][end] = 1;
        graph[end][start] = 1;
    }

    public void printGraph() {
        System.out.print(" ");
        for (int x = 0; x < COLS; x++) {
            System.out.print((char) (x + 65));
        }
        System.out.println();

        for (int row = 0; row < ROWS; row++) {
            System.out.print((char) (row + 65));
            for (int col = 0; col < COLS; col++) {
                System.out.print(graph[row][col]);
            }
            System.out.println();
        }
    }
    
    
    /**
     * 3 rules 
     * 
     * DFS makes use of a stack 
     * 
     * Rule 1 : Visit vertex in a systematic way,
     * 			mark it visited and put it the stack
     * 
     * Rule 2 : If you cannot follow Rule 1 anymore then pop vertex off stack
     * 
     * Rule 3 : If you cannot do Rule 1 or Rule 2 anymore you are done
     * 
     * @param label of vertex
     */
    public void dfs(char label){
    	
    	
    	Vertex v = getVertex(label);
    	v.visited = true;
    	visitedNodes.push(v);
    	System.out.println("Visited vertex "+v);
    	int row = getVertexRow(label);
    	for(int i = 0; i<this.COLS; i++){
    		if(graph[row][i] == 1 ){
    			Vertex t = getVertex(i);
    			if(t.visited == false)
    				dfs(t.label);
    		}			
    	}
    }
    
    private Vertex getVertex(int col){
    	return vertices[col];
    }
    
    private int getVertexRow(char label){ 	
    	return label - 65;
    }
    
    private Vertex getVertex(char label){
    	int index = label - 65;
    	return vertices[index];
    	
    }
    
    public void recursiveBFS(char label){
    	
    	Vertex v = getVertex(label);
    	if(v.visited == false){
    		v.visited = true;
    		this.queueOfNodes.add(v);
    		System.out.println("Visited vertex : "+v);
    	}
    	int r = getVertexRow(label);
    	for(int i = 0; i<this.COLS; i++){
    		
    		if(graph[r][i]==1){
    			
    			Vertex t = this.vertices[i];
    			if(t.visited == false){
    				t.visited = true;
    				this.queueOfNodes.add(t);
    				System.out.println("Visited vertex : "+t);
    			}
    		}			
    	}
    	this.queueOfNodes.poll();
    	if(queueOfNodes.isEmpty())
    		return;
    	this.recursiveBFS(this.queueOfNodes.peek().label);
    }

    public void bfs(char label) {

        int index = label - 65;
        
        if (vertices[index].wasVisited() == false) {
            vertices[index].setVisited(true);

            System.out.println(label);
        }


        for (int cols = 0; cols < COLS; cols++) {

            if (graph[index][cols] == 1 && vertices[cols].wasVisited() == false) {
                vertices[cols].setVisited(true);
                queueOfNodes.add(vertices[cols]);
                System.out.println("Enqueue: " + vertices[cols].getLabel());
            }//end of if

        }//end of for loop

        Vertex temp = queueOfNodes.poll();

        if (temp != null) {
            System.out.println("Dequeue: " + temp.getLabel());
            bfs(temp.getLabel());
        }
    }

    public void findConnected(char label) {
        int index = label - 65;
        if (vertices[index].wasVisited() == false) {
            vertices[index].setVisited(true);
            visitedNodes.push(vertices[index]);
            System.out.println("push " + label);
        }
        //traverse columns
        for (int col = 0; col < COLS; col++) {
            if (graph[index][col] == 1 && vertices[col].wasVisited() == false) {
                findConnected((char) (col + 65));
            }
        }

        if (visitedNodes.isEmpty() == false) {
            System.out.println("pop " + visitedNodes.pop().getLabel());
        }
        if (visitedNodes.isEmpty() == false) {
            findConnected(visitedNodes.peek().getLabel());
        }
    }

    private class Vertex {

        private char label;
        private boolean visited;

        public Vertex(char label) {

            this.label = label;

        }

        public char getLabel() {

            return this.label;

        }

        public void setVisited(boolean val) {

            this.visited = val;
        }

        public boolean wasVisited() {
            return this.visited;

        }
        
        @Override
        public String toString(){
        	return this.label +"";
        }
    }
}
