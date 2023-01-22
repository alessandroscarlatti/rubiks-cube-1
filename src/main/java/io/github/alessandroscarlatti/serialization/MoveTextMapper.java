package io.github.alessandroscarlatti.serialization;

import io.github.alessandroscarlatti.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class MoveTextMapper {

    public static void main(String[] args) {
        String textIn = """
                UU'U2
                RR'R2
                FF'F2
                DD'D2
                LL'L2
                BB'B2
                uu'
                rr'
                ff'
                dd'
                ll'
                bb'
                MM'
                EE'
                SS'
                xx'
                yy'
                zz'
                """.stripIndent();

        List<Move> moves = parseMoves(textIn);

        String textOut = getMovesText(moves);

        System.out.println(textOut);

        List<Sequence> sequences = parseSequences(textIn);

        String sequencesOut = getSequencesText(sequences, "\n");

        System.out.println(sequencesOut);
    }

    public static List<Sequence> parseSequences(String text) {
        String[] sequencesText = text.split("[\n,\\s+]");

        List<Sequence> sequences = new ArrayList<>();

        for (String sequenceText : sequencesText) {
            Sequence sequence = new Sequence();
            sequence.text = sequenceText;
            sequence.moves = parseMoves(sequenceText);

            sequences.add(sequence);
        }

        return sequences;
    }

    public static List<Move> parseMoves(String text) {
        String[] lines = text.split("[\n,\\s+]");

        List<Move> moves = new ArrayList<>();

        for (String line : lines) {
            String regex = "(?=[A-Za-z]\\d?'?)";
            String[] moveTokens = line.split(regex);

            for (String moveToken : moveTokens) {
                Move move = parseMove(moveToken);

                moves.add(move);
            }
        }

        return moves;
    }

    public static Move parseMove(String text) {
        Move move = new Move(text);

        Layer layer = Layer.getByCode(text.replaceAll("[^A-Za-z]", ""));

        Objects.requireNonNull(layer, () -> "Error parsing move text: " + text);

        switch (layer.getType()) {
            case FACE -> {

                if (isMultipleLayerTurn(text)) {
                    move.layers = new Layer[2];
                    move.layers[0] = layer;
                    move.layers[1] = Layer.getSecondLayer(layer);
                } else {
                    move.layers = new Layer[1];
                    move.layers[0] = layer;
                }
            }
            case MIDDLE -> {
                move.layers = new Layer[1];
                move.layers[0] = layer;
            }
            case CUBE -> {
                move.layers = Layer.getCubeLayers(layer);
            }
        }

        move.direction = parseDirection(text);
        move.rotations = parseRotationCount(text);
        move.rotation = getRotation(move.rotations, move.direction);

        return move;
    }

    private static Direction parseDirection(String text) {
        if (text.endsWith("'")) {
            return Direction.COUNTER_CLOCKWISE;
        } else {
            return Direction.CLOCKWISE;
        }
    }

    private static boolean isMultipleLayerTurn(String text) {
        if (text.matches("[a-z]\\d?'?")) {
            return true;
        }
        if (text.matches("[A-Z]\\d?'?")) {
            return false;
        }
        throw new IllegalStateException("Error evaluating if double turn: " + text);
    }

    private static int parseRotationCount(String text) {

        String regex = "[A-Za-z](\\d)'?";

        if (text.matches(regex)) {
            // there is a specific number
            return Integer.parseInt(text.replaceAll(regex, "$1"));
        } else {
            // no specific number so assume 1
            return 1;
        }
    }

    private static Rotation getRotation(int rotationCount, Direction direction) {
        return switch (direction) {
            case CLOCKWISE -> switch (rotationCount) {
                case 0 -> Rotation.ROTATE_0;
                case 1 -> Rotation.ROTATE_90;
                case 2 -> Rotation.ROTATE_180;
                case 3 -> Rotation.ROTATE_270;
                default -> throw new IllegalStateException("Unsupported value: " + rotationCount);
            };
            case COUNTER_CLOCKWISE -> switch (rotationCount) {
                case 0 -> Rotation.ROTATE_0;
                case 1 -> Rotation.ROTATE_270;
                case 2 -> Rotation.ROTATE_180;
                case 3 -> Rotation.ROTATE_90;
                default -> throw new IllegalStateException("Unsupported value: " + rotationCount);
            };
        };
    }

    public static String getMovesText(List<Move> moves) {
        StringJoiner sj = new StringJoiner("");

        for (Move move : moves) {
            sj.add(move.text);
        }

        return sj.toString();
    }

    public static String getSequencesText(List<Sequence> sequences, String delimiter) {
        StringJoiner sj = new StringJoiner(delimiter);

        for (Sequence sequence : sequences) {
            sj.add(getMovesText(sequence.moves));
        }

        return sj.toString();
    }
}
