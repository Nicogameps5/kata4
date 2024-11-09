package software.ulpgc.kata4;

import java.io.IOException;

public interface TitleReader extends AutoCloseable{
    Title read() throws IOException;
}
