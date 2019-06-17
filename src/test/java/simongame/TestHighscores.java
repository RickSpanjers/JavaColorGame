package simongame;

import context.HighscoreTestContext;
import domain.GameMode;
import domain.Highscore;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
import repository.HighscoreRepository;

import java.time.LocalDateTime;

public class TestHighscores {

    //Instance of the highscore repository
    private HighscoreRepository highscoreRepo = new HighscoreRepository(new HighscoreTestContext());


    /**
     * Test to update the highscore of a user.
     * Test whether or not it's possible to update the highscore of a specific user
     * @author Rick Spanjers
     */
    @Test
    public void updateHighscoreByUserId() {
        Highscore highscore = new Highscore(3, 5, GameMode.CLASSIC);
        highscore.setAchieved(LocalDateTime.now().toString());
        highscoreRepo.updateHighscoreByUserId(1, highscore);

        Assert.assertEquals(5, highscoreRepo.getHighscoreByUserId(1, GameMode.CLASSIC).getRound());
        Assert.assertEquals(3, highscoreRepo.getHighscoreByUserId(1, GameMode.CLASSIC).getDifficulty());
    }


    /**
     * Test to update the highscore of a user
     * Test whether or not it's possible to update the highscore of a non existing user
     * @author Rick Spanjers
     */
    @Test
    public void updateHighscoreByNonExistingId(){
        Highscore highscore = new Highscore(2, 8, GameMode.CLASSIC);
        highscore.setAchieved(LocalDateTime.now().toString());
        Assert.assertFalse(highscoreRepo.updateHighscoreByUserId(8, highscore));
    }


    /**
     * Test to get the highscore of a user
     * Test whether or not you can retrieve the highscore of a specific user
     * @author Rick Spanjers
     */
    @Test
    public void getHighscoreByUserId() {
        Assert.assertEquals(3, highscoreRepo.getHighscoreByUserId(2, GameMode.CLASSIC).getDifficulty());
        Assert.assertEquals(3, highscoreRepo.getHighscoreByUserId(2, GameMode.CLASSIC).getRound());
    }

    /**
     * Test to get the highscore of a user
     * Test whether or not you can retrieve the highscore of a non existing user
     * @author Rick Spanjers
     */
    @Test
    public void getHighscoreByNonExistingUserId() {
        Assert.assertNull(highscoreRepo.getHighscoreByUserId(8, GameMode.CLASSIC));
    }


    /**
     * Test to get all the highscores
     * Test whether or not you can retrieve all highscores in the system
     * @author Rick Spanjers
     */
    @Test
    public void getAllHighscores() {
        Assert.assertEquals(4, highscoreRepo.getAllHighscores().size());
    }


    /**
     * Test to get the highest score
     * Test whether or not you can retrieve the top score in the system
     * @author Rick Spanjers
     */
    @Test
    public void getHighestScore() {
        Assert.assertEquals(4, highscoreRepo.getHighestScore(GameMode.CLASSIC).getHighscore().getDifficulty());
        Assert.assertEquals(4, highscoreRepo.getHighestScore(GameMode.CLASSIC).getHighscore().getRound());
        Assert.assertEquals("user03",highscoreRepo.getHighestScore(GameMode.CLASSIC).getPlayerName());
    }
}
