package io.github.alessandroscarlatti.model;

public class CubeFace {
    public FacePosition facePosition;
    public Block[] blocks = new Block[9];

    public CubeFace() {
    }

    public CubeFace(FacePosition facePosition) {
        this.facePosition = facePosition;
    }

    public BlockFace[] getBlockFaces() {
        // get all the block faces that match this face
        // eg, we are wanting the block faces that are on the front cube face
        // so we just find all the blocks in the front face,
        // and then get all the faces that are on the front face.

        BlockFace[] blockFaces = new BlockFace[blocks.length];
        for (int i = 0, blocksLength = blocks.length; i < blocksLength; i++) {
            Block block = blocks[i];

            blockFaces[i] = block.faces.get(facePosition);
        }

        return blockFaces;
    }

    public BlockFace getBlockFace(int index) {
        return blocks[index].faces.get(facePosition);
    }
}
