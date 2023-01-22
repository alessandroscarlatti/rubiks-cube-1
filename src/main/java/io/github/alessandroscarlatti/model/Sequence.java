package io.github.alessandroscarlatti.model;

import java.util.ArrayList;
import java.util.List;

public class Sequence {

    public List<Move> moves = new ArrayList<>();
    public String text;

    @Override
    public String toString() {
        return "Sequence{" +
                "moves=" + moves.size() +
                ", text='" + text + '\'' +
                '}';
    }
}
