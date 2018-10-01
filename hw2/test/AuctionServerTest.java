import org.junit.Test;

//Test?
public class AuctionServerTest {
    @Test(expected = NullPointerException.class)
    public void testSubmit(){
        String[] args = {};
        for (int i = 0; i < 10; i++) {
            Simulation.main(args);
        }
    }
}
