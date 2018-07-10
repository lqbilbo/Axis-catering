/**
 * Copyright (C) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package arcade;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class LifecyclePostProcessor implements BeanPostProcessor {
    private final List<Screen> screens = new ArrayList<Screen>();

    public void suspendAllScreens() {
        for (Screen screen : screens)
            screen.suspend();
    }

  public void resumeAllScreens() {
      for (Screen screen : screens)
          screen.resume();
  }

    public Object postProcessAfterInitialization(Object object, String key)
           throws BeansException {
        screens.add((Screen) object);
        return object;
    }

    public Object postProcessBeforeInitialization(Object o, String key)
           throws BeansException { return o; }

  public static void main(String[] args) {

    BeanFactory injector = new ClassPathXmlApplicationContext("META-INF/screens.xml");
    LifecyclePostProcessor processor = injector.getBean(LifecyclePostProcessor.class);
    processor.suspendAllScreens();

    processor.resumeAllScreens();
/*
    Screen screen = (Screen) injector.getBean("screen.foodcourt");
    screen.suspend();

    screen.resume();*/

  }
}
