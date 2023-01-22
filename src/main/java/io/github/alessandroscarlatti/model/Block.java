package io.github.alessandroscarlatti.model;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.StringJoiner;

public class Block {

    public int[] coord3d;
    public final String id;  // describes original location, rotation, eg F:6=s/270 (front, 6th index, straight, 270deg rotation)
    public Map<FacePosition, BlockFace> faces = new EnumMap<>(FacePosition.class);

    public Block(int[] coord3d) {
        this.coord3d = coord3d;
        this.id = generateId(coord3d);
    }

    public Block(String id) {
        this.id = id;
    }

    public static String generateId(int[] coord3d) {
        return Arrays.toString(coord3d);
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

    int x() {
        return coord3d[0];
    }

    int y() {
        return coord3d[1];
    }

    int z() {
        return coord3d[2];
    }
}
