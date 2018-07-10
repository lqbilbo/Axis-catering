package spring.inject;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;

public class LifecyclePostProcessor implements BeanPostProcessor {
    private final List<Screen> screens = new ArrayList<>();

    public void suspendAllScreens() {
        for (Screen screen : screens) screen.suspend();
    }

    public void resumeAllScreens() {
        for (Screen screen : screens) screen.resume();
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if (o instanceof Screen) screens.add((Screen) o);
        return o;
    }
}
