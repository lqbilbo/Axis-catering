package spring.inject;

public class RescueDamselQuest implements Quest {
    @Override
    public void embark() {
        System.out.println("Emarking on quest to rescue the damsel!");
    }
}
