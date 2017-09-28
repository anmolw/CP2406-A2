import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame implements ActionListener {

    public GameWindow() {
        super();
        this.setTitle("Mineral Supertrumps");
        this.setSize(1000, 800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void startGame() {

    }

    public void actionPerformed(ActionEvent event) {

    }
}
