package cs112.lab08;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class HelloApplication extends Application /*implements EventHandler<ActionEvent>*/ {

    // CONSTANTS

    // Array of LoteriaCards to use for the game
    private static final LoteriaCard[] LOTERIA_CARDS = {
            new LoteriaCard("Las matematicas", "1.png", 1),
            new LoteriaCard("Las ciencias", "2.png", 2),
            new LoteriaCard("La Tecnología", "8.png", 8),
            new LoteriaCard("La ingeniería", "9.png", 9),
    };

    // Integer variable to track count (currently not used)
    private int count = 0;
    // Static method to shuffle and return a new (deep copy) of all Loteria cards
    private static LoteriaCard[] shuffledCards = shuffleLoteriaDeck();  // no usages yet

    // Method to shuffle and return a deep copy of the Loteria deck
    private static LoteriaCard[] shuffleLoteriaDeck() {
        // 1 usage: Called once during the initialization of 'shuffledHands'
        LoteriaCard[] cards = new LoteriaCard[LOTERIA_CARDS.length];  // Create a new array to hold LoteriaCard objects

        // Deep copy each LoteriaCard from the original Loteria deck
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new LoteriaCard(LOTERIA_CARDS[i]);  // Create a new LoteriaCard object for each card in the original deck
        }

        // Shuffle the cards using random index swapping
        for (int i = 0; i < 10*cards.length; i++) {  // Loop to shuffle 48 times (or adjust the number of iterations)
            // Generate two random indices for swapping
            int randomInt1 = (int) (Math.random() *cards.length);  // Random index 1
            int randomInt2 = (int) (Math.random() *cards.length);  // Random index 2

            // Swap the cards at the two random positions
            LoteriaCard temp = cards[randomInt1];  // Temporary variable to hold a card during the swap
            cards[randomInt1] = cards[randomInt2];
            cards[randomInt2] = temp;
        }

        return cards;
    }

    @Override
    public void start(Stage stage) throws IOException {
        LoteriaCard cardLogo=new LoteriaCard();
        //SETUP COMPONENTS
        Label titleLabel = new Label("Welcome to ECHALE STEM Loteria!");
        ImageView cardImageView = new ImageView(cardLogo.getImage());
        Label messageLabel = new Label("Click the button below to randomly draw a card.");
        Button drawCardButton = new Button("Draw Random Card");
        ProgressBar gameProgressBar = new ProgressBar(0.0);

        // CUSTOMIZE COMPONENTS
        messageLabel.setWrapText(true);
        messageLabel.setTextAlignment(TextAlignment.CENTER);
        cardImageView.setFitWidth(300);
        cardImageView.setPreserveRatio(true);
        titleLabel.setFont(new Font(15.0));
        drawCardButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(count== shuffledCards.length){
                            messageLabel.setText("GAME OVER. No more cards! Exit and run program again to reset");
                            cardImageView.setImage(cardLogo.getImage());
                            drawCardButton.setDisable(true);
                            gameProgressBar.setStyle("-fx-accent: red");
                        }
                        else{
                            LoteriaCard randomCard = shuffledCards[count];

                            // Change the image to the selected card's image
                            cardImageView.setImage(randomCard.getImage());

                            // Change the message label to display the card's name
                            messageLabel.setText(randomCard.getCardName());

                            count++;

                            double fillRatio=((double)count)/shuffledCards.length;
                            gameProgressBar.setProgress(fillRatio);
                        }
                    }

                }
        );


        // ADD COMPONENTS
        VBox vbox = new VBox();
        vbox.getChildren().addAll(titleLabel, cardImageView, messageLabel, drawCardButton, gameProgressBar);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5.0);

        // SETUP SCENE AND SHOW
        Scene scene = new Scene(vbox, 350, 500);
        stage.setScene(scene);
        stage.setTitle("ECHALE STEM Loteria");
        stage.show();
    }
/*
    @Override
    public void handle(ActionEvent actionEvent) {
        // Print message when the button is clicked
        System.out.println("Button was clicked!");

        // Get a random card index (0 to length of LOTERIA_CARDS array)
        int randomInt = (int) (Math.random() * LOTERIA_CARDS.length);

        // Select the random Loteria card
        LoteriaCard randomCard = LOTERIA_CARDS[randomInt];

        // Change the image to the selected card's image
        cardImageView.setImage(randomCard.getImage());

        // Change the message label to display the card's name
        messageLabel.setText(randomCard.getCardName());
    }*/
    public static void main(String[] args) {
        launch();
    }
}
