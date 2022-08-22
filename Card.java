*
 * Card.java - creates Card 
 * object with regards to suit and ranks
 */

public class Card implements Comparable<Card>{
	
	private int suit; // use integers 1-4 to encode the suit
	private int rank; // use integers 1-13 to encode the rank
	
	public Card(int s, int r){
		//make a card with suit s and value v
        suit = s;
        rank = r;
        
	}
	
	public int compareTo(Card c){
		// use this method to compare cards so they 
		// may be easily sorted
        int value = 0;
        if (this.rank < c.rank) {
            value = -1;
        }

        else if (this.rank == c.rank) {
            if(this.suit < c.suit) {
                value = -1;
            }
            else if (this.suit > c.suit) {
                value = 1;
            }
            else {
                value = 0; 
            }
        }
        else {
            value = 1;
        }
        return value;
	}
	
	public String toString(){
		// use this method to easily print a Card object
        String [] ranks = {"empty", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String [] suits = {"empty", "Club", "Diamond", "Heart", "Spade"};
        
        String description = suits[suit] + " " + ranks[rank];
        return description;
	}
	// add some more methods here if needed

    public int getSuit() { //public accessor for suit
        return suit;
    }
    
    public int getRank() { //public accessor for rank
        return rank;
    }
}
