package io.github.alessandroscarlatti.action;

import io.github.alessandroscarlatti.model.Cube;
import io.github.alessandroscarlatti.model.Move;
import io.github.alessandroscarlatti.render.RenderUtils;
import io.github.alessandroscarlatti.serialization.CubeCsvMapper;
import io.github.alessandroscarlatti.serialization.MoveTextMapper;

public class ActionUtilsTest {

    public static class Test1 {
        public static void main(String[] args) {
            Cube cube = CubeCsvMapper.parseCube("""
                f:e/270,s/90,c/180,c/270,s/90,c/90,c/0,s/90,e/90
                b:e/270,s/90,c/180,c/270,s/90,c/90,c/0,s/90,e/90
                l:e/270,s/90,c/180,c/270,s/90,c/90,c/0,s/90,e/90
                r:e/270,s/90,c/180,c/270,s/90,c/90,c/0,s/90,e/90
                u:e/270,s/90,c/180,c/270,s/90,c/90,c/0,s/90,e/90
                d:e/270,s/90,c/180,c/270,s/90,c/90,c/0,s/90,e/90
                    """);

            RenderUtils.renderCube(cube);

            System.out.println("make a manual move here");

            Move move = MoveTextMapper.parseMove("f");

            ActionUtils.executeMove(cube, move);

            RenderUtils.renderCube(cube);

            // this should fail right now...due to hack move
            CubeValidator.validateCube(cube);

            System.out.println("done");
        }
    }
}
