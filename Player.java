/*
 * Player.java - creates Player who can add 
 * and remove cards from their hands. creates a set 
 * bankroll and number they can bet when playing the game
 */

import java.util.ArrayList;
import java.util.Collections;

public class Player {
	
		
	private ArrayList<Card> hand; // the player's cards
	private double bankroll;
    private double bet;
		
	public Player(){		
	    // create a player here
        hand = new ArrayList<Card>();
        bankroll = 100.0;
        bet = 0.0;
	}

	public void addCard(Card c){
	    // add the card c to the player's hand
        hand.add(c);
	}

	public void removeCard(Card c){
	    // remove the card c from the player's hand
        hand.remove(c);
    }
		
    public void bets(double amt){
            // player makes a bet
            bet = amt;
            bankroll = bankroll - bet;
    }

    public void winnings(double odds){
            //	adjust bankroll if player wins
            bankroll = bankroll + (odds*bet);
    }

    public double getBankroll(){
            // return current balance of bankroll
            return bankroll;
    }

    public void sortHand() {
        //uses Collections to sort Hand
        Collections.sort(hand);
    }
    
    public ArrayList<Card> getHand() { //public accessor for hand
        return hand;
    }
    
    public String toString() { //toString() method - 
        //returns a String description of deck of cards 
        String description;
        int i =0;
        description = "Your hand is";
        for(Card element: hand){
            description += "\n" + hand.get(i).toString();
            i++;
        }
        
        return description;
    }    
}
