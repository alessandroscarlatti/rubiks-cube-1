package io.github.alessandroscarlatti.model;

public enum Rotation {
    ROTATE_0(0),
    ROTATE_90(90),
    ROTATE_180(180),
    ROTATE_270(270),
    ;

    private int degrees;

    Rotation(int degrees) {
        this.degrees = degrees;
    }

    public static Rotation getByDegrees(int degrees) {
        for (Rotation value : values()) {
            if (value.degrees == degrees) {
                return value;
            }
        }

        return null;
    }

    public int getDegrees() {
        return degrees;
    }
}
