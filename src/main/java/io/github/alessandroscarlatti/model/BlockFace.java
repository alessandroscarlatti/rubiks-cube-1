package io.github.alessandroscarlatti.model;

public class BlockFace {

    public FacePosition facePosition;
    public final String faceType;
    public Rotation rotation;

    public BlockFace(FacePosition facePosition, String faceType, Rotation rotation) {
        this.facePosition = facePosition;
        this.rotation = rotation;
        this.faceType = faceType;
    }

    @Override
    public String toString() {
        return facePosition.getCode() + ":" + faceType + "/" + (rotation != null ? rotation.getDegrees() : null);
    }
}
