package spi;

public class FileStore implements Store {
    @Override
    public boolean store(Object data) {
        System.out.println("file store successfully");
        return true;
    }
}
