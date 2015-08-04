//Shreyan Jain Period 5
//Proj-Asg3: Project 1 Complete

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

/**
   This is the EvilHangman_Jain class that represents a version of the classic game
   Hangman in which the computer cheats to maximize its chances of winning. Instead
   of choosing a single secret word at the beginning of the game that the user must
   guess, the program keeps track of all possible secret words at each stage of the
   game. For example, at the beginning of the game, since no guesses have been made,
   the program keeps track of all the words in the dictionary instead of choosing one.
   
   The program begins with a prompt for the length of the secret word. The input
   screen only accepts an int value between 4 and 15 and will continue to stay open
   until such an int value is inputted. Once the value is entered, the main game screen
   opens up.
   
   For the GUI, the top half of the pop up screen (JFrame) is a white JPanel in which
   the hangman image is progressively drawn after the user makes incorrect guesses.
   In the bottom half, the first line depicts the secret word, with "-"'s representing
   unguessed letters. The second line tells you which letters have already been guessed,
   and the third line tells you how many incorrect guesses you have remaining. There
   are also three JButton's.
      
   The first JButton allows the user to enter a single lowercase letter that represents
   a guess for a single letter. This button should take in as input only a single lowercase 
   letter (any other input should prompt the user to try again), record the guess, and
   update the screen with the new number of guesses left and the letters already used.
   Once the player guesses a letter, the program divides up the current list of all
   possible secret words into word families, based on the locations where the guessed
   letter appears in the word. The program then chooses the largest word family as the
   new list of all possible secret words. The program then updates the word on the screen
   based on which word family was chosen - for example, if the letter "e" was guessed
   and the word family with "e" appearing as the second letter in the word was chosen,
   then the word on the screen will change the second letter, a "-", to an "e." If the
   word family chosen is the one in which the guessed letter does not appear at all, then
   the guess is counted as an incorrect guesses and the next part of the hangman image
   is drawn. If any other word family is chosen, however, then the guess is correct since
   the letter does appear in the secret word. The user is allowed ten incorrect guesses
   before he loses the game and one of the possible secret words is revealed as the actual word.
   
   The second JButton allows the user to enter an entire word that represents a guess
   for the actual word itself, not just one letter. This button should only be able to
   be clicked once, as once the user guesses a word the game is over. If there is only possible
   secret word at this stage and it is guessed, then the user wins. If however, there are
   multiple secret words remaining or the user does not guess a correct secret word, the user
   loses the game and one of the possible secret words is revealed as the actual word. 
   
   The third JButton is simply the "Exit Game" button. Once clicked, the user quits the game
   and one of the possible secret words is revealed as the actual word.
*/
public class EvilHangman_Jain {
   private static JLabel dispLetters;//JLabel to display the letters already guessed
   private static ArrayList<String> lettersUsed;//ArrayList to store the letters already guessed
   private static ArrayList<String> lettersLeft;//ArrayList to store the letters not guessed
   private static int guessesLeft;//int that represents the number of incorrect guesses left
   private static JFrame mainFrame;//the main graphics screen
   private static JLabel dispGuesses;//JLabel to display the number of guesses left
   private static JLabel dispWord;//JLabel to display the word, as revealed so far
   private static JPanel imagePanel;//Jpanel to store the hangman image
   private static String wordSoFar;//String that represents the word, as revealed so far
   private static int wordLength;//represents the length of the word
   private static ArrayList<String> allWords;//ArrayList that holds all words in the dictionary
   private static ArrayList<String> wordList;//ArrayList that holds all possible secret words
   private static ArrayList<ArrayList<String>> wordMap;//ArrayList that stores different word families
   private static int lettersCorrect;//int to store the number of letters correctly guessed
   private static boolean gameWon;//boolean that keeps track of whether the game has been won or not
   private static int wrongGuesses;//int to store the total number of wrong guesses made
      
   public static void main(String[] args) throws Exception {      
      //Reading the words of a dictionary from a text file into allWords
      allWords = new ArrayList<String>();
      File wordFile = new File("dictionary.txt");
      wordFile.createNewFile();
      Scanner s = new Scanner(wordFile);
      while (s.hasNext()){
          allWords.add(s.next());
      }
      s.close();
      
      //Setting up the game and prompting the value for wordLength
      gameWon = false;
      guessesLeft = 10;
      wrongGuesses = 0;
      lettersLeft = new ArrayList(Arrays.asList("a","b","c","d","e","f","g","h","i","j",
         "k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"));
      lettersUsed = new ArrayList<String>();
      String message1 = "Welcome to Evil Hangman, the game you're guaranteed to lose!";
      String message2 = " Enter a number between 4 and 15. This will represent the length of the word you have to guess.";
      String message3 = "Sorry, you did not enter a number in the acceptable range: 4-15. Try again.";
      wordLength = Integer.parseInt(JOptionPane.showInputDialog(message1 + message2));
      while (wordLength < 4 || wordLength > 15)
         wordLength = Integer.parseInt(JOptionPane.showInputDialog(message3));
      wordSoFar = "";
      wordList = new ArrayList<String>();
      for (String word : allWords)
         if (word.length() == wordLength)
            wordList.add(word);
      for (int i = 0; i < wordLength; i++)
         wordSoFar += "-";
      wordMap = new ArrayList<ArrayList<String>>();
           
      //Creating and displaying the main game screen and the major graphics components 
      mainFrame = new JFrame();
      imagePanel = new JPanel() {
         public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (wrongGuesses == 1)
            {
               g.drawLine(50, 350, 250, 350);
            }
            if (wrongGuesses == 2)
            {
               g.drawLine(50, 350, 250, 350);
               g.drawLine(100, 350, 100, 50);
            }
            if (wrongGuesses == 3)
            {
               g.drawLine(50, 350, 250, 350);
               g.drawLine(100, 350, 100, 50);
               g.drawLine(100, 50, 300, 50);
            }
            if (wrongGuesses == 4)
            {
               g.drawLine(50, 350, 250, 350);
               g.drawLine(100, 350, 100, 50);
               g.drawLine(100, 50, 300, 50);
               g.drawLine(300, 50, 300, 100);
            }
            if (wrongGuesses == 5)
            {
               g.drawLine(50, 350, 250, 350);
               g.drawLine(100, 350, 100, 50);
               g.drawLine(100, 50, 300, 50);
               g.drawLine(300, 50, 300, 100);
               g.drawOval(270, 100, 60, 60);
            }
            if (wrongGuesses == 6)
            {
               g.drawLine(50, 350, 250, 350);
               g.drawLine(100, 350, 100, 50);
               g.drawLine(100, 50, 300, 50);
               g.drawLine(300, 50, 300, 100);
               g.drawOval(270, 100, 60, 60);
               g.drawLine(300, 160, 300, 275);
            }
            if (wrongGuesses == 7)
            {
               g.drawLine(50, 350, 250, 350);
               g.drawLine(100, 350, 100, 50);
               g.drawLine(100, 50, 300, 50);
               g.drawLine(300, 50, 300, 100);
               g.drawOval(270, 100, 60, 60);
               g.drawLine(300, 160, 300, 275);
               g.drawLine(300, 210, 250, 190);
            }
            if (wrongGuesses == 8)
            {
               g.drawLine(50, 350, 250, 350);
               g.drawLine(100, 350, 100, 50);
               g.drawLine(100, 50, 300, 50);
               g.drawLine(300, 50, 300, 100);
               g.drawOval(270, 100, 60, 60);
               g.drawLine(300, 160, 300, 275);
               g.drawLine(300, 210, 250, 190);
               g.drawLine(300, 210, 350, 190);
            }
            if (wrongGuesses == 9)
            {
               g.drawLine(50, 350, 250, 350);
               g.drawLine(100, 350, 100, 50);
               g.drawLine(100, 50, 300, 50);
               g.drawLine(300, 50, 300, 100);
               g.drawOval(270, 100, 60, 60);
               g.drawLine(300, 160, 300, 275);
               g.drawLine(300, 210, 250, 190);
               g.drawLine(300, 210, 350, 190);
               g.drawLine(300, 275, 250, 325);
            }
            if (wrongGuesses == 10)
            {
               g.drawLine(50, 350, 250, 350);
               g.drawLine(100, 350, 100, 50);
               g.drawLine(100, 50, 300, 50);
               g.drawLine(300, 50, 300, 100);
               g.drawOval(270, 100, 60, 60);
               g.drawLine(300, 160, 300, 275);
               g.drawLine(300, 210, 250, 190);
               g.drawLine(300, 210, 350, 190);
               g.drawLine(300, 275, 250, 325);
               g.drawLine(300, 275, 350, 325);
            }
         }
      };
      imagePanel.setBackground(Color.WHITE);
      JPanel guessPanel = new JPanel();
      guessPanel.setLayout(new BoxLayout(guessPanel, BoxLayout.PAGE_AXIS));
      dispWord = new JLabel(wordSoFar);//the current word, with letters and hyphens
      dispLetters = new JLabel("Letters already used:");//the letters already guessed
      dispGuesses = new JLabel("Letter Guesses Left: " + guessesLeft);//the number of guesses left
      JButton guessLetter = new JButton("Guess a letter");
      JButton guessWord = new JButton("Guess the word");
      JButton exitButton = new JButton("Quit the game");
      guessPanel.add(dispWord);
      guessPanel.add(dispLetters);
      guessPanel.add(dispGuesses);
      guessPanel.add(guessLetter);
      guessPanel.add(guessWord);
      guessPanel.add(exitButton);
      mainFrame.add(imagePanel, BorderLayout.CENTER);
      mainFrame.add(guessPanel, BorderLayout.SOUTH);
      mainFrame.setSize(500, 600);
      mainFrame.setVisible(true);
      
      //Creating the ActionListener's for the three JButtons
      guessLetter.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            processLetterButton();
         }
      });
      
      guessWord.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            processWordButton();
         }
      });
      exitButton.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            JFrame exitFrame = new JFrame("Quit Game");
            String messageD = "Exiting Game";
            mainFrame.dispose();
         }
      });
   }
   
   /**
      The processLetterButton method, which is called when the guessLetter button is clicked.
      It processes the user's guess for a single letter by splitting up the list of all
      possible secret words into several different word families based on the location of the
      guessed letter in the words. It then chooses the largest word family as the new list
      of all possible secret words and updates the game screen.
      
      This method is called from within the guessLetter button's ActionListener.
   */
   private static void processLetterButton()
   {
      //Prompting the user for the single letter guess. Only a single lowercase letter will be accepted as input.
      JFrame promptFrame = new JFrame("Guess a Letter");
      boolean escape = true;
      String message;
      String a = "Write a single lowercase letter that represents your guess.";
      a += " Make sure not to enter a letter you've already guessed.";
      String b = "\n" + dispLetters.getText() + "\nWord so far: " + wordSoFar;
      String guess = "";
      while (escape)
      {
         guess = JOptionPane.showInputDialog(promptFrame, a+b);
         escape = false;
         for (String s : lettersUsed)
            if (s.equals(guess))
            {
               escape = true;
               a = "You've already guessed this letter. Guess again.";
            }
         char c = guess.charAt(0);
         int n = (int) c;
         if (n < 97 || n > 122 || guess.length() > 1)
         {
            escape = true;
            a = "Sorry, you did not enter a lowercase letter. Guess again.";
         }
      }
      
      //Updating the game screen, specifically dispLetters, after the user guesses a letter.
      String previous = dispLetters.getText();
      if (lettersUsed.size() > 0)
         dispLetters.setText(previous + ", " + guess);
      else
         dispLetters.setText(previous + " " + guess);
      lettersUsed.add(guess);
      
      //Processing the guess by creating the different word families based on location of the letter guessed in the words.
      //wordMap stores all the word families.
      //indexList stores the positions in which the letter guessed appears for each word family.
      for (int i = 0; i < wordLength + 1; i++)
      {
         ArrayList<String> stringList = new ArrayList<String>();
         wordMap.add(stringList);
      }
      ArrayList<ArrayList<Integer>> indexList = new ArrayList<ArrayList<Integer>>();
      for (int i = 0; i < wordLength; i++)
      {
         ArrayList<Integer> indices = new ArrayList<Integer>();
         indices.add(i);
         indexList.add(indices);
      }
      ArrayList<Integer> noIndices = new ArrayList<Integer>();
      noIndices.add(-1);
      indexList.add(noIndices);
      //Adding each word from the list of possible secret words to the correct word family
      for (String word : wordList)
      {
         boolean hasGuessLetter = false;
         int guessCount = 0;
         ArrayList<Integer> guessIndices = new ArrayList<Integer>();
         for (int i = 0; i < wordLength; i++)
         {
            if (word.substring(i, i+1).equals(guess))
            {
               guessCount++;
               hasGuessLetter = true;
               guessIndices.add(i);
            }
         }
         if (!hasGuessLetter)
            wordMap.get(wordLength).add(word);
         else {
            boolean indexListFound = false;
            for (int i = 0; i < indexList.size(); i++)
            {
                if (indexList.get(i).equals(guessIndices))
                {
                  wordMap.get(i).add(word);
                  indexListFound = true;
                }
            }
            if (!indexListFound)
            {
               indexList.add(guessIndices);
               ArrayList<String> newWords = new ArrayList<String>();
               newWords.add(word);
               wordMap.add(newWords);
            }
         }
      }
      
      //Finding the largest word family and updating the game screen, depending on which word family is chosen
      int largest = findMax(wordMap);
      wordList = wordMap.get(largest);
      wordMap = new ArrayList<ArrayList<String>>();
      if (largest == wordLength)
      {
         JFrame wrongGuessFrame = new JFrame("Wrong Guess");
         String messageD = "Sorry, the letter " + guess + " is not in my secret word.";
         JOptionPane.showMessageDialog(wrongGuessFrame, messageD);
         guessesLeft = guessesLeft - 1;
         wrongGuesses++;
         imagePanel.repaint();
      }
      else if (largest < wordLength)
      {
         JFrame rightGuessFrame = new JFrame("Correct Guess");
         String messageD = "Congratulations, the letter " + guess + " is in my secret word!";
         JOptionPane.showMessageDialog(rightGuessFrame, messageD);
         lettersCorrect++;
         wordSoFar = wordSoFar.substring(0, largest) + guess + wordSoFar.substring(largest+1, wordLength);
      }
      else
      {
         JFrame rightGuessFrame = new JFrame("Correct Guess");
         String messageD = "Congratulations, the letter " + guess + " is in my secret word!";
         JOptionPane.showMessageDialog(rightGuessFrame, messageD);
         lettersCorrect = lettersCorrect + indexList.get(largest).size();
         char[] wordChars = wordSoFar.toCharArray();
         for (int i : indexList.get(largest))
            wordChars[i] = guess.charAt(0);
         wordSoFar = String.valueOf(wordChars);
      }
      dispWord.setText(wordSoFar);
      
      //Checking if the game has been won or lost after the guess.
      if (lettersCorrect == wordLength)
         gameWon = true;
      dispGuesses.setText("Letter Guesses Left: " + guessesLeft);
      if (guessesLeft == 0 && !gameWon)
      {
         JFrame lossFrame = new JFrame("Game over!");
         JOptionPane.showMessageDialog(lossFrame, "Sorry, that was your last guess. Game over! The secret word was: "
            + wordList.get(0));
         mainFrame.dispose();
      }
      else if (gameWon)
      {
         JFrame lossFrame = new JFrame("You won!");
         JOptionPane.showMessageDialog(lossFrame, "Congratulations, you won! The secret word was: " + wordSoFar);
         mainFrame.dispose();
      }
   }   
   
   /**
      The processWordButton method, which is called when the guessWord button is clicked.
      It processes the user's guess for the secret word. If the list of possible secret
      words has more than one word, the user loses the game no matter what. If there is
      only possible secret word and the user guesses it correctly, he wins. If the user
      does not guess a possible secret word, he loses. A message dialog is shown with the 
      correct word and a specific message, depending on whether the user lost or won.
      
      This method is called from within the guessWord button's ActionListener.
   */
   private static void processWordButton()
   {
      JFrame promptFrame = new JFrame("Guess the Word");
      String guessMessage = "Enter your guess for the word.";
      guessMessage +=  "\nWord so far: " + wordSoFar;
      String guess = JOptionPane.showInputDialog(promptFrame, guessMessage);
      String correctWord = "";
      if (wordList.size() > 1)
      {
         for (int i = 0; i < wordList.size(); i++)
         {
            if (!wordList.get(i).equals(guess))
            {
               correctWord = wordList.get(i);
               break;
            }
         }
         JFrame lossFrame = new JFrame("You Lose!");
         JOptionPane.showMessageDialog(lossFrame, "Sorry, that guess was incorrect. The right word was: " + correctWord +". Game over!");
         mainFrame.dispose();
      }
      else if (wordList.size() == 1 && wordList.get(0).equals(guess))
      {
         JFrame winFrame = new JFrame("You Win!");
         JOptionPane.showMessageDialog(winFrame, "Congratulations, you won! The secret word was: " + guess);
         mainFrame.dispose();
      }
      else
      {
         JFrame lossFrame = new JFrame("You Lose!");
         JOptionPane.showMessageDialog(lossFrame, "Sorry, that guess was incorrect. The right word was: " + correctWord +". Game over!");
         mainFrame.dispose();
      }
   }
   
   /**
      The findMax method, which takes an ArrayList of ArrayLists as input. It returns the 
      location (index) in the inputted ArrayList of the ArrayList element of maximum size.
      It is called from the processLetterButton method when the word family of greatest size
      must be found.
      
      If multiple ArrayList elements have the maximum size, the one that appears first
      in the big ArrayList is chosen, with one exception: if the ArrayList element
      corresponding to the word family where the guessed letter does not appear has the
      maximum size, its index is returned no matter what.
   */
   private static int findMax(ArrayList<ArrayList<String>> map)
   {
      int maxIndex = 0;
      int max = map.get(0).size();
      for (int i = 1; i < map.size(); i++)
      {
         int next = map.get(i).size();
         if (next > max)
         {
            max = next;
            maxIndex = i;
         }
      }
      if (map.get(wordLength).size() == max)
         return wordLength;
      return maxIndex;
   }
}
