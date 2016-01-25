package lu.cortex.registry.container.service;

import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;

import lu.cortex.registry.container.service.utils.LRUinMemoryCache;

public class RegistryTest {


    @Test
    public void eviction() throws Exception {
        final LRUinMemoryCache<String, String> cache = new LRUinMemoryCache<>(1000, 5, 100);
        cache.put("one", "one");
        Assert.assertTrue("key must be existing.", cache.isExist("one"));
        Thread.sleep(100);
        Assert.assertTrue("key must be existing.", cache.isExist("one"));
        Thread.sleep(1200);
        Assert.assertFalse("key must be cleaning from cache system after more 1 s.", cache.isExist("one"));
    }

    @Test
    public void overflow() throws Exception {
        final LRUinMemoryCache<String, String> cache = new LRUinMemoryCache<>(1000, 5, 100);
        Stream.of("one", "two", "three", "four", "five").forEach(e -> cache.put(e, e));
        Assert.assertEquals(5, cache.size());
        cache.put("six", "six");
        Thread.sleep(250);
        Assert.assertEquals(0, cache.size());
        Thread.sleep(250);
        cache.put("one", "one");
        Assert.assertEquals(1, cache.size());
    }
}
