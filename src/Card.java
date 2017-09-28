public abstract class Card {
    private String name;
    private String image;

    public Card(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return this.name;
    }

    public String getImage() {
        return image;
    }
}
