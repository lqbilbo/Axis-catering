package scratch;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SimpleJDKDynamicProxyDemo {

    static interface IServiceA {
        public void sayHello();
    }

    static class RealServiceA implements IServiceA {

        @Override
        public void sayHello() {
            System.out.println("Hello");
        }
    }

    static interface IServiceB {
        public void fly();
    }

    static class RealServiceB implements IServiceB {

        @Override
        public void fly() {
            System.out.println("fly");
        }
    }

    static class SimpleInvocationHandler implements InvocationHandler {

        private Object readObj;

        public SimpleInvocationHandler(Object readObj) {
            this.readObj = readObj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("entering " + method.getName());
            Object result = method.invoke(readObj, args);
            System.out.println("leaving " + method.getName());
            return result;
        }

    }

    private static <T> T getProxy(Class<T> intf, T realObj) {
        return (T) Proxy.newProxyInstance(intf.getClassLoader(), new Class<?>[] {intf}, new SimpleInvocationHandler(realObj));
    }
    public static void main(String[] args) {
        IServiceA realServiceA= new RealServiceA();
        IServiceA proxyServiceA = getProxy(IServiceA.class, realServiceA);
        proxyServiceA.sayHello();

        IServiceB realServiceB= new RealServiceB();
        IServiceB proxyServiceB = getProxy(IServiceB.class, realServiceB);
        proxyServiceB.fly();
    }
}
