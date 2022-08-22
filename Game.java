/*
 * Game.java - takes in a user input via commandLine 
 * or no arguments at all
 * user interacts with command prompt and
 * plays rounds of poker game with option 
 * to bet and win according to hand
 *  
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	
    //private instance variables used in game
	private Player ply;
	private Deck cards;
    private Scanner input;
    private double payment;
	
	
	public Game(String[] testHand){
		
        //initializes private instances 
        ply = new Player();
        input = new Scanner(System.in);
        cards = new Deck();
        payment = 0.0;
        
        int s = 0;
        int r = 0;
        
        for(int i = 0; i<5; i++){ //matches suit to numeric value from String
            if(testHand[i].substring(0,1).equals("c")) s =1;
            if(testHand[i].substring(0,1).equals("d")) s =2;
            if(testHand[i].substring(0,1).equals("h")) s =3;
            if(testHand[i].substring(0,1).equals("s")) s =4;
            
            //pulls put String + converts rank from String to Integer
            if(testHand[i].length() ==2) r = Integer.parseInt(testHand[i].substring(1,2));
            else if (testHand[i].length() == 3) r = Integer.parseInt(testHand[i].substring(1,3));
            
            //adds new card
            ply.addCard(new Card(s, r)); 
        }
	}
	
	public Game(){
		// no-argument constructor created to play a normal game
		ply = new Player();
        cards = new Deck();
        input = new Scanner(System.in);
        payment = 0.0;
	}
	
	public void play(){
		// play method - outlines + runs the game
        
        int counter = 1; // variable determines if game runs
        while (counter == 1) {
            System.out.println("Hello, Welcome to video poker. " + "You currently have: " + ply.getBankroll() + " tokens. \n Please choose a bet between 1-5.");
            int playerBet = input.nextInt();
            while(playerBet>5 || playerBet < 1) {
                System.out.println("Enter a bet between 1-5:");
                playerBet = input.nextInt();
            }
            ply.bets(playerBet); //calls bet method
            
            System.out.println("Remaining tokens: " + ply.getBankroll());
            cards.shuffle(); //shuffles cards
            
            if(ply.getHand().size() > 0) { //if argument is passed in command-line
                ply.sortHand();
                System.out.println(ply);
            }
            else {
                initialDeal();
            }
            
            finalizeHand(); //private method that switches cards
            System.out.println( "Your hand is a: " + checkHand(ply.getHand()));
            ply.winnings(getPayment());
            System.out.println("You now have: " + ply.getBankroll());
            System.out.println("Play again? If yes, enter 1; if no, enter 0.");
            counter = input.nextInt();
            ply.getHand().clear(); //reset to clear hand
        }
	}
	
	public String checkHand(ArrayList<Card> hand){
		/* this method takes in an ArrayList of cards
		 as input and then determines type of hand in deck
		and returns to user through a String */
        
		String result = " ";
        if(royalFlush()) result = "Royal Flush";
        else if (straightFlush()) result = "Straight Flush";
        else if (fourOfAKind()) result = "Four of a Kind";
        else if (fullHouse()) result = "Full House";
        else if (flush()) result = "Flush";
        else if (straight()) result = "Straight";
        else if (threeOfAKind()) result = "Three of a Kind";
        else if (twoPair()) result = "Two Pair";
        else if (onePair()) result = "One Pair";
        else if (highCard()) result = "High Card"; // else if or else
        
        return result;
	}
    
    public double getPayment(){ //public accessor
        return payment;
    }
    
    private void initialDeal(){ //deals an initial deck of cards
        for(int x = 0; x<5; x++) {
           ply.addCard(cards.deal());
        }
        ply.sortHand();
        System.out.println(ply);
        
    }
    
    private void finalizeHand(){ //method to ask player if they want to keep a card
        int counter = 0;
        for (int x= 0; x<5; x++) {
            Card latest = ply.getHand().get(counter);
            System.out.println("Do you want to keep the card " + latest + "?");
            System.out.println("If yes, enter 1; if no, enter 0:");
            int answer = input.nextInt();
            if(answer == 1) {
                ply.removeCard(latest);
                ply.addCard(cards.deal());
            }
            if(answer == 0) {
                counter++;
            }
        }
        System.out.println("Switching complete.");
        ply.sortHand();
        System.out.println(ply);       
    }
    
    //following methods are created to identify various types of hands
    
    private boolean royalFlush() { // 10 J Q K A same suit
        if(!flush() && !straight()) return false;
        else if (flush() && ply.getHand().get(1).getRank() == 10) {
            payment = 250.0;
            return true;
        }
        
        return false;
    }
	
    private boolean straightFlush() { //straight and flush
        if(straight() && flush()){
            payment = 50.0;
            return true;
        }
        else{
            return false;
        }
    }
    
    private boolean fourOfAKind() { // either 1-4 equal or 2-5 equal
        if(ply.getHand().get(0).getRank() == ply.getHand().get(1).getRank() && 
          ply.getHand().get(0).getRank() == ply.getHand().get(2).getRank() && 
          ply.getHand().get(0).getRank() == ply.getHand().get(3).getRank()) {
            payment = 25.0;
            return true;
        }     
        else if (ply.getHand().get(1).getRank() == ply.getHand().get(2).getRank() &&
                ply.getHand().get(1).getRank() == ply.getHand().get(3).getRank() &&
                ply.getHand().get(1).getRank() == ply.getHand().get(4).getRank()) {
            payment = 25.0;
            return true;
        }
        else {
            return false;
        }
    }
    
    private boolean fullHouse() { //either 3 and 2 equal rank or 2 and 3 equal rank
        if (ply.getHand().get(0).getRank() == ply.getHand().get(1).getRank() &&
           ply.getHand().get(0).getRank() == ply.getHand().get(2).getRank() &&
           ply.getHand().get(3).getRank() == ply.getHand().get(4).getRank()) {
            payment = 6.0;
            return true;
        }
        else if (ply.getHand().get(0).getRank() == ply.getHand().get(1).getRank() &&
                ply.getHand().get(2).getRank() == ply.getHand().get(3).getRank() &&
                ply.getHand().get(2).getRank() == ply.getHand().get(4).getRank()) {
            payment = 6.0;
            return true;
        }
        else {
            return false;
        }
 
    }
    
    private boolean flush() { //all same suit
        for(int i =0; i<4; i++){
            if(ply.getHand().get(i).getSuit() != ply.getHand().get(i+1).getSuit()) return false;
        }
        payment = 5.0;
        return true;        
    }
    
    private boolean straight() { //rank in increasing order by increment of 1 
        for(int i =0; i<4; i++) {
            if(ply.getHand().get(i).getRank() == ply.getHand().get(i+1).getRank()){
                return false; 
            } //checks for duplicates
                     
            //checks if incrementing by 1
            if(ply.getHand().get(i).getRank() < ply.getHand().get(i+1).getRank()) {
                if(ply.getHand().get(4).getRank() - ply.getHand().get(0).getRank() == 4 && (onePair() == false)){ //increasing + last card - first = 4
                    payment = 4.0;
                    return true;
                }
            } 
        }
    
        //hardcoded if statement for test case
        if(ply.getHand().get(0).getRank() == 1 &&
           ply.getHand().get(1).getRank() == 10 &&
           ply.getHand().get(2).getRank() == 11 &&
           ply.getHand().get(3).getRank() == 12 &&
           ply.getHand().get(4).getRank() == 13) {
            payment = 4.0;
            return true;
        }
        return false;
    }
    
    private boolean threeOfAKind() { //3 equal rank
        if (ply.getHand().get(0).getRank() == ply.getHand().get(2).getRank() ||
            ply.getHand().get(1).getRank() == ply.getHand().get(3).getRank() ||
            ply.getHand().get(2).getRank() == ply.getHand().get(4).getRank()) {
            payment = 3.0;
            return true;
        }
        else 
            return false;
    }
    
    private boolean twoPair(){ //with assuming fourOfAKind is false already
        int pairs = 0;
        for (int i=0; i<4; i++){
            if(ply.getHand().get(i).getRank() == ply.getHand().get(i+1).getRank())
                pairs++;
        }
            if (pairs == 2){
                payment = 2.0;
                return true;
            }
            else
                return false;
    }
    
    private boolean onePair(){ //duplicates
        for(int i = 0; i<4; i++) {
            if(ply.getHand().get(i).getRank() == ply.getHand().get(i+1).getRank()) {
                payment = 1.0;
                return true;
            }
        }
        return false;
    }
    
    private boolean highCard(){ //if fails all tests, returns true
        payment = 0.0; //resets payout
        return true;
    }
}
