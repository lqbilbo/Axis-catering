package spring.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.config.KnightConfig;
import spring.inject.Knight;

public class KnightMain {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/knight.xml");
        Knight knight = context.getBean(Knight.class);
        knight.embarkOnQuest();

        AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext(KnightConfig.class);
        Knight knight1 = context1.getBean(Knight.class);
        knight1.embarkOnQuest();
//        screen feed
        /*DaytimeFeed daytimeFeed = context.getBean(DaytimeFeed.class);
        OvernightFeed overnightFeed = context.getBean(OvernightFeed.class);
        overnightFeed.process();

        Thread.sleep(3000);

        daytimeFeed.process();*/
        context1.close();
    }
}
