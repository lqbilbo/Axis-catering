package classloader;

public class Main {
    public static void main(String[] args) {
        ClassLoader cl = ClassLoader.getSystemClassLoader();

        try {
            Class<?> cls = cl.loadClass("java.util.ArrayList");
            ClassLoader actualLoader = cls.getClassLoader();
            System.out.println(actualLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
