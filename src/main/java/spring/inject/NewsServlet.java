package spring.inject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

@Singleton
public class NewsServlet extends HttpServlet {
    private final PersistenceService persistence;
    private final NewsService newsService;

    @Inject
    public NewsServlet(PersistenceService persistence, NewsService newsService) {
        this.newsService = newsService;
        this.persistence = persistence;
    }

    @Override
    public void init() throws ServletException {
        persistence.start();
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res);
    }

    @Override
    public void destroy() {
        persistence.shutDown();
    }
}
