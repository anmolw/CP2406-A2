import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CardChooser extends JPanel {

    Game game;
    GameWindow ui;

    public CardChooser(ArrayList<Card> cards, Game game, GameWindow ui) {
        super();
        this.game = game;
        this.ui = ui;
        update(cards);
    }

    public void update(ArrayList<Card> cards) {
        this.removeAll();
        int rows = cards.size() / 3; // 3 cards in each row
        this.setLayout(new GridLayout(rows, 3));
        for (Card card : cards) {
            ImageIcon cardImage = new ImageIcon(new ImageIcon(card.getImage()).getImage().getScaledInstance(240, 360, Image.SCALE_SMOOTH));
            JButton imageButton;
            if (game.isValidMove(card)) {
                imageButton = new JButton(cardImage);
                imageButton.addActionListener(ui);
                imageButton.setActionCommand("Card " + card.getName());
            } else {
                cardImage = new ImageIcon(GrayFilter.createDisabledImage(cardImage.getImage()));
                imageButton = new JButton(cardImage);
            }
            this.add(imageButton);
        }
        this.validate();
    }

    public void disableAllButtons() {
        for (Component comp: this.getComponents()) {
            if (comp instanceof JButton) {
                ((JButton) comp).removeActionListener(ui);
            }
        }
    }
}
