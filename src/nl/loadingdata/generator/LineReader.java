package nl.loadingdata.generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LineReader extends Generator<String> {
    private BufferedReader reader;

    public LineReader(InputStream is) {
        reader = new BufferedReader(new InputStreamReader(is));
    }

    public static LineReader read(InputStream is) {
        return new LineReader(is);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                yield(line);
            }
            reader.close();
        } catch (IOException e) {
            pending = e;
        }
    }

}
