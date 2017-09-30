import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame implements ActionListener {

    Game game;
    JPanel playerInfoPanel;
    CardChooser chooserPanel;
    JButton passButton;
    JPanel topPanel;
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
        playerInfoPanel = new JPanel(new GridLayout(game.getNumPlayers(), 1));
        this.updatePlayerInfo();
        this.add(topPanel, BorderLayout.NORTH);
        this.add(playerInfoPanel, BorderLayout.WEST);
        chooserPanel = new CardChooser(game.getCurrentPlayer().hand, game, this);
        this.add(chooserPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private void updatePlayerInfo() {
        playerInfoPanel.removeAll();
        int count = 1;
        for (Player player: game.getPlayers()) {
            JLabel playerLabel = new JLabel("Player "+count++);
            playerInfoPanel.add(playerLabel);
        }
    }

    public void updateTopPanel(Game game) {

    }

   private void nextTurn() {
        if (chooserPanel != null) {
            this.remove(chooserPanel);
        }
        currentTurn.setText("Player "+game.getCurrentPlayerID()+1+"'s Turn");
        chooserPanel = new CardChooser(game.getCurrentPlayer().hand, game, this);
        this.add(chooserPanel, BorderLayout.CENTER);
        this.validate();
    }

    private void chooseCategory() {

    }

    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("Pass")) {
            game.passTurn();
            nextTurn();
        }
        else if (event.getActionCommand().startsWith("Card")) {
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
