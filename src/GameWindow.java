import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame implements ActionListener {

    JPanel playerInfoPanel;
    JPanel topPanel;

    public GameWindow() {
        super();
        this.setTitle("Mineral Supertrumps");
        this.setSize(1280, 800);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        topPanel = new JPanel();
        this.setVisible(true);
    }

    public void updateTopPanel(Game game) {

    }


    public void actionPerformed(ActionEvent event) {

    }
}
