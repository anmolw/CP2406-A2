import java.util.ArrayList;

public class Player {
    public ArrayList<Card> hand;
    private boolean passed;

    public Player(ArrayList<Card> hand) {
        this.hand = hand;
        this.passed = false;
    }

    public boolean hasFinished() {
        return (hand.size() == 0);
    }

    public void setPassed(boolean p) {
        this.passed = p;
    }

    public boolean hasPassed() {
        return this.passed;
    }
}
