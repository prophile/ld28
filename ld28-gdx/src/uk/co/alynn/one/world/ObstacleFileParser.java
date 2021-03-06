package uk.co.alynn.one.world;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class ObstacleFileParser {
    private static final String OF_REGEX = "^\\s*([1-9][0-9]*)\\s*@\\s*(\\d+(?:\\.\\d+)?)\\s:\\s*([AB])\\s*$";

    private static final Pattern _matcher = Pattern.compile(OF_REGEX);

    private final BufferedReader _reader;

    private final ObstacleGenerator _generator;

    public ObstacleFileParser(ObstacleGenerator generator, BufferedReader reader) {
        _reader = reader;
        _generator = generator;
    }

    private String readLine() throws IOException {
        return _reader.readLine();
    }

    private void handleLine(String line) {
        Matcher match = _matcher.matcher(line);
        if (match.matches()) {
            handleMatch(match);
        }
    }

    private boolean processLine() throws IOException {
        String line = readLine();
        if (line != null) {
            handleLine(line);
            return true;
        } else {
            return false;
        }
    }

    private void processAllLines() throws IOException {
        while (processLine()) {
            ;
        }
    }

    public void process() throws IOException {
        processAllLines();
    }

    private void handleMatch(Matcher match) {
        int value = Integer.parseInt(match.group(1));
        double pos = Double.parseDouble(match.group(2));
        Side side = interpretSide(match.group(3));
        System.out.println("Match " + value + " " + pos + " " + side);
        _generator.addObstacle(value, pos, side);
    }

    private static Side interpretSide(String sideMatch) {
        return sideMatch.equals("B") ? Side.SIDE_B : Side.SIDE_A;
    }
}
