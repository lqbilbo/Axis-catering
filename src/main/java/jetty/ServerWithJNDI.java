package jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class ServerWithJNDI {
    public static void main(String[] args) throws Exception {
        //Create the server
        Server server = new Server(8089);

        //Enable parsing of jndi-related parts of web.xml and jetty-env.xml
        org.eclipse.jetty.webapp.Configuration.ClassList classList = org.eclipse.jetty.webapp.Configuration.ClassList.setServerDefault(server);
        classList.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration");

        //Create a WebApp
        WebAppContext webApp = new WebAppContext();
        webApp.setContextPath("/");
        webApp.setWar("./my-foo-webapp.war");
        server.setHandler(webApp);

        server.start();
        server.join();
    }
}
