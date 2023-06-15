import org.junit.After;
import org.junit.Test;
import org.springframework.test.jdbc.JdbcTestUtils;
import ru.tinkoff.edu.java.scrapper.database.dto.Link;
import ru.tinkoff.edu.java.scrapper.database.jdbc.JdbcLinkService;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class TestLink extends IntegrationEnvironment {
    JdbcLinkService linkTable = new JdbcLinkService(jdbcTemplate);

    @After
    public void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "link");
    }

    @Test
    public void addLink() {
        Link link = new Link(1L, "testUrl");

        linkTable.add(link);
        var res = linkTable.findAll();

        assertTrue(res.stream().anyMatch(l -> l.url().equals(link.url())));
    }

    @Test
    public void delete() {
        Link link1 = new Link(1L, "l1");
        Link link2 = new Link(2L, "l2");
        linkTable.add(link1);
        linkTable.add(link2);

        linkTable.remove(link1);
        var res = linkTable.findAll();

        assertTrue(res.stream().anyMatch(l -> l.url().equals(link2.url())) &&
                res.stream().noneMatch(l -> l.url().equals(link1.url())));
    }

    @Test
    public void findAll() {
        Link link1 = new Link(1L, "l1");
        Link link2 = new Link(2L, "l2");
        Link link3 = new Link(3L, "l3");
        linkTable.add(link1);
        linkTable.add(link2);
        linkTable.add(link3);

        var res = linkTable.findAll();

        List<String> list = res.stream().map(Link::url).toList();
        System.out.println(list);
        assertTrue(list.contains(link1.url()) && list.contains(link2.url()) && list.contains(link3.url()));

    }
}
