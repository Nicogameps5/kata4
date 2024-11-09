package software.ulpgc.kata4;

public class TsvTitleDeserializer implements TitleDeserializer {
    @Override
    public Title deserialize(String line) {
        return deserialize(line.split("\t"));
    }

    private Title deserialize(String[] fields) {
        return new Title(fields[0], fields[3], toInt(fields[5]), toInt(fields[7]));
    }

    private int toInt(String field) {
        try {
            return Integer.parseInt(field);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
