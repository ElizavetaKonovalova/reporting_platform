import application.Application;
import application.models.Jobs;
import application.models.Organisations;
import application.models.WorkUnits;
import application.repositories.JobRepository;
import application.repositories.OrganisationRepository;
import application.repositories.WorkUnitRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ekonovalova on 12/5/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class TestDB {

    @Test
    public void testConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Properties properties = new Properties();
        properties.setProperty("user","postgres");
        properties.setProperty("password","Bpanz123");
        properties.setProperty("ssl","false");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bpa_db", properties);
    }
}
