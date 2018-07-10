package spring.inject;

import javax.inject.Inject;

public class OvernightFeed implements Feed {

    @Inject
    LifecyclePostProcessor postProcessor;

    public OvernightFeed(LifecyclePostProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }

    @Override
    public void process() {
        postProcessor.suspendAllScreens();
    }
}
