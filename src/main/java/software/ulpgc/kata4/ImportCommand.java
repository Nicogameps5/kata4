package software.ulpgc.kata4;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ImportCommand implements Command{
    public ImportCommand(){

    }
    @Override
    public void execute() {
        try {
            File input = new File("D:/Descargas/title.basics.tsv");
            File output = new File("titles.db");
            doExecute(input, output);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void doExecute(File input, File output) throws Exception {
        try(TitleReader reader = createTitleReader(input);
            TitleWriter writer = createTitleWriter(output)) {
            while(true){
                Title title = reader.read();
                if(title==null) break;
                writer.write(title);
            }
        }
    }

    private static DatabaseTitleWriter createTitleWriter(File file) throws SQLException {
        return new DatabaseTitleWriter(deleteIfExist(file));
    }

    private static File deleteIfExist(File file) {
        if(file.exists()) file.delete();
        return file;
    }

    private static FileTitleReader createTitleReader(File file) throws IOException {
        return new FileTitleReader(file, new TsvTitleDeserializer());
    }
}
