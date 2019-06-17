package simongame;

import domain.Board;
import domain.Game;
import domain.Player;
import domain.PlayerState;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestGameDomain{

    @Test
    public void currentGameDataTest()
    {
        Game currentGame = new Game();
        Assert.assertFalse(currentGame.getGameActive());
        Assert.assertEquals(currentGame.getPlayersThatHaveNotLost().size(), 0);
        Assert.assertFalse(currentGame.isRoundFinished());
    }

    @Test
    public void playerDataTest(){
        Player p = new Player(1, "Test");
        Assert.assertNotEquals(p.getBoard(), null);
        Assert.assertNotEquals(p.getPlayerState(), null);
    }

    @Test
    public void boardDataTest(){
        Board b = new Board();
        Assert.assertNotEquals(b.getPressedColors(), null);
        Assert.assertNotEquals(b.getColors(), null);
    }

    @Test
    public void playerStateDataTest(){
        PlayerState state = new PlayerState();
        Assert.assertFalse(state.getLost());
        Assert.assertFalse(state.getReady());
    }

}
