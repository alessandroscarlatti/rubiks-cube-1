package io.github.alessandroscarlatti.action;

import io.github.alessandroscarlatti.model.Cube;
import io.github.alessandroscarlatti.model.FacePosition;
import io.github.alessandroscarlatti.model.Layer;
import io.github.alessandroscarlatti.model.Move;

import java.util.List;

public class ActionUtils {

    public static void executeMoves(Cube cube, List<Move> moves) {
        for (Move move : moves) {
            executeMove(cube, move);
        }
    }

    public static void executeMove(Cube cube, Move move) {
        cube.cubeFaces.get(FacePosition.FRONT).blocks[0] = cube.cubeFaces.get(FacePosition.FRONT).blocks[1];

        // to actually execute a move...

        // move each affected layer, 1 at a time
        // will need to use a buffer, so that we don't lose the original objects
        // literally reassign/swap the block objects in each affected cube face

        for (Layer layer : move.layers) {
            // outer layer
            // U0 -> U2
            // U2 -> U8
            // U6 -> U0
            // U8 -> U6
            // U1 -> U5
            // U5 -> U7
            // U7 -> U3
            // U3 -> U1
            // rotateBlocksOnCubeFace(Rotation)

            // affected faces
            // F0 -> L0
            // F1 -> L1
            // F2 -> L2
            // rotateBlocksAroundCube(
        }
    }
}
