/**
 * Purpose: Implement the classic High-Low Game.
 * @author C2C Hampton Ford
 */

import java.util.Random;
import java.util.Scanner;
import java.lang.Math;

public class main {
    static int UPPERBOUND = 100;

    /**
     * Purpose: Validate User Input using hasNext().
     * @return String user's guess.
     */
    private static String validateInputStr() {
        String str = "";
        Scanner genericString = new Scanner(System.in);
        if (!genericString.hasNextInt()) {
            str = genericString.nextLine();
            return str;
        }
        else {
            validateInputStr();
        }
        return str;
    }

    /**
     * Purpose: Validate user input using hasNextInt().
     * @return int user's guess.
     */
    private static int validateInput() {
        Scanner genericInt = new Scanner(System.in);
        int number = 0;
        if (genericInt.hasNextInt()) {
            number = genericInt.nextInt();
            return number;
        }
        else {
            validateInput();
        }
        return number;
    }

    /**
     * Purpose: Select the appropriate message upon winning a game.
     *
     * @param game_guesses This number of guesses during that game.
     * @param OPTIMALGUESSCNT This is the number of guesses required assuming optimal guessing.
     * @return 0 on success.
     */
    private static int selectWinOption(int game_guesses, int OPTIMALGUESSCNT) {
        if (game_guesses == (OPTIMALGUESSCNT-2) || game_guesses == (OPTIMALGUESSCNT-1)) {
            System.out.println("You win! Phenomenal job!");
        }
        else if (game_guesses < (OPTIMALGUESSCNT-2)) {
            System.out.println("You win! Nice job!");
        }
        else if (game_guesses == (OPTIMALGUESSCNT) || game_guesses == (OPTIMALGUESSCNT+1)) {
            System.out.println("You win! You did alright.");
        }
        else {
            System.out.println("You only won because it's impossible to lose.");
        }
        return 0;
    }

    /**
     * Purpose: Print required dialogue upon winning.
     *
     * @param total_guesses This is this total number of guesses used in the seshion.
     * @param num_games The number of games played in this session.
     * @param OPTIMALGUESSCNT This is the number of guesses required assuming optimal guessing.
     * @return 0 on success.
     */
    private static int printEndDialogue(int total_guesses, int num_games, int OPTIMALGUESSCNT) {
        System.out.println("The maximum possible number of guesses was: " + OPTIMALGUESSCNT);
        System.out.println("Your stats are as follows:");
        System.out.println("Total Guesses: " + total_guesses);
        float avg_guesses = (float)total_guesses/num_games;
        System.out.printf("Average Number of guesses per game: %.1f.\n", avg_guesses);

        if (avg_guesses == (OPTIMALGUESSCNT-2) || avg_guesses == (OPTIMALGUESSCNT-1)) {
            System.out.println("You Did A Phenomenal Job!!! Here's something for you:");
        }
        else if (avg_guesses < (OPTIMALGUESSCNT-2)) {
            System.out.println("Nice job!");
        }
        else if (avg_guesses == (OPTIMALGUESSCNT) || avg_guesses == (OPTIMALGUESSCNT+1)) {
            System.out.println("You did alright.");
        }
        else {
            System.out.println("I'm sure you'll do better next.");
        }

        System.out.println("My batteries are low, and it's getting dark... goodbye");

        return 0;
    }

    /**
     * Purpose: Generate a secret number.
     *
     * @param arg1 This is absolutely never used, but we have to include this attribute.
     * @return The secret number to be guessed.
     */
    private static int getSecretNum(int arg1) {
        Random rand = new Random();
        return rand.nextInt(UPPERBOUND)+1; //make range 1-100
    }

    /**
     * Purpose: Calculate the optimal number of guesses.
     *
     * @param operand The first log to be taken.
     * @param base The log base you want to take.
     * @return The maximum number of optimal guesses that called be taken b/w 1 and some bound.
     */
    private static int getlog(int operand, int base) {
        double answ = Math.log(operand)/Math.log(base);
        if (answ == Math.ceil(answ)) {
            return (int)answ;
        }
        else {
            return (int)answ+1;
        }
    }

    /**
     * Purpose: Play the high-low game.
     * @param args Required for main().
     */
    public static void main(String[] args) {
        //Calculate the optimal number of guesses
        int OPTIMALGUESSCNT = getlog(UPPERBOUND,10);

        //Begin game
        System.out.println("This is the High Low Game. You WILL have fun.");

        //Print Name
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your name or be annihilated: ");

        String name = validateInputStr();
        if (name.isEmpty()) {
            System.out.println("You didn't enter a name. You must be Nobody. Glad I'm not a cyclops, haha jk, unless...\nHello Player 1.");
        }
        System.out.printf("Hello %s\n", name);

        boolean playAgain = false;
        int total_guesses = 0;
        short num_games = 0;
        do { //setup each game
            int game_guesses = 0;
            int secretNum = getSecretNum(Integer.parseInt("BEEF",16));
            int guess = -1;
            int prev_guess = -1;
            while (guess != secretNum) { //A single game seshion
                Scanner scanGuess = new Scanner(System.in);
                System.out.println("Guess a number between 0 and 100: ");
                guess = validateInput();
                ++game_guesses;
                if (guess > UPPERBOUND || guess <= 0) {
                    System.out.println("Your guess wasn't even in the range given!");
                }
                if (guess > secretNum) {
                    if (prev_guess != -1 && guess >= prev_guess && prev_guess >= secretNum) {
                        System.out.println("You incorrectly guessed even higher this time! Lower your angle of attack!");
                    }
                    System.out.println("You guessed too high.");
                    prev_guess = guess;
                }
                else if (guess < secretNum) {
                    if (prev_guess != -1 && guess <= prev_guess && prev_guess <= secretNum) {
                        System.out.println("You incorrectly guessed even lower this time! Aim high airman!");
                    }
                    System.out.println("You guessed too low.");
                    prev_guess = guess;
                }
                else {
                    //The game has effectively ended -> List Win Possibilities
                    selectWinOption(game_guesses, OPTIMALGUESSCNT);
                    //Check whether to play again
                    boolean goodInput = false;
                    while (!goodInput) {
                        System.out.println("Would you like to play again? Enter yes (y) or no (n): ");
                        Scanner clearBuff = new Scanner(System.in);
                        String raw_playAgain = "";
                        raw_playAgain = validateInputStr();
                        //check input (I didn't feel like using Patterns
                        if (raw_playAgain.equalsIgnoreCase("yes") || raw_playAgain.equalsIgnoreCase("y")) {
                            playAgain = true;
                            goodInput = true;
                        }
                        else if (raw_playAgain.equalsIgnoreCase("no") || raw_playAgain.equalsIgnoreCase("n")) {
                            playAgain = false;
                            goodInput = true;
                        }
                        else {
                            System.out.println("Enter valid input! Ahhhh.");
                        }
                    } //end check play again input
                }
            }
            System.out.println("You had " + game_guesses + " guesses during this game.\n\n");
            total_guesses += game_guesses;
            ++num_games;
        }while(playAgain);

        printEndDialogue(total_guesses, num_games, OPTIMALGUESSCNT);
    }
}