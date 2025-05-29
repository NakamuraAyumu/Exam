package bean;

import java.sql.Connection;
import java.util.List;

public class TestSaveRequest {
    private List<Test> testList;
    private Connection connection;

    public TestSaveRequest(List<Test> testList, Connection connection) {
        this.testList = testList;
        this.connection = connection;
    }

    public List<Test> getTestList() {
        return testList;
    }

    public Connection getConnection() {
        return connection;
    }
}
