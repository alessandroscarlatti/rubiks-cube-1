package io.github.alessandroscarlatti.serialization;

import io.github.alessandroscarlatti.model.*;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class CubeCsvMapper {

    public static void main(String[] args) {
        String csvLinesIn = """
                f:e/270,s/90,c/90,c,s/90,c/180,c/270,s/90,e/90
                b:e/270,s/90,c/90,c,s/90,c/180,c/270,s/90,e/90
                l:e/270,s/90,c/90,c,s/90,c/180,c/270,s/90,e/90
                r:e/270,s/90,c/90,c,s/90,c/180,c/270,s/90,e/90
                u:e/270,s/90,c/90,c,s/90,c/180,c/270,s/90,e/90
                d:e/270,s/90,c/90,c,s/90,c/180,c/270,s/90,e/90
                """.stripIndent();

        Cube cube = parseCube(csvLinesIn);

        String csvLinesOut = getCubeCsv(cube);

        System.out.println(csvLinesOut);
    }

    public static Cube parseCube(String csvLines) {

        Cube cube = getBlankCube();


        for (String line : csvLines.split("\n")) {

            String lineRegex = "(.+?):(.+)";

            // eg, d:e/270,s/90,c/90,c,s/90,c/180,c/270,s/90,e/90
            FacePosition facePosition = FacePosition.getPositionByCode(StringUtils.trimToEmpty(line.replaceAll(lineRegex, "$1")));
            String csv = StringUtils.trimToEmpty(line.replaceAll(lineRegex, "$2"));

            // eg, e/270,s/90,c/90,c,s/90,c/180,c/270,s/90,e/90
            String[] csvTokens = csv.split(",");

            for (int i = 0; i < csvTokens.length; i++) {
                // eg, e/90
                String csvToken = csvTokens[i];
                String regex = "(.+?)/(.+)";

                String faceType = csvToken;
                Rotation rotation = Rotation.ROTATE_0;

                if (csvToken.matches(regex)) {
                    // the rotation was provided, so parse that
                    faceType = csvToken.replaceAll(regex, "$1").trim();
                    rotation = Rotation.getByDegrees(Integer.parseInt(csvToken.replaceAll(regex, "$2").trim()));
                }

                // populate the face (i)
                cube.cubeFaces.get(facePosition).blocks[i].faces.put(facePosition, new BlockFace(facePosition, faceType, rotation));
            }
        }

        return cube;
    }

    private static Cube getBlankCube() {

        class BlockFactory {
            final Map<String, Block> blockMap = new HashMap<>();

            Block getBlock(String id) {
                return blockMap.computeIfAbsent(id, id1 -> new Block(blockMap.size() + 1 + "/" + id1));
            }

            Block getBlock(int x, int y, int z) {
                // x, y, z in first quadrant
                // measured from left, bottom, front corner of cube
                // ...built this because I might possibly use it in the future...
                String id = String.join(",", String.valueOf(x), String.valueOf(y), String.valueOf(z));

                return blockMap.computeIfAbsent(id, id1 -> new Block(blockMap.size() + 1 + "/" + id1));
            }

            void validateBlockCount() {
                int expectedCount = 26;
                if (blockMap.size() != expectedCount) {
                    throw new IllegalStateException("block map expected " + expectedCount +", but was " + blockMap);
                }
            }
        }

        BlockFactory blockFactory = new BlockFactory();

        Cube cube = new Cube();

        cube.cubeFaces.put(FacePosition.FRONT, new CubeFace(FacePosition.FRONT));
        cube.cubeFaces.put(FacePosition.BACK, new CubeFace(FacePosition.BACK));
        cube.cubeFaces.put(FacePosition.LEFT, new CubeFace(FacePosition.LEFT));
        cube.cubeFaces.put(FacePosition.RIGHT, new CubeFace(FacePosition.RIGHT));
        cube.cubeFaces.put(FacePosition.UP, new CubeFace(FacePosition.UP));
        cube.cubeFaces.put(FacePosition.DOWN, new CubeFace(FacePosition.DOWN));

        // go U, M, D
        // U = 9
        // M = 8
        // D = 9
        int blockCount = 9 + 8 + 9;

        cube.cubeFaces.get(FacePosition.FRONT).blocks[0] = blockFactory.getBlock("F1/L1/U1");
        cube.cubeFaces.get(FacePosition.FRONT).blocks[1] = blockFactory.getBlock("F1/L0/U1");
        cube.cubeFaces.get(FacePosition.FRONT).blocks[2] = blockFactory.getBlock("F1/R1/U1");
        cube.cubeFaces.get(FacePosition.FRONT).blocks[3] = blockFactory.getBlock("F1/L1/U0");
        cube.cubeFaces.get(FacePosition.FRONT).blocks[4] = blockFactory.getBlock("F1/L0/U0");
        cube.cubeFaces.get(FacePosition.FRONT).blocks[5] = blockFactory.getBlock("F1/R1/U0");
        cube.cubeFaces.get(FacePosition.FRONT).blocks[6] = blockFactory.getBlock("F1/L1/D1");
        cube.cubeFaces.get(FacePosition.FRONT).blocks[7] = blockFactory.getBlock("F1/L0/D1");
        cube.cubeFaces.get(FacePosition.FRONT).blocks[8] = blockFactory.getBlock("F1/R1/D1");

        cube.cubeFaces.get(FacePosition.BACK).blocks[0] = blockFactory.getBlock("B1/R1/U1");
        cube.cubeFaces.get(FacePosition.BACK).blocks[1] = blockFactory.getBlock("B1/L0/U1");
        cube.cubeFaces.get(FacePosition.BACK).blocks[2] = blockFactory.getBlock("B1/L1/U1");
        cube.cubeFaces.get(FacePosition.BACK).blocks[3] = blockFactory.getBlock("B1/R1/U0");
        cube.cubeFaces.get(FacePosition.BACK).blocks[4] = blockFactory.getBlock("B1/L0/U0");
        cube.cubeFaces.get(FacePosition.BACK).blocks[5] = blockFactory.getBlock("B1/L1/U0");
        cube.cubeFaces.get(FacePosition.BACK).blocks[6] = blockFactory.getBlock("B1/R1/D1");
        cube.cubeFaces.get(FacePosition.BACK).blocks[7] = blockFactory.getBlock("B1/L0/D1");
        cube.cubeFaces.get(FacePosition.BACK).blocks[8] = blockFactory.getBlock("B1/L1/D1");

        cube.cubeFaces.get(FacePosition.LEFT).blocks[0] = blockFactory.getBlock("B1/L1/U1");
        cube.cubeFaces.get(FacePosition.LEFT).blocks[1] = blockFactory.getBlock("F0/L1/U1");
        cube.cubeFaces.get(FacePosition.LEFT).blocks[2] = blockFactory.getBlock("F1/L1/U1");
        cube.cubeFaces.get(FacePosition.LEFT).blocks[3] = blockFactory.getBlock("B1/L1/U0");
        cube.cubeFaces.get(FacePosition.LEFT).blocks[4] = blockFactory.getBlock("F0/L1/U0");
        cube.cubeFaces.get(FacePosition.LEFT).blocks[5] = blockFactory.getBlock("F1/L1/U0");
        cube.cubeFaces.get(FacePosition.LEFT).blocks[6] = blockFactory.getBlock("B1/L1/D1");
        cube.cubeFaces.get(FacePosition.LEFT).blocks[7] = blockFactory.getBlock("F0/L1/D1");
        cube.cubeFaces.get(FacePosition.LEFT).blocks[8] = blockFactory.getBlock("F1/L1/D1");

        cube.cubeFaces.get(FacePosition.RIGHT).blocks[0] = blockFactory.getBlock("F1/R1/U1");
        cube.cubeFaces.get(FacePosition.RIGHT).blocks[1] = blockFactory.getBlock("F0/R1/U1");
        cube.cubeFaces.get(FacePosition.RIGHT).blocks[2] = blockFactory.getBlock("B1/R1/U1");
        cube.cubeFaces.get(FacePosition.RIGHT).blocks[3] = blockFactory.getBlock("F1/R1/U0");
        cube.cubeFaces.get(FacePosition.RIGHT).blocks[4] = blockFactory.getBlock("F0/R1/U0");
        cube.cubeFaces.get(FacePosition.RIGHT).blocks[5] = blockFactory.getBlock("B1/R1/U0");
        cube.cubeFaces.get(FacePosition.RIGHT).blocks[6] = blockFactory.getBlock("F1/R1/D1");
        cube.cubeFaces.get(FacePosition.RIGHT).blocks[7] = blockFactory.getBlock("F0/R1/D1");
        cube.cubeFaces.get(FacePosition.RIGHT).blocks[8] = blockFactory.getBlock("B1/R1/D1");

        cube.cubeFaces.get(FacePosition.UP).blocks[0] = blockFactory.getBlock("F1/L1/U1");
        cube.cubeFaces.get(FacePosition.UP).blocks[1] = blockFactory.getBlock("F0/L1/U1");
        cube.cubeFaces.get(FacePosition.UP).blocks[2] = blockFactory.getBlock("B1/L1/U1");
        cube.cubeFaces.get(FacePosition.UP).blocks[3] = blockFactory.getBlock("F1/L0/U1");
        cube.cubeFaces.get(FacePosition.UP).blocks[4] = blockFactory.getBlock("F0/L0/U1");
        cube.cubeFaces.get(FacePosition.UP).blocks[5] = blockFactory.getBlock("B1/L0/U1");
        cube.cubeFaces.get(FacePosition.UP).blocks[6] = blockFactory.getBlock("F1/R1/U1");
        cube.cubeFaces.get(FacePosition.UP).blocks[7] = blockFactory.getBlock("F0/R1/U1");
        cube.cubeFaces.get(FacePosition.UP).blocks[8] = blockFactory.getBlock("B1/R1/U1");

        cube.cubeFaces.get(FacePosition.DOWN).blocks[0] = blockFactory.getBlock("F1/R1/D1");
        cube.cubeFaces.get(FacePosition.DOWN).blocks[1] = blockFactory.getBlock("F0/R1/D1");
        cube.cubeFaces.get(FacePosition.DOWN).blocks[2] = blockFactory.getBlock("B1/R1/D1");
        cube.cubeFaces.get(FacePosition.DOWN).blocks[3] = blockFactory.getBlock("F1/L0/D1");
        cube.cubeFaces.get(FacePosition.DOWN).blocks[4] = blockFactory.getBlock("F0/L0/D1");
        cube.cubeFaces.get(FacePosition.DOWN).blocks[5] = blockFactory.getBlock("B1/L0/D1");
        cube.cubeFaces.get(FacePosition.DOWN).blocks[6] = blockFactory.getBlock("F1/L1/D1");
        cube.cubeFaces.get(FacePosition.DOWN).blocks[7] = blockFactory.getBlock("F0/L1/D1");
        cube.cubeFaces.get(FacePosition.DOWN).blocks[8] = blockFactory.getBlock("B1/L1/D1");

        blockFactory.validateBlockCount();

        return cube;
    }

    public static String getCubeCsv(Cube cube) {

        StringJoiner lines = new StringJoiner("\n");

        for (Map.Entry<FacePosition, CubeFace> cubeFaceEntry : cube.cubeFaces.entrySet()) {
            StringJoiner csv = new StringJoiner(",", cubeFaceEntry.getKey().getCode() + ":", "");

            for (BlockFace blockFace : cubeFaceEntry.getValue().getBlockFaces()) {
                csv.add(blockFace.faceType + "/" + blockFace.rotation.getDegrees());
            }

            lines.add(csv.toString());
        }

        return lines.toString();
    }


}
