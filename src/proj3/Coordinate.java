package proj3;
/**
 * Coordinate.java
 * @author Joseph Lamb
 * jolamb1@umbc.edu
 * CMSC 202 
 * Lecture Section 04
 * Lab Section 02
 * 11/1/2011
 * 
 * This class represents a single set of coordinates.
 * Class invariant: All Coordinates have a nonnull row and column number
 */
public class Coordinate {
	
	private int row, column;
	/*
	 * constructor
	 * This method creates a coordinate object with int values representing which row and column it is in
	 * precondition: row and column must be not null integers
	 * postcondition: a Coordinate object is created
	 */
	public Coordinate(int row, int column) {
	this.row = row;
	this.column = column;
	}

	
/*
 * Accessor for column instance variable
 * precondition: none
 * postcondition: column has been returned
 */
	public int getColumn() {
		return this.column;
	}
	/*
	 * Accessor for row instance variable
	 * precondition: none
	 * postcondition: row has been returned
	 */
	public int getRow() {
		
		return this.row;
	}

	
	
	
}
