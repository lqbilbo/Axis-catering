package com.axisframework.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 抽象工厂。为领域对象(比如聚合Aggregates和事件Events)提供唯一性标识的工厂类。
 * 此类通过ServiceLoader发现实现,如果没有找到,它将使用 {@code java.util.UUID}
 * 在 {@code META-INF/services} 中创建名叫 {@code org.axisframework.common.IdentifierFactory} 的文件,
 * 该文件必须包含实现类的全路径名。而且必须包含一个公共的无参构造器并继承自IdentifierFactory。
 * 该类是线程安全的
 *
 * @author luoqi
 * @since 1.0
 */
public abstract class IdentifierFactory {

    private static final Logger logger = LoggerFactory.getLogger(IdentifierFactory.class);
    private static final IdentifierFactory INSTANCE;

    static {
        logger.debug("查找使用context class loader的IdentifierFactory实现类");
        IdentifierFactory factory = locateFactories(Thread.currentThread().getContextClassLoader(), "Context");
        if (factory == null) {
            logger.debug("查找使用IdentifierFactory class loader的IdentifierFactory实现类");
            factory = locateFactories(IdentifierFactory.class.getClassLoader(), "IdentifierFactory");
        }
        if (factory == null) {
            factory = new DefaultIdentifierFactory();
        }
        INSTANCE = factory;
    }

    private static IdentifierFactory locateFactories(ClassLoader classLoader, String classLoaderName) {
        IdentifierFactory found = null;
        Iterator<IdentifierFactory> services = ServiceLoader.load(IdentifierFactory.class, classLoader).iterator();
        if (services.hasNext()) {
            found = services.next();
        }
        return found;
    }

    public static IdentifierFactory getInstance() { return INSTANCE; }

    /**
     * 子类必须实现的方法,用于根据方法中策略生成唯一性标识
     * @return 字符串标识
     */
    protected abstract String generateIdentifier();

}
