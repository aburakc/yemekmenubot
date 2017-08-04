package aburakc.yemekmenubot;

import aburakc.yemekmenubot.data.LauncMenuRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {


    private static final Logger logger = LoggerFactory
            .getLogger(ApplicationTest.class);

    @Autowired
    private LauncMenuRepository launcMenuRepository;

    @Autowired
    private AppConfiguration appConfiguration;

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test1() throws  Exception {

        logger.debug(appConfiguration.getMONGO_CONN_STR());
    }

}