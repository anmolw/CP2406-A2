import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame implements ActionListener {

   private Game game;
   private JPanel infoPanel;
   private CardChooser chooserPanel;
   private JButton passButton;
   private JPanel topPanel;
   private JLabel lastCard;
   private JLabel categoryLabel;
   private JLabel currentTurn;
   private JPanel categoryPanel;
   private Card chosenCard;

    public GameWindow(Game game) {
        super();
        this.game = game;
        this.setTitle("Mineral Supertrumps");
        this.setSize(1280, 800);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        topPanel = new JPanel();
        categoryLabel = new JLabel("");
        lastCard = new JLabel();
        currentTurn = new JLabel("Player 1's Turn");
        topPanel.add(currentTurn);
        passButton = new JButton("Pass");
        passButton.addActionListener(this);
        topPanel.add(passButton);
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        this.updatePlayerInfo();
        this.add(topPanel, BorderLayout.NORTH);
        this.add(infoPanel, BorderLayout.WEST);
        chooserPanel = new CardChooser(game.getCurrentPlayer().hand, game, this);
        this.add(chooserPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private void updatePlayerInfo() {
        infoPanel.removeAll();
        int count = 1;
        categoryLabel.setText("Category: "+game.getCurrentCategory());
        infoPanel.add(categoryLabel);
        for (Player player : game.getPlayers()) {
            JLabel playerLabel = new JLabel("Player " + count++);
            JLabel cardCount = new JLabel("Cards: " + player.hand.size());
            String passed = (player.hasPassed()) ? "Yes" : "No";
            JLabel hasPassed = new JLabel("Passed: " + passed);
            infoPanel.add(playerLabel);
            infoPanel.add(cardCount);
            infoPanel.add(hasPassed);
        }
        this.validate();
    }

    private void nextTurn() {
        if(game.gameOver()) {
            currentTurn.setText("Game Over!");
            passButton.setText("Exit");
            chooserPanel.disableAllButtons();
            return;
        }
        chosenCard = null;
        lastCard.setText(game.displayLastCard());
        currentTurn.setText("Player " + (game.getCurrentPlayerID() + 1) + "'s Turn");
        chooserPanel.update(game.getCurrentPlayer().hand);
        this.updatePlayerInfo();
        this.pack();
    }

    private void chooseCategory() {
        if (categoryPanel == null) {
            categoryPanel = new JPanel();
            categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
            categoryPanel.add(new JLabel("Choose Category:"));
            for (String category: game.getCategories()) {
                JButton categoryButton = new JButton(category);
                categoryButton.setActionCommand("Category "+category);
                categoryButton.addActionListener(this);
                categoryPanel.add(categoryButton);
            }
            this.add(categoryPanel, BorderLayout.EAST);
            this.validate();
        }

        categoryPanel.setVisible(true);
        chooserPanel.disableAllButtons();
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("Pass")) {
            game.passTurn();
            nextTurn();
        } else if (event.getActionCommand().startsWith("Card")) {
            String cardChoice = event.getActionCommand().substring(5);
            System.out.println(cardChoice);
            Card card = game.getCardWithName(cardChoice, game.getCurrentPlayer().hand);
            if (game.hasCategoryChoice() && !(card instanceof SuperTrumpCard)) {
                chosenCard = card;
                chooseCategory();
            }
            else {
                game.playTurn(card);
                nextTurn();
            }

        } else if (event.getActionCommand().startsWith("Category")) {
            String categoryChoice = event.getActionCommand().substring(9);
            System.out.println(categoryChoice);
            categoryPanel.setVisible(false);
            game.setCategory(categoryChoice);
            game.playTurn(chosenCard);
            nextTurn();
        }
        else if(event.getActionCommand().equals("Exit")) {
            this.setVisible(false);
            System.exit(0);
        }
    }
}
