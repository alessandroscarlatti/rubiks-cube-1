package io.github.alessandroscarlatti.model;

import java.util.EnumMap;
import java.util.Map;

public class Cube {
    public Map<FacePosition, CubeFace> cubeFaces = new EnumMap<>(FacePosition.class);
}
