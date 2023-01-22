package io.github.alessandroscarlatti.model;

public class Move {
    public Layer[] layers;
    public Direction direction;
    public int rotations;
    public Rotation rotation;
    public final String text;

    public Move(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Move{" +
                "layers=" + (layers != null ? layers.length : 0 ) +
                ", direction=" + (direction != null ? direction.getCode() : null) +
                ", rotations=" + rotations +
                ", rotation=" + (rotation != null ? rotation.getDegrees() : null) +
                ", text=" + text +
                '}';
    }
}
