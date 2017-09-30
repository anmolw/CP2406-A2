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
            ImageIcon cardImage = new ImageIcon(new ImageIcon(card.getImage()).getImage().getScaledInstance(260, 380, Image.SCALE_SMOOTH));
            JButton imageButton;
            if (game.isValidMove(card)) {
                imageButton = new JButton(cardImage);
                imageButton.addActionListener(this);
            }
            else {
                cardImage = new ImageIcon(GrayFilter.createDisabledImage(cardImage.getImage()));
                imageButton = new JButton(cardImage);
            }
            this.add(imageButton);
        }
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Image clicked on");
    }
}
