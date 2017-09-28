public class SuperTrumpCard extends Card {
    private int trumpType;
    String instructions;


    public SuperTrumpCard(String name, String image, int type, String instructions) {
        super(name, image);
        this.trumpType = type;
        this.instructions = instructions;

    }

    public int getTrumpType() {
        return trumpType;
    }


    public String getInstructions() {
        return instructions;
    }
}
