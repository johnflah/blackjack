package com.jayoheff.util;

import com.jayoheff.constants.UserInterfaceStrings;
import com.jayoheff.impl.BlackJack;
import com.jayoheff.impl.BlackJackPlayer;
import com.jayoheff.vm.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackJackUtil {

    BlackJack game ;

    /**
     * No-args constructor, used to initialize the blackjack game object.
     */
    public BlackJackUtil() {
        this.game = new BlackJack();
    }

    /**
     * Constructor is used in the event that the number of decks and joker requirements are variable.
     * @param numDecks Number of decks to use.s
     * @param useJokers A boolean that indicates whether or not to include jokers
     */
    public BlackJackUtil(int numDecks, boolean useJokers) {
        this(); // constructor chaining
        this.game.setUseJokers(useJokers); //Setting whether to use Jokers or not.
        this.game.setNumberOfDecks(numDecks); //setting the number of decks

    }


    /***
     * This is the main engine of the game, it takes input and calls methods to calculate scores.
     * The end result is a console message indicating a winner.
     */
    public void playBlackJack() {

//        String input =""; // Used to hold input from player
        String result = ""; //End Game result

        game.setupGame();//setting the decks
        game.shuffleCards(); // Using Collections.shuffle to shuffle the deck.
        game.startGame(); // Initialize Hands played to zero and deal cards

        displayCards(); // Display first hand of cards


        List<BlackJackPlayer> standing = new ArrayList<>(); // this holds any players that have chosen to stand.
        List<BlackJackPlayer> eligiblePlayers = new ArrayList<>();
        eligiblePlayers.addAll(game.getPlayers());

        List<BlackJackPlayer> bookends = anyBookends(game); // Any players with A-10 or equivalent out of the gate.

        boolean isThereANaturalBlack = (game.getHandsPlayed() == 0) && (bookends.size() > 0);

        //do player card dealing
        servePlayers( eligiblePlayers, standing);

        //serve Dealer cards if applicable.
        BlackJackPlayer dealer = game.getDealer();

        // if there are remaining players in the game
        if( (standing.size() > 0)) {
            //are there any Natural Blackjacks
            if(isThereANaturalBlack) {
                // Dealer has to go with whats in their hand and cannot request more.
                dealer.setStands(true);
            }else{
                dealer = doDealerNext();
            }
            //Calculating the winner.
            result = calculateWinner(standing, dealer, isThereANaturalBlack);

        }else{
            //dealer wins
            result = UserInterfaceStrings.ALL_PLAYERS_BUST;

        }

        //separated out as the output could be destined for console, web service endpoint etc.
        System.out.println(result);

    }

    /**
     * This method calculates the winner based on the list of remaining players, the dealer and if any natural blackjack has occurred.
     * @param standing List of remaining players
     * @param dealer the dealer player object
     * @param isThereANaturalBlack was a natural blackjack detected
     * @return String consisting of the winners, losers and scores
     */
    private String calculateWinner(List<BlackJackPlayer> standing, BlackJackPlayer dealer, boolean isThereANaturalBlack) {

        String result = "";

        // Pocket Ace 10
        if(isThereANaturalBlack){
            for(BlackJackPlayer p : standing) {
                // where the player and dealer both have a natural blackjack
                if(p.isNatural() && dealer.isNatural()){
                    result = UserInterfaceStrings.DRAW;
                }else{
                    result = String.format(UserInterfaceStrings.PLAYER_WIN, p.getScore());
                }
            }
        } else if( dealer.isBust() ){
            //player wins
            result = String.format(UserInterfaceStrings.DEALER_LOSS,dealer.getScore());

        }else if(dealer.isStands()){
            for(BlackJackPlayer p : standing){
                if(p.getScore() > dealer.getScore()){
                    result += String.format(UserInterfaceStrings.PLAYER_WIN,p.getScore());
                    result += "\n"+String.format(UserInterfaceStrings.DEALER_LOSS,dealer.getScore());

                }else if(p.getScore() <= dealer.getScore()){
                    result += String.format(UserInterfaceStrings.DEALER_WIN,dealer.getScore());
                    result += "\n"+String.format(UserInterfaceStrings.PLAYER_LOSS,p.getScore());

                }
            }
        }

        return result;
    }

    /**
     * This method automates the dealer element of the game, the dealer has to stop at 17.
     * @return
     */
    private BlackJackPlayer doDealerNext() {

        BlackJackPlayer dealer = game.getDealer();
        while (dealer.isEligiblePlayer()){
            game.dealCard(dealer);
            dealer.calculateScore();
        }
        return dealer;
    }


    /**
     * Deal cards to the players
     * @param eligiblePlayers
     * @param standing
     */
    private void servePlayers(List<BlackJackPlayer> eligiblePlayers, List<BlackJackPlayer> standing) {
        String input ;

        do {

            for(BlackJackPlayer p : game.getPlayers()){
                //if user is not bust or standing => prompt user to hit or stand
                while( p.isEligiblePlayer()) {

                    System.out.println(UserInterfaceStrings.BLACKJACK_PROMPT);

                    input = getUserInput();
                    input = input.toUpperCase();

                    switch (input) {
                        case "H":
                            game.dealCard(p);
                            p.calculateScore();
                            break;
                        case "S":
                            p.setStands(true);
                            break;
                        default:
                            System.out.println(UserInterfaceStrings.INVALID_MENU_PROMPT);
                            System.out.println(UserInterfaceStrings.BLACKJACK_PROMPT);

                            break;
                    }
                    //Display Cards
                    displayCards();
                }

                if(p.isBust() ){
                    if(eligiblePlayers.contains(p)) {
                        eligiblePlayers.remove(p);
                    }
                }

                if(p.isStands()){
                    if(eligiblePlayers.contains(p)) {
                        eligiblePlayers.remove(p);
                    }
                    standing.add(p);
                }

            }



        }while (eligiblePlayers.size() > 0);

    }


    /**
     * Utility Method to get user input from the console.
     * @return String value
     */
    private String getUserInput() {

        Scanner in = new Scanner(System.in);

        return in.nextLine();

    }

    /**
     * This method checks for any Ace Ten or equivalent cards from the start. Only used to detect if a natural blackjack occurred.
     * @param game
     * @return
     */
    private List<BlackJackPlayer> anyBookends(BlackJack game) {
        List<BlackJackPlayer> bookends = new ArrayList<>();
        for(BlackJackPlayer p : game.getPlayers()){
            if(p.isBlackjack()){
                bookends.add(p);
                p.setStands(true);
                p.setBlackjack(true);
                p.setNatural(true);
            }
        }

        return bookends;
    }

    /**
     * This method displays the cars for the players.
     */
    private void displayCards() {

        for(BlackJackPlayer player: game.getPlayers()) {
            String result = "";
            int score = player.getScore();
            for (Card c : player.getCardHand()) {
                if(player.isDealer()){
                    result += " Dealer has : "+player.getCardHand().get(0).getFaceValue() + " of " +player.getCardHand().get(0).getSuit() +", ?";
                }else {
                    result += c.getFaceValue() + " of " + c.getSuit() + " From Deck # " + c.getDeck() +"\n";
                }
            }
            System.out.println(result += "Score = " +score);
        }



    }

    /**
     * This method displays the Continue Y/N screen.
     * @return
     */
    public String displayHomeScreen() {

        System.out.println(UserInterfaceStrings.MENU_PROMPT);

        return getUserInput();

    }
}
