////////////////////////////////////////////////////////////////////////////////
// Main File:        FindPath.java
// This File:        AStarSearcher.java
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
//	This file is for setting up the astar method in searching. A priority queue is used
//as frontier list. The successors of expanded node is added into the list.
//Once the node is expanded, mark as expanded and scuccessor will not be selected
//The every time the node is expanded, it will be tested if it is goal first,
//and then see if the child of it can be added into frontier list. f value will be
//used to determine the members that is poll from list. f = g + h. g value will
//be used to determine whether the successors needs to replace the node that
//already in the frontier list. Iterator is used to go over the frontier list, and 
//for loop is used to go over the successors list. getparent() is used to find the 
//path from goal to the start.
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {

		// FILL THIS METHOD

		// explored list is a Boolean array that indicates if a state associated 
		//with a given position in the maze has already been explored. 
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		// ...

		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();

		// TODO initialize the root state and add
		// to frontier list
		// ...
		State S = new State(maze.getPlayerSquare(),null,0,0);
		int Sx = S.getX();
		int Sy = S.getY();
		int Gx = maze.getGoalSquare().X;
		int Gy = maze.getGoalSquare().Y;
		double fv = Math.sqrt(Math.pow((Sx-Gx), 2)+Math.pow((Sy-Gy), 2));
		StateFValuePair begin = new StateFValuePair(S,fv);
		frontier.add(begin);
		
			// TODO return true if a solution has been found
			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// TODO update the maze if a solution found
					
			while (!frontier.isEmpty()) {
				
				/* this is for debugging
				 * 
				Iterator<StateFValuePair> check=frontier.iterator();
				
				while(check.hasNext()) {
					State checking = check.next().getState();
					System.out.print("["+checking.getX()+","+checking.getY()+"]");
				}
				System.out.println("");
				System.out.print(this.noOfNodesExpanded);
				System.out.println(""); */
			

				StateFValuePair current = frontier.poll();
				State currentstate = current.getState();

				this.cost = currentstate.getGValue();
				this.maxDepthSearched = currentstate.getDepth();
				this.noOfNodesExpanded++;
				
				if(currentstate.isGoal(maze)) {
					
					while(currentstate.getParent()!=null) {
						
						currentstate = currentstate.getParent();
						maze.setOneSquare(currentstate.getSquare(), '.');
						
					}
					
					maze.setOneSquare(currentstate.getSquare(), 'S');
					return true;
				}	
				
				
				else {
					explored[currentstate.getX()][currentstate.getY()]=true;
					//expand current node
					for(int i=0; i<currentstate.getSuccessors(explored, maze).size();i++) {
						
						// for every succssor, get the x,y value of it
						int succX = currentstate.getSuccessors(explored, maze).get(i).getX();
						int succY = currentstate.getSuccessors(explored, maze).get(i).getY();
						
						//System.out.println(succX+" "+succY+","+i);
						boolean exist = false;
						
						double fvthis = currentstate.getSuccessors(explored, maze).get(i).getGValue()+Math.sqrt(Math.pow((succX-Gx), 2)+Math.pow((succY-Gy), 2));
						StateFValuePair newnode = new StateFValuePair(currentstate.getSuccessors(explored, maze).get(i),fvthis);
						
						//compare the index to see if the square exists
						Iterator<StateFValuePair> it=frontier.iterator();
						//test for iterator for 
						while (it.hasNext()) {
							//System.out.print("ww");
							
							//temp pair for tracing
							StateFValuePair temp = it.next();
							
							//this line is for debugging
							//System.out.println(temp.getState().getX()+","+temp.getState().getY());
							
							if(temp.getState().getX()==succX && temp.getState().getY()==succY) {
								if(temp.getState().getGValue() > current.getState().getGValue()) {
									//remove the old node from frontier
									it.remove();
									
									//generate a node for this successor in pairs
									frontier.add(newnode);			
								}			
								exist = true;
								break;
							}							
						}
						//if not exist then add to the frontier
						if(!exist) {
							frontier.add(newnode);
						}
					}	
					this.maxSizeOfFrontier=Math.max(this.maxSizeOfFrontier, frontier.size());
				}				
			}
			// use frontier.poll() to extract the minimum stateFValuePair.
			// use frontier.add(...) to add stateFValue pairs
		
		return false;

		// TODO return false if no solution
	}

}
