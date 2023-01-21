package io.github.alessandroscarlatti.model;

public enum FacePosition {
    FRONT("f"),
    BACK("b"),
    LEFT("l"),
    RIGHT("r"),
    UP("u"),
    DOWN("d");

    private String code;

    FacePosition(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static FacePosition getPositionByCode(String code) {
        for (FacePosition value : values()) {
            if (value.code.toUpperCase().equals(code.toUpperCase())) {
                return value;
            }
        }

        return null;
    }
}
