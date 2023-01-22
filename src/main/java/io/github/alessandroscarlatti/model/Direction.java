package io.github.alessandroscarlatti.model;

public enum Direction {

    CLOCKWISE("CW"),
    COUNTER_CLOCKWISE("CCW");

    private final String code;

    Direction(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
