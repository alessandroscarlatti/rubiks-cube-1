package io.github.alessandroscarlatti.model;

import java.util.EnumMap;
import java.util.Map;

public class Cube {
    public Map<FacePosition, CubeFace> cubeFaces = new EnumMap<>(FacePosition.class);

    public Block[][][] blocks = new Block[3][3][3];

    public Block getBlockFrom3d(int x, int y, int z) {
        return blocks[x][y][z];
    }

    public Block getBlockFrom2d(FacePosition facePosition, int x, int y) {
        int[] coord3d = getBlock3dCoord(facePosition, x, y);
        return blocks[coord3d[0]][coord3d[1]][coord3d[2]];
    }

    public Block[][] getCubeFaceBlocks(FacePosition facePosition) {
        Block[][] cubeFaceBlocks = new Block[3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                cubeFaceBlocks[x][y] = getBlockFrom2d(facePosition, x, y);
            }
        }

        return cubeFaceBlocks;
    }

    public static int[] getBlock3dCoord(FacePosition facePosition, int x, int y) {
        return switch (facePosition) {
            case FRONT -> new int[]{x, y, 0};
            case BACK -> new int[]{2 - x, y, 2};
            case LEFT -> new int[]{0, y, 2 - x};
            case RIGHT -> new int[]{2, y, x};
            case UP -> new int[]{y, 2, x};
            case DOWN -> new int[]{y, 0, x};
        };
    }

    public static int[] getBlock2dCoord(int x, int y, int z, FacePosition facePosition) {
        return switch (facePosition) {
            case FRONT -> new int[]{x, y};
            case BACK -> new int[]{x, y};
            case LEFT -> new int[]{z, y};
            case RIGHT -> new int[]{z, y};
            case UP -> new int[]{z, x};
            case DOWN -> new int[]{z, x};
        };
    }
}
