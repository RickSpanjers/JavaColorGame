package simongame;

import context.UserTestContext;
import domain.Player;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

public class TestUsers {

    //Instances of a valid and invalid user
    private Player correctUser = new Player(5, "RickSpanjers", "Rick.spanjers@outlook.com", "Test1234!");
    private Player falseUser = new Player(6, "RickSpanjers2", "Rickspanjersoutlook.com", "Test123");

    //Instance of a test User database table
    private UserTestContext context = new UserTestContext();

    /**
     * Test for Constructor of User.
     * Test whether or not you can make a new User object without issues
     * @author Rick Spanjers
     */
    @Test
    public void TestCaseConstructor() {
        Assert.assertEquals("RickSpanjers", correctUser.getPlayerName());
        Assert.assertEquals("Test1234!", correctUser.getPlayerPassword());
        Assert.assertEquals(5, correctUser.getPlayerId());
    }


    /**
     * Test to register a new User.
     * Test whether or not you can make a new user with valid data
     * @author Rick Spanjers
     */
    @Test
    public void TestRegisterTrue() {
        correctUser.setPlayerMail("Rick.spanjers@Outlook.com");
        Assert.assertTrue(context.registerUser(correctUser));
        Assert.assertEquals(5, context.getAllPlayers().size());
        Assert.assertEquals("RickSpanjers", context.getUserById(5).getPlayerName());
    }


    /**
     * Test to register a new User.
     * Test whether or not you can make a new user with invalid data
     * @author Rick Spanjers
     */
    @Test
    public void TestRegisterFalse() {
        falseUser.setPlayerMail("Rick.spanjersOutlook.com");
        Assert.assertFalse(context.registerUser(falseUser));
        Assert.assertEquals(4, context.getAllPlayers().size());
    }


    /**
     * Test to login with an existing user.
     * Test whether or not you can log into the application with a valid user
     * @author Rick Spanjers
     */
    @Test
    public void TestLoginTrue() {
        context.registerUser(correctUser);
        Assert.assertTrue(context.loginUser("User01", "user01@outlook.com", "Test123"));
    }


    /**
     * Test to login with an existing user.
     * Test whether or not you can log into the application with an invalid user
     * @autzor Rick Spanjers
     */
    @Test
    public void TestLoginFalse() {
        context.registerUser(falseUser);
        Assert.assertFalse(context.loginUser("Rick3", "rickspanjersoutlook.com", "123"));
    }
}
