package io.github.alessandroscarlatti.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.StringJoiner;

public class Block {

    public final String id;  // describes original location, rotation, eg F:6=s/270 (front, 6th index, straight, 270deg rotation)
    public Map<FacePosition, BlockFace> faces = new EnumMap<>(FacePosition.class);

    public Block(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(id + ": ");

        StringJoiner sj = new StringJoiner(",", "[", "]");

        for (Map.Entry<FacePosition, BlockFace> entry : faces.entrySet()) {
            if (entry.getValue() != null) {
                sj.add(entry.getValue().toString());
            }
        }

        sb.append(sj);

        return sb.toString();
    }
}
