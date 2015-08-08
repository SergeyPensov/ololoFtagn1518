package http;

import finalStates.Constants;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.File;

/**
 * @Author Sergey Pensov
 */
public class APIServer {
    public APIServer() {
        Server apiServer;


        apiServer = new Server();
        ServerConnector connector = new ServerConnector(apiServer);
        connector.setPort(Constants.PORT);
        connector.setHost(Constants.HOST);
        apiServer.addConnector(connector);

        HandlerList apiHandlers = new HandlerList();
        ClassLoader loader = this.getClass().getClassLoader();
        File indexLoc = new File(loader.getResource("ui").getFile());
        String htmlLoc = indexLoc.getParentFile().getAbsolutePath();
        System.out.println(htmlLoc);

        //add page with visualisator;
        ServletContextHandler apiHandler = new ServletContextHandler();
        ServletHolder defaultServletHolder = new ServletHolder(new DefaultServlet());
        defaultServletHolder.setInitParameter("resourceBase", htmlLoc +"\\ui");
        apiHandler.addServlet(defaultServletHolder, "/*");
        apiHandler.setWelcomeFiles(new String[]{"index.html"});


        apiHandlers.addHandler(apiHandler);
        apiHandlers.addHandler(new DefaultHandler());

        apiServer.setHandler(apiHandlers);
        try {

            apiServer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
