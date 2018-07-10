package jetty;

import org.eclipse.jetty.embedded.AsyncEchoServlet;
import org.eclipse.jetty.embedded.HelloServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.util.concurrent.ThreadLocalRandom;

public class ExampleServer {

    public static void main(String[] args) throws Exception {

        Thread t = new Thread(()->{
            for (int i = 0; i <10; i++) {
            }
            System.out.println("current thread : " + Thread.currentThread());
        });
        t.start();
        System.out.println("current thread state: " + t.getState());

        t.sleep(500);
        t.yield();
        System.out.println("current thread state: " + t.getState());

        t.join(2000);
        Server server = new Server();

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8090);
        server.setConnectors(new Connector[] { connector });

        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        context.addServlet(HelloServlet.class, "/hello");
        context.addServlet(AsyncEchoServlet.class, "/echo/*");

        HandlerCollection handlers = new HandlerCollection();
        handlers.setHandlers(new Handler[] { context, new DefaultHandler() });
        server.setHandler(handlers);
        System.out.println("current thread : " + Thread.currentThread());

        server.start();
        Thread.yield();
        server.join();

    }
}
