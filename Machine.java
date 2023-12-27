import java.util.Scanner;

public class Machine {

    private int[] everything;
    private int[][] patternTemplate;
    private int[] sequenceOfNumbers;
    private int[][] playerCard;
    private int[][] playerMarkedCard;
    private boolean lastNumberIsOnThePlayerCard;
    private int[][] patternTemplate90;
    private int[][] patternTemplate180;
    private int[][] patternTemplate270;
    
    public Machine() {
        everything = new int[125];
        patternTemplate = new int [5][5];
        sequenceOfNumbers = new int [75];
        playerCard = new int [5][5];
        playerMarkedCard = new int [5][5];
        lastNumberIsOnThePlayerCard = false;
        patternTemplate90 = new int [5][5];
        patternTemplate180 = new int [5][5];
        patternTemplate270 = new int [5][5];
    }

    public void readInput() {
        Scanner keyboard = new Scanner(System.in);
        //now we have a scanner, and the text "Please enter input" will appear to prompt the user to enter the numbers


        for (int i = 0; i < everything.length; i++) {
            if (keyboard.hasNextInt()) {
                everything[i] = keyboard.nextInt();
            } else {
                everything[i] = -1;
            }
        }
        //this for loop wil see if the input has an integer ready to be taken in. If so, it takes it into an array.
        //If there are no integers to be read, it starts inputting -1s as place holders.

        int twentySixthElementPosition = 0;
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 5; column ++) {
                patternTemplate[row][column] = everything[twentySixthElementPosition];
                twentySixthElementPosition++;
            }
        }
        //this for loop will take the first 25 integers of the array from before and store them into a 2D array to act as a Bingo Card Pattern Template

        int firstNegativeOnePosition = 0;
    
        for (int index = everything.length - 1; everything[index] == -1; index--) {
            firstNegativeOnePosition = index;
        }
        //index now holds the position right before last number on the player card

        int playerCardNumberPositionCounter = firstNegativeOnePosition - 25;
        int firstPlayerCardNumberPosition = playerCardNumberPositionCounter;
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 5; column ++) {
                playerCard[row][column] = everything[playerCardNumberPositionCounter];
                playerCardNumberPositionCounter++;
            }
        }
        //this for loop take the first 25 integers before the placeholder -1s and stores them in a 2D array to act as a player's Bingo Card

        for (int i = 0; i < sequenceOfNumbers.length; i++) {
            sequenceOfNumbers[i] = -1;
        }
        //puts placeholder -1s into sequenceOfNumbers in case less than all 75 possible numbers are called

        int index = 0;
        for (int i = twentySixthElementPosition; i < firstPlayerCardNumberPosition; i++ ) {
            sequenceOfNumbers[index] = everything[i];
            index++;
        }
        //this for loop takes the integers in between the first 25 and the first 25 before the placeholder -1s
        
        keyboard.close();
    }

//playerMarkedCard should first be filled with 0s, to signify there is no winning pattern on the player's card
//then as you compare the numbers called with each number on the player's card, for the positions that store the same value, input a 1 on the marked card
//then you will compare the 1s and 0s of the marked card and winning pattern

    public void marker() {
        //marks the playerMarkedCard with 1s/4s where the player has a called number
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
                playerMarkedCard[row][column] = 0;
            }
        }
        //filled playerMarkedCard with 0s as placeholders

        int lastPositionOfCalledNumbersInSequenceOfNumbers = 0;
        for (int i = 0; sequenceOfNumbers[i] != -1; i++) {
            lastPositionOfCalledNumbersInSequenceOfNumbers++;
        }
        //gets the position of the last called number

        for (int i = 0; i < lastPositionOfCalledNumbersInSequenceOfNumbers + 1; i++) {
            for (int row = 0; row < 5; row++) {
                for (int column = 0; column < 5; column++) {
                    if (playerCard[row][column] == sequenceOfNumbers[i]) {
                        if (isItCrazy(patternTemplate) == true) {
                            playerMarkedCard[row][column] = 4;
                        } else {
                            playerMarkedCard[row][column] = 1;
                        }
                    }
                    if (sequenceOfNumbers[lastPositionOfCalledNumbersInSequenceOfNumbers] == playerCard[row][column]) {
                        lastNumberIsOnThePlayerCard = true;
                    }
                }
            }
        }
        //compares the numbers the player has with the numbers called. If there is a match, store a 1 on the marked card
        //checks every iteration to see if the last number called is one of the numbers on the player card
    }

    public boolean isItCrazy (int [][] array) {
        //checks to see if the array passed is a crazy array
        int counter = 0;
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
                if (array[row][column] == 4) {
                    counter++;
                }
            }
        }
        if (counter > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int[][] rotator(int [][] array) {
        //rotates array passed as the parameter
        for (int row  = 0; row < 5; row++) {
            for (int column = 0; column < 5; column++) {
                array[row][column] = patternTemplate[column][4 - row];
            }
        }
        return array;
    }

    public void rotationChecker() {
        //checks to see if you need to rotate the array
        if (isItCrazy(patternTemplate) == true) {
            rotator(patternTemplate);
            for (int row = 0; row < 5; row++) {
                for (int column = 0; column < 5; column++) {
                    patternTemplate90[row][column] = (rotator(patternTemplate))[row][column];
                }
            }
            rotator(patternTemplate90);
            for (int row = 0; row < 5; row++) {
                for (int column = 0; column < 5; column++) {
                    patternTemplate180[row][column] = (rotator(patternTemplate90))[row][column];
                }
            }
            rotator(patternTemplate180);
            for (int row = 0; row < 5; row++) {
                for (int column = 0; column < 5; column++) {
                    patternTemplate270[row][column] = (rotator(patternTemplate180))[row][column];
                }
            }
        }
    }

    public String isItAWinner() {
        int counterOne = 0;
        int counterTwo = 0;
        int counterTwo90 = 0;
        int counterTwo180 = 0;
        int counterTwo270 = 0;
        if (lastNumberIsOnThePlayerCard == false) {
            return "NO BINGO";
        } else {
            if ((isItCrazy(patternTemplate)) == false) {
                for (int row = 0; row < 5; row++) {
                    for (int column = 0; column < 5; column++) {
                        if (patternTemplate[row][column] != playerMarkedCard[row][column]) {
                            counterOne++;
                        }
                    }
                }
                if (counterOne > 0) {
                    return "NO BINGO";
                } else {
                    return "BINGO!";
                }
            } else {
                for (int row = 0; row < 5; row++) {
                    for (int column = 0; column < 5; column++) {
                        if (patternTemplate[row][column] != playerMarkedCard[row][column]) {
                            counterTwo++;
                        }
                    }
                }
                
                for (int row = 0; row < 5; row++) {
                    for (int column = 0; column < 5; column++) {
                        if (patternTemplate90[row][column] != playerMarkedCard[row][column]) {
                            counterTwo90++;
                        }
                    }
                }
                
                for (int row = 0; row < 5; row++) {
                    for (int column = 0; column < 5; column++) {
                        if (patternTemplate180[row][column] != playerMarkedCard[row][column]) {
                            counterTwo180++;
                        }
                    }
                }

                for (int row = 0; row < 5; row++) {
                    for (int column = 0; column < 5; column++) {
                        if (patternTemplate270[row][column] != playerMarkedCard[row][column]) {
                            counterTwo270++;
                        }
                    }
                }

                if (counterTwo != 0 && counterTwo90 != 0 && counterTwo180 != 0 && counterTwo270 != 0) {
                    return "NO BINGO";
                } else {
                    return "BINGO!";
                }
            }
        }
    }
}