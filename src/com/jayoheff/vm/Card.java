package com.jayoheff.vm;

public class Card {

    private String suit;
    private String faceValue;
    private int gameValue;
    private int deck;

    public Card(){}

    public Card(String suit, String faceValue, int gameValue, int deck){
        this.faceValue = faceValue;
        this.gameValue = gameValue;
        this.suit = suit;
        this.deck = deck;
    }



    //Getters and Setters

    public int getDeck() {
        return deck;
    }

    public void setDeck(int deck) {
        this.deck = deck;
    }


    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
    }

    public int getGameValue() {
        return gameValue;
    }

    public void setGameValue(int gameValue) {
        this.gameValue = gameValue;
    }

}
