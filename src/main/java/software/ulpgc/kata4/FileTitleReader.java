package software.ulpgc.kata4;

import java.io.*;

public class FileTitleReader implements TitleReader, AutoCloseable{
    private final TitleDeserializer deserializer;
    private final BufferedReader reader;

    public FileTitleReader(File file, TitleDeserializer deserializer) throws IOException{
        this.deserializer = deserializer;
        this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        this.reader.readLine();
    }
    @Override
    public Title read() throws IOException {
        return deserialize(reader.readLine());
    }

    private Title deserialize(String line) {
        return line != null ?
                deserializer.deserialize(line):
                null;
    }

    @Override
    public void close() throws Exception {
        this.reader.close();
    }
}
