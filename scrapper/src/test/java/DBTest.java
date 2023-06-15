import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class DBTest extends IntegrationEnvironment {

    @Test
    public void linkTableCreate() {
        try {
            Connection connection = POSTGRE_SQL_CONTAINER.createConnection("");
            PreparedStatement statement = connection.prepareStatement("select * from link");
            var resultSet = statement.executeQuery();
            Set<String> linkFields = Set.of("link_id", "url");
            assertEquals(resultSet.getMetaData().getColumnCount(), linkFields.size());
            for (int i = 1; i <= linkFields.size(); i++) {
                assertTrue(linkFields.contains(resultSet.getMetaData().getColumnName(i)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void linkTableAddLink() {
        try {
            String url = "https://link_test.com";

            Connection connection = POSTGRE_SQL_CONTAINER.createConnection("");
            PreparedStatement statement = connection.prepareStatement("insert into link(url) values ('https://link_test.com')");
            statement.addBatch();
            statement.executeBatch();


            PreparedStatement statement1 = connection.prepareStatement("select url from link");
            statement1.executeQuery();
            var resultSet = statement1.executeQuery();

            resultSet.next();
            String got = resultSet.getString(1);
            assertEquals(url, got);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
