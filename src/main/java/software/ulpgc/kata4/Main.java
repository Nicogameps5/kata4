package software.ulpgc.kata4;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        ImportCommand command = new ImportCommand();
        command.execute();

    }
}