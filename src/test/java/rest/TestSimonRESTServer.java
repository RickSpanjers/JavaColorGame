package rest;

import mocks.MockSimonRESTClient;
import domain.GameMode;
import domain.Highscore;
import domain.Player;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

public class TestSimonRESTServer {


    @Test
    public void registerTest()
    {
        IRESTClient client = new MockSimonRESTClient();
        boolean result = client.register("RickSpanjers2", "Test@Test.nl", "Rick123!");
        Assert.assertNotNull(result);
    }

    @Test
    public void getHighscoreTest()
    {
        IRESTClient client = new MockSimonRESTClient();
        Highscore result = client.getHighscore(1);
        Assert.assertNotNull(result);
    }

    @Test
    public void setHighscoreTest()
    {
        IRESTClient client = new MockSimonRESTClient();
        boolean result = client.setHighscore(1, 1, 1, GameMode.CLASSIC);
        Assert.assertNotNull(result);
    }

    @Test
    public void getHighestScoreTest()
    {
        IRESTClient client = new MockSimonRESTClient();
        Player result = client.getHighestScore(GameMode.CLASSIC);
        Assert.assertNotNull(result);
    }

    @Test
    public void getAllHighscoresTest()
    {
        IRESTClient client = new MockSimonRESTClient();
        String result = client.getAllHighscores(GameMode.CLASSIC);
        Assert.assertNotNull(result);
    }


}
