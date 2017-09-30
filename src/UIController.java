import java.awt.*;


public class UIController {

    Game game;
    GameWindow gameWindow;
    CardChooser chooserPanel;

    public UIController(Game game) {
        this.game = game;
        gameWindow = new GameWindow();
        this.beginTurn();
    }

    public void cardChosen(Card card) {
        game.playTurn(card);
    }

    private void beginTurn() {
        if (chooserPanel != null) {
            gameWindow.remove(chooserPanel);
        }
        chooserPanel = new CardChooser(game.getCurrentPlayer().hand, this, game);
        gameWindow.add(chooserPanel, BorderLayout.CENTER);
        gameWindow.validate();
    }

}
