import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumPlayersDialog extends JDialog implements ActionListener {
    public NumPlayersDialog() {
        this.setSize(250, 250);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.add(new JLabel("Choose number of players:"));
        for (int i = 3; i <= 5; i++) {
            JButton choice = new JButton(Integer.toString(i));
            choice.addActionListener(this);
            this.add(choice);
        }
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        this.setVisible(false);
        Main.launchGame(Integer.parseInt(((JButton) e.getSource()).getText()));
    }
}
