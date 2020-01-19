////////////////////////////////////////////////////////////////////////////////
// Main File:        FindPath.java
// This File:        BreadthFirstSearcher.java
// Other Files:      
// Semester:         CS 540 Fall 2019
// Lecture:			 001		
//
// Author:           Wei He
// Email:            whe37@wisc.edu
// CS Login:         whe
//
/////////////////////////// OTHER SOURCES OF HELP //////////////////////////////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          N/A
//
// Online sources:   N/A 
//         
////////////////////////////////////////////////////////////////////////////////
//Discription: 
//	This file is for setting up the BFS method in searching. A linklist is used
//as frontier list. The successors of expanded node is added into the list.
//Once the node is expanded, mark as expanded and scuccessor will not be selected
//The every time the node is expanded, it will be tested if it is goal first,
//and then see if the child of it can be added into frontier list.
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Breadth-First Search (BFS)
 * 
 * You should fill the search() method of this class.
 */
public class BreadthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public BreadthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main breadth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		// FILL THIS METHOD

		// explored list is a 2D Boolean array that indicates if a state 
		// associated with a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		// ...

		// Queue implementing the Frontier list
		LinkedList<State> queue = new LinkedList<State>();
		
		//initiate a start state and add to the frontier list
		State begin = new State(maze.getPlayerSquare(),null,0,0);
		queue.add(begin);
		
		while (!queue.isEmpty()) {
			//this is for debugging
			//for (int z=0;z<queue.size();z++) {
			//	System.out.print("["+queue.get(z).getX()+","+queue.get(z).getY()+"]");
			//}
			//System.out.println("");
			
			//pop the node from frontier to explored list
			State current = queue.pop();
			
			//System.out.print(this.noOfNodesExpanded);
			this.cost = current.getGValue();
			this.maxDepthSearched = current.getDepth();
			
			//as long as the node is taken oout of the queue, it is expended.
			this.noOfNodesExpanded++;
			// TODO return true if find a solution
			// TEST for goal
			if(current.isGoal(maze)) {
				//print the route
				while(current.getParent()!=null) {
					current = current.getParent();
					maze.setOneSquare(current.getSquare(), '.');
				}
				maze.setOneSquare(current.getSquare(), 'S');
				return true;
			}
			
			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// if it is not a goal then expend it
			else { 
				
				explored[current.getX()][current.getY()]=true;
				//if the square alrealy in frontier, donot add it
				for(int i=0; i<current.getSuccessors(explored, maze).size();i++) {

					// for every succssor, get the x,y value of it
					int succX = current.getSuccessors(explored, maze).get(i).getX();
					int succY = current.getSuccessors(explored, maze).get(i).getY();
					boolean exist = false;
					
					//compare the index to see if the square exists
					for (int j=0;j<queue.size();j++) {
						//check if there already a node with same x,y in the queue
						if(queue.get(j).getX()==succX && queue.get(j).getY()==succY) {
							
							//check which one has a smaller cost, and keep the smaller one
							if(queue.get(j).getGValue()>current.getGValue()) {
								queue.remove(j);
								queue.add(current.getSuccessors(explored, maze).get(i));								
							}
							exist = true;
							break;
						}
					}	
					
					//if not exist then add to the frontier
					if(!exist) {
						queue.add(current.getSuccessors(explored, maze).get(i));
					}
				}
				
				this.maxSizeOfFrontier=Math.max(this.maxSizeOfFrontier, queue.size());
				//System.out.println(" "+queue.size());

			}
			// TODO update the maze if a solution found
			// use queue.pop() to pop the queue.
			// use queue.add(...) to add elements to queue
		}
		
		// TODO return false if no solution
		return false;
	}
}
