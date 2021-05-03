package com.jayoheff.impl;

import com.jayoheff.constants.GameConstants;
import com.jayoheff.interfaces.GameRules;

public class BlackJackRules implements GameRules {


    public static boolean isThereANaturalBlackjack(BlackJack game){

        if(game.getHandsPlayed() > 1){
            return false;
        }else{
            return true;
        }
    }

    public static boolean doesPlayerHaveABlackJack( BlackJackPlayer player){

        return (player.getScore() == GameConstants.WIN_SCORE);
    }


    }
