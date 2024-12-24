import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    String questionText;
    String[] options;
    int correctOption;

    public Question(String questionText, String[] options, int correctOption) {
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
    }

    public boolean isCorrect(int userChoice) {
        return userChoice == correctOption;
    }
}

public class QuizApplication {
    private static final int QUESTION_TIME_LIMIT = 10; 
    private Question[] questions;
    private int score = 0;
    private int[] userAnswers;
    private int currentQuestionIndex = 0;
    private Timer timer;
    private boolean timeUp = false;

    public QuizApplication() {
        initializeQuestions();
        userAnswers = new int[questions.length];
    }

    private void initializeQuestions() {
        questions = new Question[]{
            new Question("What is the capital of France?", new String[]{"1. Berlin", "2. Madrid", "3. Paris", "4. Rome"}, 3),
            new Question("Who developed Java?", new String[]{"1. James Gosling", "2. Dennis Ritchie", "3. Bjarne Stroustrup", "4. Tim Berners-Lee"}, 1),
            new Question("What is 7 x 8?", new String[]{"1. 54", "2. 56", "3. 64", "4. 48"}, 2),
            new Question("Which planet is known as the Red Planet?", new String[]{"1. Earth", "2. Venus", "3. Jupiter", "4. Mars"}, 4)
        };
    }

    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("===== Welcome to the Quiz =====");
        System.out.println("You have " + QUESTION_TIME_LIMIT + " seconds for each question. Answer quickly!\n");

        for (currentQuestionIndex = 0; currentQuestionIndex < questions.length; currentQuestionIndex++) {
            displayQuestion(currentQuestionIndex);
            timeUp = false;

            startTimer();

            int userChoice = getUserInput(scanner);

            timer.cancel();

            if (timeUp) {
                System.out.println("Time's up! Moving to the next question.\n");
                userAnswers[currentQuestionIndex] = -1; // Mark as unanswered
            } else {
                userAnswers[currentQuestionIndex] = userChoice;
                if (questions[currentQuestionIndex].isCorrect(userChoice)) {
                    System.out.println("Correct!\n");
                    score++;
                } else {
                    System.out.println("Wrong! The correct answer was: " + questions[currentQuestionIndex].correctOption + "\n");
                }
            }
        }

        displayResults();
        scanner.close();
    }

    private void displayQuestion(int index) {
        Question q = questions[index];
        System.out.println("Question " + (index + 1) + ": " + q.questionText);
        for (String option : q.options) {
            System.out.println(option);
        }
        System.out.print("Enter your choice (1-4): ");
    }

    private int getUserInput(Scanner scanner) {
        while (!timeUp && !scanner.hasNextInt()) {
            scanner.next();
            if (!timeUp) System.out.print("Please enter a valid choice (1-4): ");
        }
        if (timeUp) return -1; 
        return scanner.nextInt();
    }

    private void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeUp = true;
                System.out.println("\nTime's up!");
            }
        }, QUESTION_TIME_LIMIT * 1000);
    }

    private void displayResults() {
        System.out.println("===== Quiz Results =====");
        System.out.println("Your final score: " + score + "/" + questions.length);

        System.out.println("\nSummary:");
        for (int i = 0; i < questions.length; i++) {
            String status;
            if (userAnswers[i] == -1) {
                status = "Unanswered";
            } else if (questions[i].isCorrect(userAnswers[i])) {
                status = "Correct";
            } else {
                status = "Incorrect";
            }
            System.out.println("Question " + (i + 1) + ": " + status);
        }

        System.out.println("\nThank you for playing the quiz!");
    }

    public static void main(String[] args) {
        QuizApplication quiz = new QuizApplication();
        quiz.startQuiz();
    }
}

