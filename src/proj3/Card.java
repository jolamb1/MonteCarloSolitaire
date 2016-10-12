package proj3;
/**
 * Card.java
 * @author Joseph Lamb
 * jolamb1@umbc.edu
 * CMSC 202 
 * Lecture Section 04
 * Lab Section 02
 * 10/19/2011
 * 
 * This class represents a single playing card.
 * Class invariant: All Cards have a rank and suit from the respective enums, and both can not be null
 */

public class Card {
	
	private Rank rank;
	private Suit suit;	
	/*
	 * Constructor
	 * This creates a Card object with a Rank from the Rank enum and a Suit from the Suit enum
	 * precondition: rank and suit are not null and are fromt their respective enums
	 * postcondition: a Card object has been created
	 */
	public Card(Rank rank, Suit suit) {
		if (rank == null) {
			rank = Rank.ACE;
		} else if (suit == null) {
			suit = Suit.SPADES;
		}
		this.rank = rank;
		this.suit = suit;
		
	}
/*
 * This method returns the Rank of the called Card object
 * precondition: none
 * postcondition: the Rank has been returned
 */
	public Rank getRank() {
		return rank;
	}
	/*
	 * This method returns the Suit of the called Card object
	 * precondition: none
	 * postcondition: the Suit has been returned
	 */
	public Suit getSuit() {
		return suit;
	}
/*
 * This method returns the Card object as a string containing the Rank and Suit
 * precondition: none
 * postcondition: a String representation of the Card has been returned
 */
	public String toString() {
		
		return this.rank.getName()+" "+this.suit.getName();
	}
	
	public static void main(String[] args) {
		
		
		
		Card theAceOfSpades = new Card(Rank.ACE,Suit.SPADES);
		
		System.out.println(theAceOfSpades.getRank());
		System.out.println(theAceOfSpades.getSuit());
		
		System.out.println(theAceOfSpades);
		
	}

}
