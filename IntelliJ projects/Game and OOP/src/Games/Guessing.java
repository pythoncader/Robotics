package Games;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Guessing { //class must be named the same as the file name
    public static void main(String[] args) {
        System.out.println("Your best score is: "+playgame());

    }
    public static int playgame(){
        boolean play = true;
        int bestscore = 10;
        int[] guesses = new int[10];
        while(play) {
            int guessnum = 0;
            java.util.Arrays.fill(guesses, 0); //clears the array
            Random randGuess = new Random();
            int tries = 0;
            int randNum = randGuess.nextInt(10) + 1;
            int Guess;
            boolean win = false;

            System.out.println("Guess a number between 1 and 10: ");

            while (!win) {
                Guess = 0;
                try {
                    Scanner myinput = new Scanner(System.in);
                    Guess = myinput.nextInt();
                } catch(InputMismatchException myexception){
                    System.out.println("That's not a number between one and ten. Guess again: ");
                    continue;
                } catch(Exception other){
                    Scanner playinput = new Scanner(System.in);
                    while (true) {
                        System.out.println("Error occurred, Continue?");
                        String play_input = playinput.nextLine();
                        if (play_input.toLowerCase().contains("n")) {
                            play = false; //when we break out of this loop, the while(play) loop will not be true, so we will exit the program.
                            break;
                        } else if (play_input.toLowerCase().contains("y") || play_input.toLowerCase().contains("s")) {
                            break; //play is already true, so we just start the loop over
                        } else {
                            System.out.println("That's not a number between one and ten");

                        }
                    }
                    break;
                }
                boolean guessagain = false;
                if (Guess < 11 && Guess > 0) {
                    for (int i : guesses) { //same thing as saying: (for (int i = 0; i < guesses.length; i++), guesses[i]) or in Python: (for i in guesses)
                        if (i == Guess) {
                            System.out.println("You have already guessed this number. Guess again: ");
                            guessagain = true;
                        }
                    }

                    if (guessagain) {
                        continue;
                    }
                    tries++;

                    if (Guess == randNum) {
                        win = true;
                    } else if (Guess < randNum) {
                        System.out.println("Guess higher..");
                    } else {
                        System.out.println("Guess lower..");
                    }
                } else {
                    System.out.println("That's not a number between one and ten.. Guess again: ");
                }

                guesses[guessnum] = Guess;
                guessnum++;
            }
            System.out.println("You guessed it!");
            System.out.println("Number of tries: " + tries + " ");

            if (tries < bestscore) {
                bestscore = tries;
            }

            Scanner playinput = new Scanner(System.in);
            while (true){
                System.out.println("Do you want to play again?");
                String play_input = playinput.nextLine();
                if (play_input.toLowerCase().contains("n")) {
                    play = false; //when we break out of this loop, the while(play) loop will not be true, so we will exit the program.
                    break;
                } else if (play_input.toLowerCase().contains("y") || play_input.toLowerCase().contains("s")) {
                    break; //play is already true, so we just start the loop over
                } else {
                    System.out.println("I don't know what you mean..");
                }
            }


        }
        return bestscore;
    }
}