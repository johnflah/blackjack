package com.jayoheff.interfaces;

import com.jayoheff.impl.BlackJackPlayer;

/**
 * John O'Flaherty
 * April 2020
 * github.com/johnflah
 *
 */
public interface CardGame {

    public void setupGame(); // allowing for variations in setup between different card games, use jokers/number of decks etc
    public void shuffleCards(); // randomize cards in deck.
    public void startGame(); // initialize game according to variation of game.
    public void addPlayer(BlackJackPlayer player); // generic add player method.
    public BlackJackPlayer getPlayer(int index);  // Get Player by Index
    public void dealCard(BlackJackPlayer player); // Deal a card to a player.
    public Object getGameState();

    public void endGame(); // end game could be used to count up score and declare winner.


}
