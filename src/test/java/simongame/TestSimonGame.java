package simongame;

import mocks.MockSimonGameGUI;
import mocks.MockSimonRegistrationGUI;
import mocks.MockSimonSetupGUI;
import domain.Color;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import domain.SimonColor;
import websockets.client.notify.IClientNotify;
import websockets.client.notify.NotifyGUI;

public class TestSimonGame {

    private ISimonGUI guiLogic = new SimonGameGUILogic();
    private ISimonGame game = new SimonGameLogic();
    private MockSimonGameGUI mockGUI = new MockSimonGameGUI();
    private MockSimonRegistrationGUI mockRegistrationGUI = new MockSimonRegistrationGUI();
    private MockSimonSetupGUI mockSetup = new MockSimonSetupGUI();
    private IClientNotify guiNotify = new NotifyGUI();


    @Test
    public void registerPlayer() {
        guiNotify.setRegisterGUI(mockRegistrationGUI);

        String username = "RickSpanjers3";
        String mail = "Rick.Spanjers3@outlook.com";
        String password = "rick123";

        game.registerPlayer(username,mail,password);
        guiNotify.notifyRegistrationSuccess();
        Assert.assertEquals("Successful registration", mockRegistrationGUI.getMsg());
    }

    @Test
    public void registerPlayerFalse() {
        guiNotify.setRegisterGUI(mockRegistrationGUI);

        String username = "RickSpanjers";
        String mail = "Rick.Spanjers@outlook.com";
        String password = "rick123";

        game.registerPlayer(username,mail,password);
        guiNotify.notifyRegistrationFailure();
        Assert.assertNotEquals("Successful registration", mockRegistrationGUI.getMsg());
    }

    @Test
    public void loginPlayer() {
        guiNotify.setSetupGUI(mockSetup);
        guiNotify.notifyLoginSuccess();
        Assert.assertTrue(mockSetup.isLogin());
    }

    @Test
    public void loginPlayerFalse() {
        guiNotify.setSetupGUI(mockSetup);
        guiNotify.notifyLoginFailure();
        Assert.assertFalse(mockSetup.isLogin());
    }


    @Test
    public void enterSequence() {
        guiNotify.setGameGUI(mockGUI);
        Color color = new Color(SimonColor.RED);
        int playerNr = 1;
        game.notifyReady(1);
        game.startNewGame();
        game.enterSequence(color, playerNr);
        Assert.assertFalse(mockGUI.isPlayerMove());
    }

    @Test
    public void setGameDifficulty() {
        mockGUI.setLoggedIn("Rick", 1);
        Assert.assertEquals(1, mockGUI.getDifficulty());
        Assert.assertEquals("Rick", mockGUI.getPlayerName());
    }

    @Test
    public void setGameDifficultyFalse() {
        mockGUI.setLoggedIn("Rick", 5);
        guiNotify.setGameGUI(mockGUI);
        guiNotify.notifyErrorMsgGame("False difficulty");
        Assert.assertEquals(5, mockGUI.getDifficulty());
        Assert.assertEquals("Rick", mockGUI.getPlayerName());
        Assert.assertEquals("False difficulty", mockGUI.getMsg());
    }


    @Test
    public void startNewGame() {
        guiNotify.setGameGUI(mockGUI);
        guiNotify.notifyStartGame();
        game.startNewGame();
        Assert.assertTrue(mockGUI.isStartGame());
    }


    @Test
    public void setGameVictor() {
        guiNotify.setGameGUI(mockGUI);
        guiNotify.notifyGameFinish();
        Assert.assertEquals("Game finished!", mockGUI.getMsg());
    }
}
