package software.ulpgc.kata4;

import java.io.IOException;

public interface TitleWriter extends AutoCloseable{
    void write(Title title) throws IOException;
}
