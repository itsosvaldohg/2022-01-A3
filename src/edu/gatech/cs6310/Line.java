package edu.gatech.cs6310;

public class Line {

    private final Item item;
    private final int quantity;
    private final float weight;
    private final float cost;

    public Line(final Item item, final int quantity, final float weight, final float cost) {
        this.item = item;
        this.quantity = quantity;
        this.weight = weight;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return String.format("item_name:%s,total_quantity:%d,total_cost:%d,total_weight:%d",
                item.getName(), quantity, (int)cost, (int)weight);
    }
}
