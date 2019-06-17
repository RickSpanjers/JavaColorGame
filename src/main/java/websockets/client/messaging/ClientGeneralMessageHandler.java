package websockets.client.messaging;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.Player;
import dto.RoundDTO;
import websockets.client.notify.IClientNotify;
import websockets.client.notify.NotifyGUI;
import websockets.messaging.EnumOperationMessage;
import websockets.messaging.IClientMessageHandler;

import java.util.List;

public class ClientGeneralMessageHandler implements IClientMessageHandler {

    private IClientNotify notifyGUI = new NotifyGUI();
    private Gson gson = new Gson();

    @Override
    public void handleMessage(EnumOperationMessage type, String message, String sessionId) {
        switch (type) {
            case SETOPPONENT:
                notifyGUI.notifySetOpponents(gson.fromJson(message, new TypeToken<List<Player>>(){}.getType()));
                break;
            case OPPONENTLOSS:
                notifyGUI.notifyOpponentLoss(Integer.parseInt(message));
                break;
            case REGISTERSUCCESS:
                notifyGUI.notifyRegistrationSuccess();
                break;
            case REGISTERFAIL:
                notifyGUI.notifyRegistrationFailure();
                break;
            case LOGINSUCCESS:
                notifyGUI.notifyLoginSuccess();
                break;
            case LOGINFAIL:
                notifyGUI.notifyLoginFailure();
                break;
            case NOTIFYWAIT:
                notifyGUI.notifyClientWait();
                break;
            case NOTIFYSTART:
                notifyGUI.notifyStartGame();
                break;
            case ERRORSETUP:
                notifyGUI.notifyErrorMsgSetup(message);
                break;
            case ERRORGAME:
                notifyGUI.notifyErrorMsgGame(message);
                break;
            case PROCESROUND:
                showRoundSequence(type, message);
                break;
            case SHOWLASTSEQUENCE:
                showRoundSequence(type, message);
                break;
            case GAMEFINISH:
                notifyGUI.notifyGameFinish();
                break;
            case GAMELOSE:
                notifyGUI.notifyGameLose(Integer.parseInt(message));
                break;
            case HIGHSCORES:
                notifyGUI.notifyAllHighScores(gson.fromJson(message, new TypeToken<List<Player>>(){}.getType()));
                break;
            case RECORDHOLDER:
                notifyGUI.notifyRecordHolder(gson.fromJson(message, Player.class));
                break;
            case SYSTEMRESET:
                notifyGUI.notifySystemReset();
                break;
            default:
                break;
        }
    }

    public IClientNotify getGUINotify() {
        return notifyGUI;
    }

    @Override
    public void setGUINotify(IClientNotify notify) {
        this.notifyGUI = notify;
    }

    private void showRoundSequence(EnumOperationMessage type, String message) {
        RoundDTO dto = gson.fromJson(message, RoundDTO.class);
        if(type == EnumOperationMessage.PROCESROUND){
            notifyGUI.notifyRound(dto.getLastSequence(), dto.getDifficulty(), dto.getLost());
        }else{
            notifyGUI.notifyLastSequence(dto.getLastSequence(), dto.getDifficulty());
        }
    }
}
