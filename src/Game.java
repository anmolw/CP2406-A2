import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Arrays;


public class Game {
    private static final String filename = "card.txt";
    private Card lastCard;
    private Player[] players;
    private int currentPlayerID;
    private int numPlayers;
    private int category = -1;
    private ArrayList<Card> pack;
    private boolean categoryChoice = true;
    int lastTrumpPlayerID;

    private static HashMap<String, Integer> cleavageMap = new HashMap<>();
    private static HashMap<String, Integer> ecoMap = new HashMap<>();
    private static HashMap<String, Integer> crustalMap = new HashMap<>();
    private static final String[] categories = {"Hardness", "Specific gravity", "Cleavage", "Crustal abundance", "Economic value"};

    public Game(int numPlayers) {
        try {
            pack = readCards(filename);
        } catch (FileNotFoundException e) {
            System.err.println("Error: Card list file not found.");
            return;
        }

        this.numPlayers = numPlayers;
        players = new Player[numPlayers];

        pack = addSuperTrumps(pack);
        Collections.shuffle(pack);

        for(int i = 0; i < numPlayers; i++) {
            players[i] = new Player(dealHand(pack));
        }

        currentPlayerID = 0;
        // int numPlayers = getNumberInput(3, 5, "Enter number of players(3-5): ");
        // playGame(pack, numPlayers);
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Player getCurrentPlayer(){
        return players[currentPlayerID];
    }

    public int getCurrentPlayerID() {
        return currentPlayerID;
    }

    public String[] getCategories() {
        return categories;
    }

    public String getCurrentCategory() {
        if (category == -1) {
            return "None";
        }
        return categories[category];
    }

    public void passTurn() {
        players[currentPlayerID].setPassed(true);
        players[currentPlayerID].hand.add(pack.get(0));
        pack.remove(0);
        advanceTurn();
    }

    private void advanceTurn() {
        int curr = currentPlayerID;
        boolean stop = false;
        while(!stop && !gameOver()) {
            if (curr == numPlayers - 1) {
                curr = 0;
            }
            else {
                curr++;
            }
            if (!players[curr].hasPassed() && players[curr].hand.size() >= 1) {
                currentPlayerID = curr;
                stop = true;
            }
        }
        if(roundOver()) {
            newRound();
        }
    }

    private void newRound() {
        lastCard = null;
        category = -1;
        categoryChoice = true;
        for (int i = 0; i < players.length; i++) {
            players[i].setPassed(false);
        }
    }

    public String displayLastCard(MineralCard card, int category) {
        String output = "Last card played: " + card.getName() + " " + categories[category] + ": ";
        if (category == 0)
            output = output + card.getHardness();
        else if (category == 1)
            output = output + card.getGravity();
        else if (category == 2)
            output = output + card.getCleavage();
        else if (category == 3)
            output = output + card.getCrustalAbundance();
        else if (category == 4)
            output = output + card.getEcoValue();
        return (output);

    }

    public Card getCardWithName(String name, ArrayList<Card> list) {
        for (Card card : list) {
            if (card.getName().equals(name))
                return card;
        }
        return null;
    }

    private boolean gameOver() {
        int notEmpty = 0;
        for (Player player : players) {
            if (player.hand.size() > 0)
                notEmpty++;
        }
        return (notEmpty <= 1);
    }

    private boolean roundOver() {
        int numPassed = 0;
        for (Player player : players) {
            if (player.hasPassed()) {
                numPassed++;
            }
        }
        return (numPassed >= players.length - 1);
    }

    private static ArrayList<Card> readCards(String filename) throws FileNotFoundException {
        Scanner file;
        file = new Scanner(new FileInputStream(filename));

        ArrayList<Card> pack = new ArrayList<>();

        boolean passedHeader = false;
        int count = 0;
        while (file.hasNextLine()) {
            String line = file.nextLine();
            if (passedHeader) {
                // Split the line
                String[] line_split = line.split(",");
                String imageLoc = String.format("images/Slide%02d.jpg", ++count);
                // Create a MineralCard object from each line of the card file excluding the header
                Card card = new MineralCard(line_split[0], imageLoc, Double.parseDouble(line_split[1]), Double.parseDouble(line_split[2]), line_split[3], line_split[4], line_split[5]);
                pack.add(card);
            }
            passedHeader = true;
        }

        System.out.println("Loaded cards successfully.");
        return pack;
    }

    private static ArrayList<Card> addSuperTrumps(ArrayList<Card> pack) {
        Card[] superTrumpList = {
                new SuperTrumpCard("The Mineralogist", "images/Slide58.jpg", 2, "changes the trumps category to Cleavage"),
                new SuperTrumpCard("The Geologist", "images/Slide60.jpg", 5, "change to trumps category of your choice"),
                new SuperTrumpCard("The Geophysicist", "images/Slide59.jpg", 1, "changes the trumps category to Specific Gravity"),
                new SuperTrumpCard("The Petrologist", "images/Slide56.jpg", 3, "changes the trumps category to Crustal Abundance"),
                new SuperTrumpCard("The Miner", "images/Slide55.jpg", 4, "changes the trumps category to Economic Value"),
                new SuperTrumpCard("The Gemmologist", "images/Slide57.jpg", 0, "changes the trumps category to Hardness")};

        pack.addAll(Arrays.asList(superTrumpList));
        return pack;
    }

    public void playTurn(Card card) {
        if (card instanceof SuperTrumpCard) {
            int trumpType = ((SuperTrumpCard) card).getTrumpType();
            lastCard = card;
            getCurrentPlayer().hand.remove(card);
            if (trumpType != 5) {
                category = trumpType;
            }
            else {
                if (getCurrentPlayer().hand.size() > 1)
                    categoryChoice = true;
                else
                    advanceTurn();
            }
            for (int i = 0; i < numPlayers; i++) {
                players[i].setPassed(false);
            }
        }
        else if(isValidMove(card)) {
            lastCard = card;
            players[currentPlayerID].hand.remove(card);
            advanceTurn();
        }
    }

    public boolean hasCategoryChoice() {
        return categoryChoice;
    }

    public boolean isValidMove(Card card) {
        if (category == -1) {
            return true;
        }
        if (lastCard == null) {
            return true;
        }
        else if (lastCard instanceof SuperTrumpCard) {
            return true;
        }
        if(lastCard instanceof MineralCard && !(card instanceof SuperTrumpCard)) {
            return (isGreater(category, (MineralCard) card, (MineralCard) lastCard));
        }
        else if(card instanceof SuperTrumpCard) {
            return true;
        }
        return false;
    }

    private static boolean isGreater(int category, MineralCard card1, MineralCard card2) {

        if (cleavageMap.isEmpty()) {
            cleavageMap.put("none", 0);
            cleavageMap.put("poor/none", 1);
            cleavageMap.put("1 poor", 2);
            cleavageMap.put("2 poor", 3);
            cleavageMap.put("1 good", 4);
            cleavageMap.put("1 good/1 poor", 5);
            cleavageMap.put("2 good", 6);
            cleavageMap.put("3 good", 7);
            cleavageMap.put("1 perfect", 8);
            cleavageMap.put("1 perfect/1 good", 9);
            cleavageMap.put("1 perfect/2 good", 10);
            cleavageMap.put("2 perfect/1 good", 11);
            cleavageMap.put("3 perfect", 12);
            cleavageMap.put("4 perfect", 13);
            cleavageMap.put("6 perfect", 14);
        }

        if (crustalMap.isEmpty()) {
            crustalMap.put("ultratrace", 0);
            crustalMap.put("trace", 1);
            crustalMap.put("low", 2);
            crustalMap.put("moderate", 3);
            crustalMap.put("high", 4);
            crustalMap.put("very high", 5);
        }

        if (ecoMap.isEmpty()) {
            ecoMap.put("trivial", 0);
            ecoMap.put("low", 1);
            ecoMap.put("moderate", 2);
            ecoMap.put("high", 3);
            ecoMap.put("high", 4);
            ecoMap.put("very high", 5);
            ecoMap.put("I'm rich!", 6);
        }


        if (category == 0) {
            return (card1.getHardness() > card2.getHardness());
        } else if (category == 1) {
            return (card1.getGravity() > card2.getGravity());
        } else if (category == 2) {
            return (cleavageMap.get(card1.getCleavage()) > cleavageMap.get((card2.getCleavage())));
        } else if (category == 3) {
            return (crustalMap.get(card1.getCrustalAbundance()) > cleavageMap.get((card2.getCrustalAbundance())));
        } else if (category == 4) {
            return (ecoMap.get(card1.getEcoValue()) > ecoMap.get(card2.getEcoValue()));
        }
        return false;
    }


    private static ArrayList<Card> dealHand(ArrayList<Card> pack) {
        ArrayList<Card> hand = new ArrayList(pack.subList(0, 8));
        pack.subList(0, 8).clear();
        return hand;
        // pack.removeAll(hand);
    }
}
