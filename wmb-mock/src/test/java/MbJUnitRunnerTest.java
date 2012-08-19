import com.ibm.broker.plugin.MbMessage;
import com.pressassociation.bus.junit.MbJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MbJUnit4ClassRunner.class)
public class MbJUnitRunnerTest {
    @Test
    public void testCreationOfMbMessage() throws Exception {
        MbMessage message = new MbMessage();
    }
}
