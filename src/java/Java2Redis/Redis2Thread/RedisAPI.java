package Java2Redis.Redis2Thread;

/***Created by moyongzhuo
 *On 2018/3/6  ***9:37.
 ******/
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis操作接口

 */
public class RedisAPI {
    private static JedisPool pool = null;

    /**
     * 构建redis连接池
     *
     * @param ip
     * @param port
     * @return JedisPool
     */
    public static JedisPool getPool() {
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
/*            MaxActive：可用连接实例的最大数目，为负数的时候没有限制。
            MaxIdle:空闲连接实例的最大数目，为负值时没有限制。
            MaxWait：等待获取链接的超时时间。*/
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(-1);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(-1);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(1000 * 100);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            //config.auth("xiemo");
            pool = new JedisPool(config, "127.0.0.1", 6379);
        }
        return pool;
    }

    /**
     * 返还到连接池
     *
     * @param pool
     * @param redis
     */
    public static void returnResource(JedisPool pool, Jedis redis) {
        if (redis != null) {
            pool.returnResource(redis);
        }
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static String get(String key){
        String value = null;

        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }

        return value;
    }
}




