package software.ulpgc.kata4;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class DatabaseTitleWriter implements TitleWriter, AutoCloseable{
    private final Connection connection;
    private final PreparedStatement insertStatement;
    private final static String CreateTableStatement = """
            CREATE TABLE IF NOT EXISTS movies (
                id TEXT PRIMARY KEY,
                title TEXT NOT NULL,
                year INTEGER,
                duration INTEGER
            )
            """;
    private final static String InsertRecordStatement = """
            INSERT INTO movies (id,title,year,duration)
            VALUES (?,?,?,?)
            """;

    public DatabaseTitleWriter(File file) throws SQLException{
        this(connectionFor(file));
    }

    public DatabaseTitleWriter(String connection) throws SQLException{
        this.connection = DriverManager.getConnection(connection);
        this.connection.setAutoCommit(false);
        this.insertStatement = initDatabase(this.connection);
    }

    private PreparedStatement initDatabase(Connection connection) throws SQLException{
        Statement statement = connection.createStatement();
        statement.execute(CreateTableStatement);
        return connection.prepareStatement(InsertRecordStatement);
    }

    private static String connectionFor(File file) {
        return "jdbc:sqlite:" + file.getAbsolutePath();
    }

    @Override
    public void write(Title title) throws IOException {
        try{
            updateInsertStatementWith(title);
            insertStatement.execute();
        } catch (SQLException e){
            throw new IOException(e.getMessage());
        }
    }

    private void updateInsertStatementWith(Title title) throws SQLException{
        for(Parameter parameter: toParameters(title))
            updateInsertStatementWith(parameter);
    }

    private void updateInsertStatementWith(Parameter parameter) throws SQLException{
        if(isNull(parameter.value))
            insertStatement.setNull(parameter.id, parameter.type);
        else
            insertStatement.setObject(parameter.id, parameter.value);
    }

    private boolean isNull(Object value) {
        return value instanceof Integer && (Integer) value == -1;
    }

    private List<Parameter> toParameters(Title title) {
        return List.of(
                new Parameter(1, title.id(), Types.LONGNVARCHAR);
                new Parameter(2, title.title(), Types.LONGNVARCHAR);
                new Parameter(3, title.year(), Types.LONGNVARCHAR);
                new Parameter(4, title.duration(), Types.LONGNVARCHAR);
        )
    }

    private record Parameter(int id, Object value, int type){
    }
    @Override
    public void close() throws Exception {
        this.connection.commit();
    }
}
