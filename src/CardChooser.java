import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CardChooser extends JPanel implements ActionListener {

    UIController ui;
    Game game;
    public CardChooser(ArrayList<Card> cards, UIController ui, Game game) {
        super();
        this.ui = ui;
        this.game = game;

        int rows = cards.size() / 4; // 4 cards in each row
        this.setLayout(new GridLayout(rows, 4));
        for (Card card: cards) {
            ImageIcon cardImage = new ImageIcon(card.getImage());
            JButton imageButton = new JButton(cardImage);
            if (game.isValidMove(card)) {
                imageButton.addActionListener(this);
            }
            else {
                // gray out card
            }
            this.add(imageButton);
        }
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Image clicked on");
    }
}
