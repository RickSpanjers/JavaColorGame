package integrationTests;

import mocks.MockSimonGameGUI;
import mocks.MockSimonRegistrationGUI;
import mocks.MockSimonSetupGUI;
import com.google.gson.Gson;
import domain.Color;
import dto.RoundDTO;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
import simongui.interfaces.ISimonGameGUI;
import simongui.interfaces.ISimonRegisterGUI;
import simongui.interfaces.ISimonSetupGUI;
import websockets.client.messaging.ClientGeneralMessageHandler;
import websockets.client.messaging.ClientMessageProcessor;
import websockets.client.notify.IClientNotify;
import websockets.client.notify.NotifyGUI;
import websockets.messaging.EnumOperationMessage;
import websockets.messaging.IClientMessageHandler;
import websockets.messaging.IClientMessageProcessor;
import websockets.messaging.SimonGenericMessage;

import java.util.ArrayList;

public class TestClientMessagingIntegration {

    @Test
    public void RegistrationResultTest()
    {
        SimonGenericMessage resultMsg = new SimonGenericMessage(EnumOperationMessage.REGISTERSUCCESS);
        IClientNotify guiNotify = new NotifyGUI();
        ISimonRegisterGUI registrationGUI = new MockSimonRegistrationGUI();
        guiNotify.setRegisterGUI(registrationGUI);
        IClientMessageHandler handler = new ClientGeneralMessageHandler();
        IClientMessageProcessor processor = new ClientMessageProcessor(handler);

        handler.setGUINotify(guiNotify);
        processor.processMessage("1", resultMsg);
        Assert.assertEquals("Successful registration", ((MockSimonRegistrationGUI) registrationGUI).getMsg());

    }

    @Test
    public void LoginResultTest()
    {
        SimonGenericMessage resultMsg = new SimonGenericMessage(EnumOperationMessage.LOGINSUCCESS);
        IClientNotify guiNotify = new NotifyGUI();
        ISimonSetupGUI setupGUI = new MockSimonSetupGUI();
        guiNotify.setSetupGUI(setupGUI);
        IClientMessageHandler handler = new ClientGeneralMessageHandler();
        IClientMessageProcessor processor = new ClientMessageProcessor(handler);

        handler.setGUINotify(guiNotify);
        processor.processMessage("1", resultMsg);
        Assert.assertTrue(((MockSimonSetupGUI) setupGUI).isLogin());

    }

    @Test
    public void RoundResultTest()
    {
        SimonGenericMessage resultMsg = new SimonGenericMessage(EnumOperationMessage.PROCESROUND);
        ArrayList<Color> colors = new ArrayList<>();
        Gson gson = new Gson();
        resultMsg.setData(gson.toJson(new RoundDTO(colors, 1)));
        IClientNotify guiNotify = new NotifyGUI();
        ISimonGameGUI gameGUI = new MockSimonGameGUI();
        guiNotify.setGameGUI(gameGUI);
        IClientMessageHandler handler = new ClientGeneralMessageHandler();
        IClientMessageProcessor processor = new ClientMessageProcessor(handler);

        handler.setGUINotify(guiNotify);
        processor.processMessage("1", resultMsg);
        Assert.assertEquals(((MockSimonGameGUI) gameGUI).getTurn(), 1);
    }
}
