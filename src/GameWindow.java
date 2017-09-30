import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame implements ActionListener {

    Game game;
    JPanel infoPanel;
    CardChooser chooserPanel;
    JButton passButton;
    JPanel topPanel;
    JLabel lastCard;
    JLabel currentTurn;

    public GameWindow(Game game) {
        super();
        this.game = game;
        this.setTitle("Mineral Supertrumps");
        this.setSize(1280, 800);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        topPanel = new JPanel();
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

    public void updateTopPanel(Game game) {

    }

    private void nextTurn() {
        currentTurn.setText("Player " + (game.getCurrentPlayerID() + 1) + "'s Turn");
        chooserPanel.update(game.getCurrentPlayer().hand);
        this.updatePlayerInfo();
        this.pack();
    }

    private void chooseCategory() {

    }

    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("Pass")) {
            game.passTurn();
            nextTurn();
        } else if (event.getActionCommand().startsWith("Card")) {
            String cardChoice = event.getActionCommand().substring(5);
            System.out.println(cardChoice);
            game.playTurn(game.getCardWithName(cardChoice, game.getCurrentPlayer().hand));
            if (game.hasCategoryChoice()) {
                chooseCategory();
            }
            nextTurn();
        }
    }
}
