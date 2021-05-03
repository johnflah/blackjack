package com.jayoheff.impl;

import com.jayoheff.constants.GameConstants;
import com.jayoheff.interfaces.CardGame;
import com.jayoheff.vm.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlackJack implements CardGame {

    private int handsPlayed;
    private int numberOfDecks;
    private boolean useJokers;

    private BlackJackPlayer dealer;
    private List<BlackJackPlayer> players;
    private List<Card> availableCards;

    private int lastCardIndex ;

    //constructor
    //no-args first
    public BlackJack(){

        availableCards = new ArrayList<>();
        players = new ArrayList<>();

    }

    //Constructor passing in number of decks, whether to use jokers or not.
    public BlackJack(int decks, boolean jokers){
        this();
        this.setNumberOfDecks(decks);
        this.setUseJokers(jokers);
    }

    @Override
    public void setupGame() {

        generateDeck(); //Generate Deck from
        generatePlayers(GameConstants.NUM_PLAYERS);
        addDealer();
    }

    private void generatePlayers(int numPlayers) {

        for(int i=0; i< numPlayers; i++) {
          addPlayer(new BlackJackPlayer());
        }
    }

    public void addDealer(){
        this.dealer = new BlackJackPlayer(true);

    }

    private void generateDeck() {
        int numberOfCardsRequired = GameConstants.NUM_CARDS_PER_SUIT * GameConstants.NUM_SUITS * GameConstants.NUM_DECKS;
        for(int i =0; i< numberOfCardsRequired; i++){
            Card aCard =generateCard(i);
            addCard(aCard);
        }
    }

    private void addCard(Card aCard) {
        this.availableCards.add(aCard);
    }


    private Card generateCard(int value) {
        int deckNumber = (value / (GameConstants.NUM_CARDS_PER_SUIT * GameConstants.NUM_SUITS) )+1;
        int faceValue = (value % GameConstants.NUM_CARDS_PER_SUIT);
        int actualValue= faceValue;
        int suit = value / (deckNumber * GameConstants.NUM_CARDS_PER_SUIT);

        if(faceValue == 0){
            //its an ace
            actualValue = 11;
        }else if(faceValue >=10){
            //its a face card
            actualValue = 10;
        }else{

            actualValue = faceValue +1;
        }

        return new Card(GameConstants.SUITS[suit],GameConstants.CARDS[faceValue],actualValue,deckNumber);
    }


    @Override
    public void shuffleCards() {

        Collections.shuffle(availableCards);

    }

    @Override
    public void startGame() {

        this.setHandsPlayed(0);
        boolean flag = false;
        int firstDealComplete =0;
        do {
            //Serving the Players first, One card each then the dealer until everyone has two cards
            for(BlackJackPlayer plyr : this.getPlayers()) {
                this.dealCard(plyr);
            }
            this.dealCard(this.getDealer());
            firstDealComplete++;
        }while(firstDealComplete < 2);

    }


    @Override
    public void addPlayer(BlackJackPlayer player) {
           this.players.add(player);
    }

    @Override
    public BlackJackPlayer getPlayer(int index) {
        return this.getPlayers().get(index);
    }

    @Override
    public void dealCard(BlackJackPlayer player) {
        player.addCard(availableCards.get(this.lastCardIndex));
        this.lastCardIndex++;

    }

    @Override
    public Object getGameState() {
        return null;
    }

    @Override
    public void endGame() {

    }


    //Getters & Setters
    public int getHandsPlayed() {
        return handsPlayed;
    }

    public void setHandsPlayed(int handsPlayed) {
        this.handsPlayed = handsPlayed;
    }


    public int getNumberOfDecks() {
        return numberOfDecks;
    }

    public void setNumberOfDecks(int numberOfDecks) {
        this.numberOfDecks = numberOfDecks;
    }

    public boolean isUseJokers() {
        return useJokers;
    }

    public void setUseJokers(boolean useJokers) {
        this.useJokers = useJokers;
    }

    public BlackJackPlayer getDealer() {
        return dealer;
    }

    public void setDealer(BlackJackPlayer dealer) {
        this.dealer = dealer;
    }

    public List<BlackJackPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<BlackJackPlayer> players) {
        this.players = players;
    }

    public List<Card> getAvailableCards() {
        return availableCards;
    }

    public void setAvailableCards(List<Card> availableCards) {
        this.availableCards = availableCards;
    }

    public int getLastCardIndex() {
        return lastCardIndex;
    }

    public void setLastCardIndex(int lastCardIndex) {
        this.lastCardIndex = lastCardIndex;
    }


}
