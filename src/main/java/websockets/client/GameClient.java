package websockets.client;

import org.eclipse.jetty.util.component.LifeCycle;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;

public class GameClient {
    public static void main(String[] args) {
        URI uri = URI.create("ws://localhost:8095/simongame/");
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            try {
                Session session = container.connectToServer(GameClientSocket.class, uri);
                session.getBasicRemote().sendText("Hello");
                Thread.sleep(100000);
                session.close();
            } finally {
                if (container instanceof LifeCycle) {
                    ((LifeCycle) container).stop();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}