package org.debug.smartdeviceiot.server;


import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public abstract class BaseTest {
    public static Logger log= LoggerFactory.getLogger(BaseTest.class);

}
