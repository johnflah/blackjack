package com.jayoheff;

import com.jayoheff.constants.GameConstants;
import com.jayoheff.constants.UserInterfaceStrings;
import com.jayoheff.impl.BlackJack;
import com.jayoheff.util.BlackJackUtil;
import com.jayoheff.vm.Card;
import com.jayoheff.impl.BlackJackPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    /**
     * Entrypoint to Application
     * @param args
     */
    public static void main(String[] args) {

        boolean quitGame = false;
        String input ="";


        do {
            //Using a utility class to hide away the game complexity.
            BlackJackUtil bUtil = new BlackJackUtil(GameConstants.NUM_DECKS, GameConstants.USE_JOKERS);

            input = bUtil.displayHomeScreen();

            if (input.equalsIgnoreCase(UserInterfaceStrings.EXIT)) {
                System.out.println(UserInterfaceStrings.EXIT_MESSAGE);
            } else if (input.equalsIgnoreCase(UserInterfaceStrings.PLAY)) {
                //Bootstrapping the game
                bUtil.playBlackJack();
            } else {
                System.out.println(UserInterfaceStrings.INVALID_MENU_PROMPT);
            }
        }while(!input.equalsIgnoreCase(UserInterfaceStrings.EXIT));

    }





}
