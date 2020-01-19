////////////////////////////////////////////////////////////////////////////////
// Main File:        FindPath.java
// This File:        State.java
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

import java.util.ArrayList;

/**
 * A state in the search represented by the (x,y) coordinates of the square and
 * the parent. In other words a (square,parent) pair where square is a Square,
 * parent is a State.
 * 
 * You should fill the getSuccessors(...) method of this class.
 * 
 */
public class State {

	private Square square;
	private State parent;

	// Maintain the gValue (the distance from start)
	// You may not need it for the BFS but you will
	// definitely need it for AStar
	private int gValue;

	// States are nodes in the search tree, therefore each has a depth.
	private int depth;

	/**
	 * @param square
	 *            current square
	 * @param parent
	 *            parent state
	 * @param gValue
	 *            total distance from start
	 */
	public State(Square square, State parent, int gValue, int depth) {
		this.square = square;
		this.parent = parent;
		this.gValue = gValue;
		this.depth = depth;
	}

	/**
	 * @param visited
	 *            explored[i][j] is true if (i,j) is already explored
	 * @param maze
	 *            initial maze to get find the neighbors
	 * @return all the successors of the current state
	 */
	public ArrayList<State> getSuccessors(boolean[][] explored, Maze maze) {

		// FILL THIS METHOD
		// TODO check all four neighbors in left, down, right, up order
		// TODO remember that each successor's depth and gValue are
		// +1 of this object.
		
		//get current location
		//note x is the up-down, y is the left-right
		final int x = getX();
		final int y = getY();
		//create the list 
		ArrayList<State> successors = new ArrayList<State>();
		//mark current square has been explored
		//explored[x][y] = true;
		
		//check for left inside the boundary
			char context = maze.getSquareValue(x,y-1);
			
			if (context!='%' && (explored[x][y-1]==false)) {
				//create vaild square and state
				Square leftsquare = new Square((x),y-1) ;
				State leftstate = new State(leftsquare,this,this.getGValue()+1,this.getDepth()+1);
				//add into the list
				successors.add(leftstate);
				//System.out.println("<");
			}
		
		//check for down
			context = maze.getSquareValue(x+1,y);
			if (context!='%' && (explored[x+1][y]==false)) {
				//create vaild square and state
				Square downsquare = new Square(x+1,y);
				State downstate = new State(downsquare,this,this.getGValue()+1,this.getDepth()+1);
				successors.add(downstate);
				//System.out.println("v");
			}
		
		//check for right
			context = maze.getSquareValue(x,y+1);
			if (context!='%' && (explored[x][y+1]==false)) {
				//create vaild square and state
				Square rightsquare = new Square(x,y+1) ;
				State rightstate = new State(rightsquare,this,this.getGValue()+1,this.getDepth()+1);
				successors.add(rightstate);
				//System.out.println(">");
			}
			
		//check for up
			context = maze.getSquareValue(x-1,y);
			if (context!='%' && (explored[x-1][y]==false)) {
				//create vaild square and state
				Square upsquare = new Square(x-1,y) ;
				State upstate = new State(upsquare,this,this.getGValue()+1,this.getDepth()+1);
				successors.add(upstate);
			}
		
		return successors;
	}

	/**
	 * @return x coordinate of the current state
	 */
	public int getX() {
		return square.X;
	}

	/**
	 * @return y coordinate of the current state
	 */
	public int getY() {
		return square.Y;
	}

	/**
	 * @param maze initial maze
	 * @return true is the current state is a goal state
	 */
	public boolean isGoal(Maze maze) {
		if (square.X == maze.getGoalSquare().X
				&& square.Y == maze.getGoalSquare().Y)
			return true;

		return false;
	}

	/**
	 * @return the current state's square representation
	 */
	public Square getSquare() {
		return square;
	}

	/**
	 * @return parent of the current state
	 */
	public State getParent() {
		return parent;
	}

	/**
	 * You may not need g() value in the BFS but you will need it in A-star
	 * search.
	 * 
	 * @return g() value of the current state
	 */
	public int getGValue() {
		return gValue;
	}

	/**
	 * @return depth of the state (node)
	 */
	public int getDepth() {
		return depth;
	}
}
