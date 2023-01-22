package io.github.alessandroscarlatti.model;

public enum Layer {
    FRONT("F", LayerType.FACE),
    BACK("B", LayerType.FACE),
    LEFT("L", LayerType.FACE),
    RIGHT("R", LayerType.FACE),
    UP("U", LayerType.FACE),
    DOWN("D", LayerType.FACE),
    MIDDLE("M", LayerType.MIDDLE),
    EQUATOR("E", LayerType.MIDDLE),
    SIDE("S", LayerType.MIDDLE),
    X("X", LayerType.CUBE),
    Y("Y", LayerType.CUBE),
    Z("Z", LayerType.CUBE);

    private final String code;
    private final LayerType type;

    Layer(String code, LayerType type) {
        this.code = code;
        this.type = type;
    }

    public static Layer getByCode(String code) {
        for (Layer value : values()) {
            if (value.code.toUpperCase().equals(code.toUpperCase())) {
                return value;
            }
        }

        return null;
    }

    public static Layer getSecondLayer(Layer layer) {
        return switch (layer) {
            case FRONT, BACK -> SIDE;
            case LEFT, RIGHT -> MIDDLE;
            case UP, DOWN -> EQUATOR;
            default -> throw new IllegalStateException("Unsupported value: " + layer);
        };
    }

    public static Layer[] getCubeLayers(Layer cubeLayer) {
        return switch (cubeLayer) {
            case X -> new Layer[]{LEFT, MIDDLE, RIGHT};
            case Y -> new Layer[]{DOWN, EQUATOR, UP};
            case Z -> new Layer[]{FRONT, SIDE, BACK};
            default ->
                throw new IllegalStateException("Unsupported value: " + cubeLayer);
        };
    }

    public String getCode() {
        return code;
    }

    public LayerType getType() {
        return type;
    }
}
