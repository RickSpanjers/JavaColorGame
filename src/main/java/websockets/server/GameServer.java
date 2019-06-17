package websockets.server;

import logging.ILogger;
import logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import javax.websocket.server.ServerContainer;


public class GameServer {

    public static void main(String[] args) {
        ILogger logger = Logger.getInstance();
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8095);
        server.addConnector(connector);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        try {
            ServerContainer wsContainer = WebSocketServerContainerInitializer.configureContext(context);
            wsContainer.addEndpoint(GameServerSocket.class);
            server.start();
            server.join();
        } catch (Exception e) {
            logger.log(e);
        }
    }
}