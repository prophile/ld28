package uk.co.alynn.one;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

final class ConstantsLoader {
    private final BufferedReader _rdr;
    private final Map<String, String> _mapping;

    public ConstantsLoader(Reader rdr) {
        _rdr = new BufferedReader(rdr);
        _mapping = new HashMap<String, String>();
    }

    private String readLine() throws IOException {
        String result = _rdr.readLine();
        if (result == null) {
            throw new IOException("EOF reached");
        }
        return result;
    }

    private String stripComments(String str) {
        int index = str.indexOf("#");
        if (index != -1) {
            return str.substring(0, index);
        } else {
            return str;
        }
    }

    private String readStrippedLine() throws IOException {
        return stripComments(readLine());
    }

    private void loadLine() throws IOException {
        String nextLine = readStrippedLine();
        String[] components = nextLine.split("=", 2);
        if (components.length == 2) {
            _mapping.put(components[0], components[1]);
        }
    }

    private void loadAllLines() {
        try {
            while (true) {
                loadLine();
            }
        } catch (IOException except) {
            return;
        }
    }

    public Constants load() {
        loadAllLines();
        return new Constants(_mapping);
    }
}
