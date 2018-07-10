package spi;

public class DBStore implements Store {
    @Override
    public boolean store(Object data) {
        System.out.println("db store successfully");
        return true;
    }
}
