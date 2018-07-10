package spring.inject;

import javax.inject.Inject;

public class DaytimeFeed implements Feed {

    @Inject
    LifecyclePostProcessor postProcessor;

    public DaytimeFeed(LifecyclePostProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }

    @Override
    public void process() {
        postProcessor.resumeAllScreens();
    }
}
