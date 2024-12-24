import java.util.Scanner;
import java.util.Random;

public class NumberGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain = true;
        int totalScore = 0;
        int roundNum = 0;
        System.out.println("Welcome to the Number Guessing Game");
        while (playAgain) {
            roundNum++;
            int lowerBound = 1;  
            int upperBound = 100;
            int targetNum = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
            int attemptsLeft = 7; 
            boolean roundWon = false;

            System.out.println("\nRound " + roundNum);
            System.out.println("I'm thinking of a number between " + lowerBound + " and " + upperBound);
            System.out.println("You have " + attemptsLeft + " attempts to guess it correctly.");
            while (attemptsLeft > 0) {
                System.out.print("Enter your guess: ");
                int userGuess = sc.nextInt();
                attemptsLeft--;

                if (userGuess == targetNum) {
                    System.out.println("Congratulations! You guessed the correct number.");
                    roundWon = true;
                    totalScore += 10;
                    break;
                } else if (userGuess < targetNum) {
                    System.out.println("Too low! Try again. Attempts left: " + attemptsLeft);
                } else {
                    System.out.println("Too high! Try again. Attempts left: " + attemptsLeft);
                }
            }

            // Round outcome
            if (!roundWon) {
                System.out.println("Sorry! You ran out of attempts. The number was: " + targetNum);
            } else {
                System.out.println("You won this round!");
            }

            System.out.println("Your current score: " + totalScore);

            // Ask to play again
            System.out.print("Do you want to play another round? (yes/no): ");
            String response = sc.next();
            playAgain = response.equalsIgnoreCase("yes");
        }

        System.out.println("\nGame Over! Your final score is: " + totalScore);
        System.out.println("Thanks for playing this Game");
    }
}

