package simonAlgorithm;
import mocks.MockSimonGameLogic;
import domain.GameMode;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
import simonalgorithm.ISequenceGenerator;
import simonalgorithm.SequenceGenerator;
import simongame.ISimonGame;

public class TestSimonAlgorithm {

    private ISequenceGenerator sequenceGenerator;

    @Test
    public void generateSequenceClassicTest()
    {
        ISimonGame gameLogic = new MockSimonGameLogic();
        gameLogic.getCurrentGame().setGameMode(GameMode.CLASSIC);
        sequenceGenerator = new SequenceGenerator(gameLogic);
        sequenceGenerator.initialize();
        sequenceGenerator.processRound();
        Assert.assertEquals(4, sequenceGenerator.getLastSequence().size());
    }

    @Test
    public void generateSequenceLengthBattleTest()
    {
        ISimonGame gameLogic = new MockSimonGameLogic();
        gameLogic.getCurrentGame().setGameMode(GameMode.LENGTHBATTLE);
        sequenceGenerator = new SequenceGenerator(gameLogic);
        sequenceGenerator.initialize();
        sequenceGenerator.processRound();
        Assert.assertEquals(1, sequenceGenerator.getLastSequence().size());
    }

}
