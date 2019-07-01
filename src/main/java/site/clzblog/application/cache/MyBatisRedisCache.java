package site.clzblog.application.cache;

import org.apache.ibatis.cache.Cache;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import site.clzblog.application.utils.SerializeUtils;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyBatisRedisCache implements Cache {

    private Jedis redisClient = newRedisClient();

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private String id;

    public MyBatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }


    public String getId() {
        return this.id;
    }


    public int getSize() {

        return Integer.valueOf(redisClient.dbSize().toString());
    }


    public void putObject(Object key, Object value) {
        redisClient.set(SerializeUtils.serializeable(key.toString()), SerializeUtils.serializeable(value));
    }

    public Object getObject(Object key) {
        return SerializeUtils.unserializable(redisClient.get(SerializeUtils.serializeable(key.toString())));
    }


    public Object removeObject(Object key) {
        return redisClient.expire(SerializeUtils.serializeable(key.toString()), 0);
    }


    public void clear() {
        redisClient.flushDB();
    }


    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    private static Jedis newRedisClient() {
        JedisPool pool = new JedisPool("127.0.0.1", 6379);
        return pool.getResource();
    }
}
