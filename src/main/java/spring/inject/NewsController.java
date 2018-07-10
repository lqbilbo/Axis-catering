package spring.inject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewsController implements Controller {

    private final PersistenceService persistence;
    private final NewsService newsService;

    public NewsController(PersistenceService persistence, NewsService newsService) {
        this.newsService = newsService;
        this.persistence = persistence;
    }

    private void init() {
        persistence.start();
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return null;
    }

    private void destroy() {
        persistence.shutDown();
    }
}
