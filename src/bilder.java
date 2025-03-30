import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class bilder {
    private JButton prevBtn;
    private JPanel mainPanel;
    private JButton nextBtn;
    private JLabel picLabel;
    private JTextField suchFeld;
    private JButton wortSuchenButton;
    private JButton neuesSpielButton;
    private JButton schließenButton;
    private JLabel Striche;
    private JLabel Counter;

    private int picCounter = 1;
    private String wordToGuess;
    private StringBuilder currentGuess;
    private int remainingAttempts;
    private List<String> wordList;

    public bilder() {
        // Initialize the word list//W3school
        wordList = new ArrayList<>();
        wordList.add("Apfel");
        wordList.add("Sonne");
        wordList.add("Auto");

        // Set up the initial word
        startNewGame();

        // Button actions
        wortSuchenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char guess = suchFeld.getText().toUpperCase().charAt(0);
                processGuess(guess);
                suchFeld.setText("");
            }
        });

        neuesSpielButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        prevBtn.addActionListener(new ImageChangeBtnClick(-1));
        nextBtn.addActionListener(new ImageChangeBtnClick(1));

        // Initial image display
        updateImage();
        schließenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void startNewGame() {
        // Randomly select a new word
        Random rand = new Random();
        wordToGuess = wordList.get(rand.nextInt(wordList.size())).toUpperCase();
        currentGuess = new StringBuilder("_".repeat(wordToGuess.length()));
        remainingAttempts = 9;

        Striche.setText(currentGuess.toString());
        picCounter = 1;  // Reset image to the first one
        updateImage();
    }

    private void processGuess(char guess) {
        boolean correctGuess = false;

        // Check if the guess is in the word
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == guess) {
                currentGuess.setCharAt(i, guess);
                correctGuess = true;
            }
        }

        // If incorrect, reduce attempts and update image
        if (!correctGuess) {
            remainingAttempts--;
            picCounter++;
            if (remainingAttempts == 0) {
                JOptionPane.showMessageDialog(mainPanel, "Du hast es nicht geschafft! Das Wort war: " + wordToGuess);
                startNewGame(); // Restart game after failure
            }
        }

        Striche.setText(currentGuess.toString());
        updateImage();

        // Update the Counter label with the remaining attempts
        Counter.setText("Verbleibende Versuche: " + remainingAttempts);

        // Check if the word is completely guessed
        if (currentGuess.toString().equals(wordToGuess)) {
            JOptionPane.showMessageDialog(mainPanel, "Glückwunsch! Du hast das Wort erraten!");
            startNewGame(); // Restart game after winning
        }
    }


    private void updateImage() {
        // Update the image based on the number of attempts remaining
        ImageIcon new_picture = new ImageIcon("hangman/hangman" + picCounter + ".png");
        Image image = new_picture.getImage(); // transform it
        Image newimg = image.getScaledInstance(250, 250, Image.SCALE_SMOOTH); // scale it smoothly
        new_picture = new ImageIcon(newimg);  // transform it back
        picLabel.setIcon(new_picture);
    }

    private class ImageChangeBtnClick implements ActionListener {
        private int direction;

        public ImageChangeBtnClick(int val) {
            this.direction = val;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            picCounter += direction;

            if (picCounter < 1) picCounter = 10;
            else if (picCounter > 10) picCounter = 1;

            updateImage();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hangman Game");
        frame.setContentPane(new bilder().mainPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}