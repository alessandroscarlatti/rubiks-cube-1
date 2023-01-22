package io.github.alessandroscarlatti.action;

import io.github.alessandroscarlatti.model.Cube;
import io.github.alessandroscarlatti.model.FacePosition;
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
    }
}
