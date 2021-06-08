import com.lagou.edu.service.TransferService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author jingjiejiang
 * @history Jun 8, 2021
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TransactionalTest {

    @Autowired
    TransferService transferService;

    @Test
    public void tansactionTest1() throws Exception {
        transferService.transfer("6029621011000", "6029621011001", 100);
    }
}