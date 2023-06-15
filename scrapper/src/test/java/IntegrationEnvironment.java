import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

public class IntegrationEnvironment {
    static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;
    static final JdbcTemplate jdbcTemplate;

    static {
        POSTGRE_SQL_CONTAINER =
                new PostgreSQLContainer<>("postgres:14")
                        .withDatabaseName("scrapper")
                        .withUsername("ilya")
                        .withPassword("123");
        POSTGRE_SQL_CONTAINER.start();

        try {
            Connection connection = POSTGRE_SQL_CONTAINER.createConnection("");
            Liquibase liquibase = new liquibase.Liquibase("master.xml", new DirectoryResourceAccessor(
                    Path.of("../").resolve("migrations")), new JdbcConnection(connection)
            );
            liquibase.update();

            jdbcTemplate = new JdbcTemplate(new SingleConnectionDataSource(
                    POSTGRE_SQL_CONTAINER.createConnection(""), false
            ));
        } catch (LiquibaseException | SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
