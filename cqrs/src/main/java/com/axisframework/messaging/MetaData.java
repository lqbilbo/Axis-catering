package com.axisframework.messaging;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Metadata:元数据是和payload一起存在于一个消息体(Message)中的组件。一般情况下，metadata
 * 包含了payload中不属于领域特定的部分。
 *
 * @author luoqi
 * @since 1.0
 */
public class MetaData implements Map<String, Object>, Serializable {


    private static final long serialVersionUID = 4175101605706370724L;
    private static final MetaData EMPTY_META_DATA = new MetaData();
    public static final String UNSUPPORTED_MUTATION_MSG = "Metadata is immutable.";

    private final Map<String, Object> values;

    public MetaData() {
        values = Collections.emptyMap();
    }

    public static MetaData emptyInstance() {
        return EMPTY_META_DATA;
    }

    public MetaData(Map<String, ?> items) {
        values = Collections.unmodifiableMap(new HashMap<>(items));
    }

    /**
     * 根据metaDataEntries创建Metadata新实例
     * 如果该实例已经存在，则直接返回。这样做比单纯的拷贝构造器{@link #MetaData(java.util.Map)}更合适
     *
     * @param metaDataEntries
     * @return
     */
    public static MetaData from(Map<String, ?> metaDataEntries) {
        if (metaDataEntries instanceof MetaData) {
            return (MetaData) metaDataEntries;
        } else if (metaDataEntries == null || metaDataEntries.isEmpty()) {
            return MetaData.emptyInstance();
        }
        return new MetaData(metaDataEntries);
    }

    public static MetaData with(String key, Object value) {
        return MetaData.from(Collections.singletonMap(key, value));
    }

    public MetaData and(String key, Object value) {
        HashMap<String, Object> newValues = new HashMap<>(values);
        newValues.put(key, value);
        return new MetaData(newValues);
    }

    public MetaData subset(String... keys) {
        return MetaData.from(Stream.of(keys).filter(this::containsKey).collect(new MetaDataCollector(this::get)));
    }


    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return values.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        return values.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return null;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Object> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return null;
    }

    /**
     * 实现了Collector,不同之处在于不允许空值{@code null}
     */
    private class MetaDataCollector implements Collector<String, Map<String, Object>, MetaData> {

        private final Function<String, Object> valueProvider;

        public MetaDataCollector(Function<String, Object> valueProvider) {
            this.valueProvider = valueProvider;
        }

        @Override
        public Supplier<Map<String, Object>> supplier() {
            return HashMap::new;
        }

        @Override
        public BiConsumer<Map<String, Object>, String> accumulator() {
            return (map, key) -> map.put(key, valueProvider.apply(key));
        }

        @Override
        public BinaryOperator<Map<String, Object>> combiner() {
            return (m1, m2) -> {
                Map<String, Object> result = new HashMap<>(m1);
                result.putAll(m2);
                return result;
            };
        }

        @Override
        public Function<Map<String, Object>, MetaData> finisher() {
            return MetaData::from;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.emptySet();
        }
    }
}
