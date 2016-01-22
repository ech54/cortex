package lu.cortex.registry.container.service.utils;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class LRUinMemoryCache<K, V> {

    private long evictionTime = 0l;
    private long timeToLive = 0l;
    private long maxItems = 0l;
    private final Map<K, LRUInMemoryObject<V>> cache = new ConcurrentHashMap<>();

    private Lock lock = new ReentrantLock();

    public LRUinMemoryCache(final long timeToLive, final long maxItems) {
        this.timeToLive = timeToLive;
        this.maxItems = maxItems;
        this.checkRegistry();
    }


    class LRUInMemoryObject<V> {

        private final Instant birth = Instant.now();
        protected V toCache;

        protected LRUInMemoryObject(final V toCache) {
            this.toCache = toCache;
        }

        public Instant getBirth() {
            return birth;
        }

        public V getValue() {
            return this.toCache;
        }
    }

    public V get(final K key) {
        return (V) synchronize(new Operation() {
            @Override
            public Object execute() {
                return cache.get(key).getValue();
            }
        });
    }

    public void put(final K key, V value) {
        synchronize(new Operation() {
            @Override
            public Object execute() {
                return cache.put(key, new LRUInMemoryObject<>(value));
            }
        });
    }

    public void remove(final K key) {
        synchronize(new Operation() {
            @Override
            public Object execute() {
                return cache.remove(key);
            }
        });
    }

    protected void checkRegistry() {
        final ExecutorService pool = Executors.newFixedThreadPool(1);
        registerForShutdown(pool);
        Callable<Boolean> cleanUpTask = () -> cleanUp();
        try {
            while(true) {
                pool.submit(cleanUpTask);
                Thread.sleep(evictionTime);
            }
        } catch(final Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void registerForShutdown(final ExecutorService pool) {
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                if (pool != null && !pool.isShutdown())
                    pool.shutdown();
            }
        });
    }

    public Boolean cleanUp() {
        try {
            lock.lock();
            final Instant today = Instant.now();
            final List<K> oldestObjects = this.cache.entrySet().stream()
                    .filter(e -> (today.getLong(ChronoField.MILLI_OF_SECOND) - e.getValue().birth.getLong(ChronoField.MILLI_OF_SECOND) == timeToLive))
                    .map(e -> e.getKey())
                    .collect(Collectors.toList());
            oldestObjects.stream().forEach(k -> this.remove(k));
        } finally {
            lock.unlock();
        }
        return true;
    }

    protected Object synchronize(final Operation operation) {
        try {
            lock.lock();
            return operation.execute();
        }
        finally{
            lock.unlock();
        }
    }

    interface Operation {


        Object execute();

    }
}
