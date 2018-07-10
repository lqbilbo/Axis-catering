package spring.inject;

public class FoodcourtScreen implements Screen {
    private final Feed daytimeFeed;
    private final Feed overnightFeed;

    public FoodcourtScreen(DaytimeFeed daytimeFeed, OvernightFeed overnightFeed) {
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
        System.out.println("the FoodcourtScreens have " + state + "...");
    }
}
