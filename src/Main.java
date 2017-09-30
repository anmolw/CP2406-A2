public class Main {
    public static void main(String[] args) {
        NumPlayersDialog prompt = new NumPlayersDialog();
    }

    public static void launchGame(int numPlayers) {
        Game game = new Game(numPlayers);
        GameWindow ui = new GameWindow(game);
    }
}
