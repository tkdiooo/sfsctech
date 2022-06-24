import com.test.data.Runner;
import com.test.data.service.ReadService;
import com.test.data.service.WriteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Class RedisTest
 *
 * @author 张麒 2018-8-13.
 * @version Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Runner.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DataTest {

    private static Logger logger = LoggerFactory.getLogger(DataTest.class);

    @Autowired
    private ReadService readService;

    @Autowired
    private WriteService writeService;


    @Test
    public void testKafka() {
        readService.read();
        System.out.println("=======================");
        writeService.read();
    }
}
