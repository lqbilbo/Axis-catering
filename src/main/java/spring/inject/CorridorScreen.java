package spring.inject;

import javax.inject.Inject;

public class CorridorScreen implements Screen {

    @Inject
    private final Feed daytimeFeed;
    @Inject
    private final Feed overnightFeed;

    public CorridorScreen(DaytimeFeed daytimeFeed, OvernightFeed overnightFeed) {
        this.daytimeFeed = daytimeFeed;
        this.overnightFeed = overnightFeed;
    }

    @Override
    public void suspend() {
        show("suspend");
    }

    @Override
    public void resume() {
        show("resume");
    }

    private void show(String state) {
        System.out.println("the CorridorScreens have " + state + "...");
    }
}
