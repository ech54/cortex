package lu.cortex.registry.container.service.utils;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default cache implementation to handle LRU access.
 * @param <K> The key.
 * @param <V> The value.
 */
public class LRUinMemoryCache<K, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LRUinMemoryCache.class);

    // Time in millisecond before to evict the check cached object to evict.
    private long evictionTime = 1000l; // By default 1 second.

    // Time to live of cached object in millisecond.
    private long timeToLive = 60000l; // By default 1 minute.

    // The max number of items.
    private long maxItems = 10000l; // By default 10 000 items.

    // The cached object.
    private final Map<K, LRUInMemoryObject<V>> cache = new ConcurrentHashMap<>();

    private Lock lock = new ReentrantLock();

    public LRUinMemoryCache(final long timeToLive, final long maxItems, final long evictionTime) {
        this.timeToLive = timeToLive;
        this.maxItems = maxItems;
        this.evictionTime = evictionTime;
        this.checkRegistry();
    }

    /**
     * Object wrapper allows to flag the birth instance
     *  of the cached object.
     * @param <V> The type of object.
     */
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

    /**
     * Retrieve the value based on its key pair.
     * @param key The key pair.
     * @return The corresponding value.
     */
    public V get(final K key) {
        try {
            lock.lock();
            return cache.get(key).getValue();
        }
        finally{
            lock.unlock();
        }
    }

    /**
     * Method provides the size of current cache system.
     * @return The size.
     */
    public int size() {
        try {
            lock.lock();
            return cache.size();
        } finally{
            lock.unlock();
        }
    }

    /**
     * Indicates if the key is known of the cache.
     * @param key The key.
     * @return <code>true</code> if key is existing,
     *  <code>false</code>.
     */
    public boolean isExist(K key) {
        try {
            lock.lock();
            return Boolean.valueOf(cache.containsKey(key));
        }
        finally{
            lock.unlock();
        }
    }

    /**
     * Put a key, value pair in the cache system.
     * @param key The key.
     * @param value The value.
     */
    public void put(final K key, V value) {
        try {
            lock.lock();
            cache.put(key, new LRUInMemoryObject<>(value));
        }
        finally{
            lock.unlock();
        }
    }

    /**
     * Remove from the cache system the given key.
     * @param key The key.
     */
    public void remove(final K key) {
        try {
            lock.lock();
            cache.remove(key);
        }
        finally{
            lock.unlock();
        }
    }

    public List<V> getAll() {
        try {
            lock.lock();
            return cache.values().stream()
                    .map(LRUInMemoryObject::getValue)
                    .collect(Collectors.toList());
        }
        finally{
            lock.unlock();
        }
    }

    protected void checkRegistry() {
        final ExecutorService pool = Executors.newFixedThreadPool(1);
        registerForShutdown(pool);
        final Callable<Boolean> cleanUpTask = () -> cleanUp();
        pool.submit(cleanUpTask);
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

    /**
     * Allow to clean the cache. This method is called internally
     *  by an threading task.
     * @return
     */
    public boolean cleanUpCache() {
        try {
            lock.lock();
            overflowCache();
            evictCache();
            return true;
        } catch(final Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            lock.unlock();
        }
    }

    private boolean cleanUp() {
        try {
            while(true) {
                cleanUpCache();
                Thread.sleep(evictionTime);
            }
        } catch(final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void overflowCache() {
        if (this.cache.size()<= this.maxItems) {
            return;
        }
        this.cache.clear();
    }

    private void evictCache() {
        final List<K> oldestObjects = this.cache.entrySet().stream()
                .filter(e -> ((Instant.now().toEpochMilli() - e.getValue().birth.toEpochMilli()) >= timeToLive))
                .map(e -> e.getKey())
                .collect(Collectors.toList());
        if (oldestObjects.size()>0) {
            oldestObjects.stream().map(k -> "key to remove: "+k).forEach(LOGGER::info);
            oldestObjects.stream().forEach(k -> this.remove(k));
        }
    }

}
