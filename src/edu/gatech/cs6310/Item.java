package edu.gatech.cs6310;

public class Item {

    private final String name;
    private final float weight;

    public Item(final String name, final float weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public float getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.format("%s,%d", name, (int)weight);
    }
}
