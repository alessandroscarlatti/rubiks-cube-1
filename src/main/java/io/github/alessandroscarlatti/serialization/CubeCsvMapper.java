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

        String csvLinesOut = getCubeCsv2(cube);

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

                int[] coord2d = getCoord2dFromIndex(i);

                cube.getBlockFrom2d(facePosition, coord2d[0], coord2d[1]).faces.put(facePosition, new BlockFace(facePosition, faceType, rotation));
            }
        }

        return cube;
    }

    private static int[] getCoord2dFromIndex(int index) {
        return new int[]{index / 3, index % 3};
    }

    private static Cube getBlankCube() {

        class BlockFactory {
            final Map<String, Block> blockMap = new HashMap<>();

            Block getBlock(String id) {
                return blockMap.computeIfAbsent(id, id1 -> new Block(blockMap.size() + 1 + "/" + id1));
            }

            Block getBlock(int[] coord3d) {
                // x, y, z in first quadrant
                // measured from left, bottom, front corner of cube
                // ...built this because I might possibly use it in the future...

                return blockMap.computeIfAbsent(Block.generateId(coord3d), id1 -> new Block(coord3d));
            }

            void validateBlockCount() {
                int expectedCount = 26;
                if (blockMap.size() != expectedCount) {
                    throw new IllegalStateException("block map expected " + expectedCount +", but was " + blockMap.size());
                }
            }
        }

        BlockFactory blockFactory1 = new BlockFactory();
        BlockFactory blockFactory2 = new BlockFactory();

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

        cube.cubeFaces.get(FacePosition.FRONT).blocks[0] = blockFactory1.getBlock("F1/L1/U1");
        cube.cubeFaces.get(FacePosition.FRONT).blocks[1] = blockFactory1.getBlock("F1/L0/U1");
        cube.cubeFaces.get(FacePosition.FRONT).blocks[2] = blockFactory1.getBlock("F1/R1/U1");
        cube.cubeFaces.get(FacePosition.FRONT).blocks[3] = blockFactory1.getBlock("F1/L1/U0");
        cube.cubeFaces.get(FacePosition.FRONT).blocks[4] = blockFactory1.getBlock("F1/L0/U0");
        cube.cubeFaces.get(FacePosition.FRONT).blocks[5] = blockFactory1.getBlock("F1/R1/U0");
        cube.cubeFaces.get(FacePosition.FRONT).blocks[6] = blockFactory1.getBlock("F1/L1/D1");
        cube.cubeFaces.get(FacePosition.FRONT).blocks[7] = blockFactory1.getBlock("F1/L0/D1");
        cube.cubeFaces.get(FacePosition.FRONT).blocks[8] = blockFactory1.getBlock("F1/R1/D1");

        cube.cubeFaces.get(FacePosition.BACK).blocks[0] = blockFactory1.getBlock("B1/R1/U1");
        cube.cubeFaces.get(FacePosition.BACK).blocks[1] = blockFactory1.getBlock("B1/L0/U1");
        cube.cubeFaces.get(FacePosition.BACK).blocks[2] = blockFactory1.getBlock("B1/L1/U1");
        cube.cubeFaces.get(FacePosition.BACK).blocks[3] = blockFactory1.getBlock("B1/R1/U0");
        cube.cubeFaces.get(FacePosition.BACK).blocks[4] = blockFactory1.getBlock("B1/L0/U0");
        cube.cubeFaces.get(FacePosition.BACK).blocks[5] = blockFactory1.getBlock("B1/L1/U0");
        cube.cubeFaces.get(FacePosition.BACK).blocks[6] = blockFactory1.getBlock("B1/R1/D1");
        cube.cubeFaces.get(FacePosition.BACK).blocks[7] = blockFactory1.getBlock("B1/L0/D1");
        cube.cubeFaces.get(FacePosition.BACK).blocks[8] = blockFactory1.getBlock("B1/L1/D1");

        cube.cubeFaces.get(FacePosition.LEFT).blocks[0] = blockFactory1.getBlock("B1/L1/U1");
        cube.cubeFaces.get(FacePosition.LEFT).blocks[1] = blockFactory1.getBlock("F0/L1/U1");
        cube.cubeFaces.get(FacePosition.LEFT).blocks[2] = blockFactory1.getBlock("F1/L1/U1");
        cube.cubeFaces.get(FacePosition.LEFT).blocks[3] = blockFactory1.getBlock("B1/L1/U0");
        cube.cubeFaces.get(FacePosition.LEFT).blocks[4] = blockFactory1.getBlock("F0/L1/U0");
        cube.cubeFaces.get(FacePosition.LEFT).blocks[5] = blockFactory1.getBlock("F1/L1/U0");
        cube.cubeFaces.get(FacePosition.LEFT).blocks[6] = blockFactory1.getBlock("B1/L1/D1");
        cube.cubeFaces.get(FacePosition.LEFT).blocks[7] = blockFactory1.getBlock("F0/L1/D1");
        cube.cubeFaces.get(FacePosition.LEFT).blocks[8] = blockFactory1.getBlock("F1/L1/D1");

        cube.cubeFaces.get(FacePosition.RIGHT).blocks[0] = blockFactory1.getBlock("F1/R1/U1");
        cube.cubeFaces.get(FacePosition.RIGHT).blocks[1] = blockFactory1.getBlock("F0/R1/U1");
        cube.cubeFaces.get(FacePosition.RIGHT).blocks[2] = blockFactory1.getBlock("B1/R1/U1");
        cube.cubeFaces.get(FacePosition.RIGHT).blocks[3] = blockFactory1.getBlock("F1/R1/U0");
        cube.cubeFaces.get(FacePosition.RIGHT).blocks[4] = blockFactory1.getBlock("F0/R1/U0");
        cube.cubeFaces.get(FacePosition.RIGHT).blocks[5] = blockFactory1.getBlock("B1/R1/U0");
        cube.cubeFaces.get(FacePosition.RIGHT).blocks[6] = blockFactory1.getBlock("F1/R1/D1");
        cube.cubeFaces.get(FacePosition.RIGHT).blocks[7] = blockFactory1.getBlock("F0/R1/D1");
        cube.cubeFaces.get(FacePosition.RIGHT).blocks[8] = blockFactory1.getBlock("B1/R1/D1");

        cube.cubeFaces.get(FacePosition.UP).blocks[0] = blockFactory1.getBlock("F1/L1/U1");
        cube.cubeFaces.get(FacePosition.UP).blocks[1] = blockFactory1.getBlock("F0/L1/U1");
        cube.cubeFaces.get(FacePosition.UP).blocks[2] = blockFactory1.getBlock("B1/L1/U1");
        cube.cubeFaces.get(FacePosition.UP).blocks[3] = blockFactory1.getBlock("F1/L0/U1");
        cube.cubeFaces.get(FacePosition.UP).blocks[4] = blockFactory1.getBlock("F0/L0/U1");
        cube.cubeFaces.get(FacePosition.UP).blocks[5] = blockFactory1.getBlock("B1/L0/U1");
        cube.cubeFaces.get(FacePosition.UP).blocks[6] = blockFactory1.getBlock("F1/R1/U1");
        cube.cubeFaces.get(FacePosition.UP).blocks[7] = blockFactory1.getBlock("F0/R1/U1");
        cube.cubeFaces.get(FacePosition.UP).blocks[8] = blockFactory1.getBlock("B1/R1/U1");

        cube.cubeFaces.get(FacePosition.DOWN).blocks[0] = blockFactory1.getBlock("F1/R1/D1");
        cube.cubeFaces.get(FacePosition.DOWN).blocks[1] = blockFactory1.getBlock("F0/R1/D1");
        cube.cubeFaces.get(FacePosition.DOWN).blocks[2] = blockFactory1.getBlock("B1/R1/D1");
        cube.cubeFaces.get(FacePosition.DOWN).blocks[3] = blockFactory1.getBlock("F1/L0/D1");
        cube.cubeFaces.get(FacePosition.DOWN).blocks[4] = blockFactory1.getBlock("F0/L0/D1");
        cube.cubeFaces.get(FacePosition.DOWN).blocks[5] = blockFactory1.getBlock("B1/L0/D1");
        cube.cubeFaces.get(FacePosition.DOWN).blocks[6] = blockFactory1.getBlock("F1/L1/D1");
        cube.cubeFaces.get(FacePosition.DOWN).blocks[7] = blockFactory1.getBlock("F0/L1/D1");
        cube.cubeFaces.get(FacePosition.DOWN).blocks[8] = blockFactory1.getBlock("B1/L1/D1");


        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    if (x == 1 && y == 1 && z == 1) {
                        // skip the center
                        continue;
                    }

                    cube.blocks[x][y][z] = blockFactory2.getBlock(new int[]{x, y, z});
                }
            }
        }

        blockFactory1.validateBlockCount();
        blockFactory2.validateBlockCount();

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

    public static String getCubeCsv2(Cube cube) {

        StringJoiner lines = new StringJoiner("\n");

        for (Map.Entry<FacePosition, CubeFace> cubeFaceEntry : cube.cubeFaces.entrySet()) {
            StringJoiner csv = new StringJoiner(",", cubeFaceEntry.getKey().getCode() + ":", "");

            FacePosition facePosition = cubeFaceEntry.getKey();
            Block[][] blocks = cube.getCubeFaceBlocks(facePosition);

            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    BlockFace blockFace = blocks[x][y].faces.get(facePosition);
                    csv.add(blockFace.faceType + "/" + blockFace.rotation.getDegrees());
                }
            }

            lines.add(csv.toString());
        }

        return lines.toString();
    }


}
