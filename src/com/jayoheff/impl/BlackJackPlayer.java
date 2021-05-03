package com.jayoheff.impl;

import com.jayoheff.constants.GameConstants;
import com.jayoheff.interfaces.Player;
import com.jayoheff.vm.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BlackJackPlayer implements Player {

    private boolean dealer;
    private List<Card> cardHand;
    private int score;
    private boolean stands;
    private boolean bust;
    private boolean blackjack;
    private boolean natural;


    public void calculateScore(){
        int score = 0, aceCount=0;

        //using a comparator to sort the hands on GameValue ascending. which would put aces at the end
        Comparator<Card> gameValueComparator = new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return Integer.compare( o1.getGameValue(),o2.getGameValue());
            }
        };


        Collections.sort(cardHand,gameValueComparator);

        for(Card c : cardHand) {
            boolean isAnAce = c.getFaceValue().equalsIgnoreCase(GameConstants.CARDS[0]);

            if (isDealer()) {



                if(score  < GameConstants.DEALER_STAND_SCORE) {
                    score += c.getGameValue();

                }

                if(score >= GameConstants.DEALER_STAND_SCORE && score <= GameConstants.WIN_SCORE){
                    this.setStands(true);
                }


            } else {


                if (score + c.getGameValue() > GameConstants.WIN_SCORE) {
                    if (isAnAce) {
                        c.setGameValue(1);
                    }
                }
                score += c.getGameValue();

            }


            this.score = score;
            this.setBust(this.score > GameConstants.WIN_SCORE);
            this.setBlackjack(this.score == GameConstants.WIN_SCORE);
        }
    }

    public BlackJackPlayer(){
        this(false); //defaulting a player to not be dealer
    }
    public BlackJackPlayer(boolean dealer){
        this.setDealer(dealer);
        this.setCardHand( new ArrayList<>()); // initializing to an empty hand
    }

    public void addCard(Card card){
        this.cardHand.add(card);
        calculateScore();
    }

    //Getters and Setters


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isDealer() {
        return dealer;
    }

    public void setDealer(boolean dealer) {
        this.dealer = dealer;
    }

    public List<Card> getCardHand() {
        return cardHand;
    }

    public void setCardHand(List<Card> cardHand) {
        this.cardHand = cardHand;
    }


    public boolean isStands() {
        return stands;
    }

    public void setStands(boolean stands) {
        this.stands = stands;
    }

    public boolean isBust() {
        return bust;
    }

    public void setBust(boolean bust) {
        this.bust = bust;
    }

    public boolean isBlackjack() {
        return blackjack;
    }

    public void setBlackjack(boolean blackjack) {
        this.blackjack = blackjack;
    }

    public boolean isEligiblePlayer() {
        return !isBust() && !isStands() && !isBlackjack();
    }

    public boolean isNatural() {
        return natural;
    }

    public void setNatural(boolean natural) {
        this.natural = natural;
    }
}
