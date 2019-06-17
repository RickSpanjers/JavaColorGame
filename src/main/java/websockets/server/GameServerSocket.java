package websockets.server;

import com.google.gson.Gson;
import logging.ILogger;
import logging.LogLevel;
import logging.Logger;
import simongame.ISimonGame;
import simongame.SimonGameLogic;
import websockets.messaging.EnumOperationMessage;
import websockets.messaging.IServerMessageProcessor;
import websockets.messaging.SimonGenericMessage;
import websockets.server.messaging.ServerGeneralMessageHandler;
import websockets.server.messaging.ServerMessageProcessor;
import javax.validation.constraints.NotNull;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;

@ServerEndpoint(value = "/simongame/")
public class GameServerSocket implements IServerSocket {

    private static ArrayList<Session> sessions = new ArrayList<>();
    private static ISimonGame gameLogic = new SimonGameLogic();
    private IServerMessageProcessor processor = new ServerMessageProcessor(new ServerGeneralMessageHandler(gameLogic, this));
    private Gson gson = new Gson();
    private ILogger logger = Logger.getInstance();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("[Connected] SessionID:" + session.getId());
        if (sessions.size() == 4) {
            System.out.println("[#sessions]: " + " Only 4 can connect at once");
            return;
        }
        sessions.add(session);
        System.out.println("[#sessions]: " + sessions.size());

    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            System.out.println("[Server Message received]::From=" + session.getId() + " Message=" + message);
            SimonGenericMessage msg = gson.fromJson(message, SimonGenericMessage.class);
            processor.processMessage(session, msg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    //Methods for sending etc
    @Override
    public void broadcast(SimonGenericMessage msg) {
        for (Session s : sessions) {
            sendTo(s.getId(), gson.toJson(msg));
        }
    }


    @NotNull
    @Override
    public void sendTo(String sessionId, Object object) {
        try {
            String msg = object.toString();
            if(sessionId != null){
                sendToClient(getSessionFromId(sessionId), msg);
            }else{
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            logger.log(e);
        }
    }

    @NotNull
    @Override
    public void sendToOthers(String sessionId, Object object) {
        try {
            Session session = getSessionFromId(sessionId);
            for (Session ses : sessions) {
                if (!ses.getId().equals(session.getId())) {
                    sendTo(ses.getId(), object);
                }
            }
        } catch (Exception e) {
            logger.log(e);
        }

    }

    @NotNull
    public Session getSessionFromId(String sessionId) {
        for (Session s : sessions) {
            if (s.getId().equals(sessionId)) {
                return s;
            }
        }
        return sessions.get(0);
    }

    @Override
    public void sendToClient(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.log(e);
        }
    }

    @Override
    public int getPlayerNumberSession(Session session) {
        int result = 0;
        int count = 0;
        while (count <= sessions.size()) {
            if (session.getId() == sessions.get(count).getId()) {
                return count;
            }
            count++;
        }
        return result;
    }

    @Override
    public ArrayList<Session> getSessions() {
        return sessions;
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("onClose::" + session.getId());
        try {
            processor.processMessage(session, new SimonGenericMessage(EnumOperationMessage.QUITGAME));
            for (Session s : sessions) {
                s.close();
            }
            sessions.clear();
            gameLogic = new SimonGameLogic();
        } catch (Exception e) {
            logger.log(e);
        }
    }

    @OnError
    public void onError(Throwable t) {
        logger.log(t.getMessage(), LogLevel.ERROR);
    }

}
