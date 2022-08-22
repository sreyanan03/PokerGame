/*
 * Deck.java - creates a deck of 52 cards 
 * and user can shuffle or deal cards
 */

public class Deck {
	
	private Card[] cards;
	private int top; // the index of the top of the deck

	// add more instance variables if needed
	
	public Deck(){
		// make a 52 card deck here
        top =0;
        cards = new Card[52];
        
        int i =0;
        for(int suit = 1; suit<=4; suit++){
            for(int rank=1; rank<=13; rank++){
                cards[i] = new Card(suit, rank);
                i++;
            }
        }
	}
	
	public void shuffle(){
		// shuffle the deck here
        int pos1, pos2;
        for(int c =0; c<1000; c++){ //swap 1000 times "shuffle"
            pos1 = (int) (Math.random()*52);
            pos2 = (int)(Math.random()*52);
            
            Card temp = cards[pos1];
            cards[pos1] = cards[pos2];
            cards[pos2] = temp;
        }
	}
	
	public Card deal(){
		// deal the top card in the deck
        top++;
        if(top>52){
            this.shuffle();
            top = 1; // bc the returning top -1
        }
        return cards[top-1];
	}
}
