package io.github.alessandroscarlatti.action;

import io.github.alessandroscarlatti.model.Block;
import io.github.alessandroscarlatti.model.Cube;
import io.github.alessandroscarlatti.model.CubeFace;

import java.util.HashSet;
import java.util.Set;

public class CubeValidator {

    public static void validateCube(Cube cube) {
        // make sure there are NO duplicate blocks

        Set<String> blockIds = new HashSet<>();

        for (CubeFace cubeFace : cube.cubeFaces.values()) {
            for (Block block : cubeFace.blocks) {
                if (blockIds.contains(block.id)) {
                    throw new IllegalStateException("Duplicate block id found: " + block.id + " on face " + cubeFace);
                } else {
                    blockIds.add(block.id);
                }
            }
        }
    }
}
