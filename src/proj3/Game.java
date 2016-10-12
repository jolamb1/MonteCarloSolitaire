package proj3;
import java.util.Random;
/**
 * Game.java
 * @author Joseph Lamb
 * jolamb1@umbc.edu
 * CMSC 202 
 * Lecture Section 04
 * Lab Section 02
 * 11/1/2011
 * 
 * This class represents a game of Monte Carlo Solitaire
 * Class invariant: All Game objects must have a non null, nonzero number of rows and columns
 * a nonnull score, and nonnull deck
 */
public class Game {
	
	final private int CARDS_IN_DECK = 52;
	final private double ACCEPTABLE_DISTANCE1 = 1.0;
	final private double ACCEPTABLE_DISTANCE2 = Math.sqrt(2);
	
	
	private int tableauRows;
	private int tableauColumns;
	private int score;
	private long gameNum;
	private Card[] deck;
	private Card[]tableau;
	
	/*
	 * Constructor
	 * creates a game object with a score of 0 and a tableau with a specified number of rows and columns 
	 */
	public Game(int tableauRows, int tableauColumns) {
		if (tableauRows <= 0) {
			tableauRows = 5;
		} else if(tableauColumns <= 0) {
			tableauColumns = 5;
		}
		
		this.tableauColumns = tableauColumns;
		this.tableauRows = tableauRows;
		this.score = 0;
		this.tableau = new Card[tableauRows * tableauColumns];
		
		
		deck = new Card[CARDS_IN_DECK];
		
		//generating the deck
		for(int i = 0; i < Suit.values().length; i++) {
			for( int j = 0; j < Rank.values().length; j++) {
				deck[((13*i)+j)] = new Card(Rank.values()[j], Suit.values()[i]);
			}
		}
		
			
		}
	
	/*
	 * Starts a new game of solitaire for the specified game number in accordance to project specifications.
	 * parameter: long gameNumber: used as a seed for the random number generator
	 * precondition: gameNumber is null
	 * Postcondition: deck is created, shuffled , and dealt into a 5x5 tableau
	 */
	public void newGame(long gameNumber) {
		gameNum = gameNumber;
		score = 0;
		//generating the deck
		for(int i = 0; i < Suit.values().length; i++) {
			for( int j = 0; j < Rank.values().length; j++) {
				deck[((13*i)+j)] = new Card(Rank.values()[j], Suit.values()[i]);
			}
		}
		//shuffling the deck		
		Random r = new Random(gameNumber);
		int k;
		Card temp;
	
		for (int n = deck.length - 1; n >= 1; n--){
			k = r.nextInt(n+1);
			temp = deck[k];
			deck[k] = deck[n];
			deck[n] = temp;
			
		}
		//loading up the tableau
		for (int m = 0; m < 25; m++) {
			
			tableau[m] = deck[m];
			deck[m] = null;
		}
		
		
	}
	/*
	 * Returns the number of cards left in the deck
	 * precondition: none
	 * postcondition: the number of cards in the deck have ben returned
	 */
	public int numberOfCardsLeft() {
		int counter = 0;
		for (int i = 0; i < 52; i++) {
			if (deck[i] != null) {
				counter++;
			}
			
		}
		return counter;
	}
	/*
	 * returns the player's score for the current game
	 * precondition: none
	 * postcondition: the score has been returned
	 */
	public int getScore() {
		return score;
		
	}
	
	/*
	 * restarts the current game
	 * precondition:none
	 * postcondition: the game has been reset
	 */
	public void replay() {
		newGame(gameNum);
	}
	/*
	 * returns false, because giveHint is not implemented
	 * postcondition: false is returned
	 */
	public boolean isHintImplemented() {
		return false;
	}
	/*
	 * returns null because it is not implemented
	 * postcondition: null is returned
	 */
	public Coordinate[] getHint() {
		
		return null;
	}
	/*
	 * returns a string of information on how to play
	 * postcondition: help text is returned
	 */
	public String getHelpText() {
		
		String text = "Remove pairs of same-ranked cards that are next to eachother \n either horizontally, vertically, or diagonally by clicking on them,\n this will give you two points.\nIf there are no more pairs to remove,\n click on the deck at the top left corner of the window to consolidate\n your cards. You win if you can remove all of the cards and get 52 points\n If no more moves can be taken, then restart the game using the drop-down menu ";
		return text;
		 
		
	}
	/*
	 * Returns a Suit enum representing the suit of the card at the specified coordinate in the tableau 
	 * precondition: coordinates is not null
	 * postcondition: Suit is returned
	 */
	public Suit getSuit(Coordinate coordinates) {
		int index = (coordinates.getRow()*5) + coordinates.getColumn();
		Card temp = tableau[index];
		if (temp == null) {
			return null;
		} else {
			return tableau[index].getSuit();
		}
		
		
	}
	/*
	 * Returns a Rank enum representing the Rank of the card at the specified coordinate in the tableau 
	 * precondition: coordinate is not null
	 * postcondition: Rank is returned
	 */
	public Rank getRank(Coordinate coordinate) {
		int index = (coordinate.getRow()*5) + coordinate.getColumn();
		Card temp = tableau[index];
		if (temp == null) {
			return null;
		} else {
			return tableau[index].getRank();
		}
	}
/*
 * Determines if the cards at the specified tableau locations are a match according to the rules of Monte Carlo Solitaire and if so, removes them from the tableau 
 * precondition: coord1 and coord2 are not null
 * postcondition: cards are removed if the pair works by the rules, true is returned if successful, false if not
 */
	public boolean removeCards(Coordinate coord1, Coordinate coord2) {
		
		int card1Index = (coord1.getRow() * 5) +coord1.getColumn();
		int card2Index = (coord2.getRow() * 5) +coord2.getColumn();
		//using the distance formula to calculate distance between cards 
		double part1 = Math.pow(coord1.getRow()-coord2.getRow(), 2);
		double part2 = Math.pow(coord1.getColumn()-coord2.getColumn(), 2);
		double distance = Math.sqrt(part1+part2);
		
		if ((distance == ACCEPTABLE_DISTANCE1 || distance == ACCEPTABLE_DISTANCE2) && (tableau[card1Index].getRank() == tableau[card2Index].getRank()) ) {
			tableau[card1Index] = null;
			tableau[card2Index] = null;
			score += 2;
			return true;
		} else {
			return false;
		}
	}
	/*
	 * Moves cards in the tableau towards the top (left and up) to replace the cards that were removed. Then deals cards from the deck to refill the empty spaces at the bottom of the tableau. 
	 * precondition: there are open spaces in the tableau
	 * postcondtion: the tableau has been consolidated
	 */
	public void consolidate() {
		
		for (int i = 0; i < 25; i++) {
			if (tableau[i] ==  null) {
				for (int j = i; j < 25; j++) {
					if (tableau[j] != null) {
						tableau[i] = tableau[j];
						tableau[j] = null;
						j = 25;
					}
				}
			}
		}
		
		for (int k = 0; k < 52; k++) {
			if (deck[k] != null) {
				for (int l = 0; l < 25; l++) {
					if(tableau[l] == null) {
						tableau[l] = deck[k];
						deck[k] = null;
						l = 25;
					}
				}
			}
		}
		
		
		
	}
	
	public boolean isWin() {
		if (score == 52) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		
		Game game = new Game(5,5);
		//testing to make sure the deck is made properly
		for (int i = 0; i < 52; i++) {
			System.out.println(game.deck[i].toString());
		}
		//shuffling, using the test case to make sure the algorithm is correct
		game.newGame(12345);
		System.out.println("\n\n");
		
		//testing the shuffler
		for (int i = 0; i < 52; i++) {
			Card temp = game.deck[i];
			if (temp != null) {
			System.out.println(game.deck[i].toString());
			}
		}
		//will not work, cards are not next to each other
		 boolean didWork =game.removeCards(new Coordinate(0,0), new Coordinate(4,4));
		 System.out.println(didWork);
		 //no cards were removed, so consolidate should not do anything
		 game.consolidate();
		 //to show that no cards were removed
		 for (int i = 0; i < 25; i++) {
				Card temp = game.tableau[i];
				if (temp != null) {
				System.out.println(game.tableau[i].toString());
				}
		 }
				//should work because they are diagonal to each other and they have the same rank
		didWork = game.removeCards(new Coordinate(0,4), new Coordinate(1,3));
		System.out.println(didWork);
		//score should equal 2
		System.out.println("Score: " +game.getScore());
		game.consolidate();
		for (int j = 0; j < 25; j++) {
			 Card temp = game.tableau[j];
			if (temp != null) {
			System.out.println(game.tableau[j].toString());
			}
		}
			boolean isWin = game.isWin();
			System.out.println(isWin);
			game.score = 52;
			isWin = game.isWin();
			System.out.println(isWin);
			
	}
}


		 
	


