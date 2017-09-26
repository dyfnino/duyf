package com.samton.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-9
 * 说明:
 * To change this template use File | Settings | File Templates.
 */
public class RedisUtil {

    private JedisPool pool = null;

    /**
     * <p>传入ip和端口号构建redis连接池</p>
     *
     * @param ip
     * @param prot
     */
    public RedisUtil(String ip, int prot, int time, String pass) {
        if (pool == null) {

            JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(500);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(5);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(1000 * 100);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            pool = new JedisPool(config, ip, prot, time, pass);
        }
    }

    /**
     * <p>通过配置对象 ip 端口构建连接池</p>
     * @param config 配置对象
     * @param ip ip
     * @param prot 端口
     *   pass 用户
     */
/*    public RedisUtil(JedisPoolConfig config ,String ip,int prot,String pass){
        if (pool ==null) {
            pool =new JedisPool(config,ip,prot,10000,pass);
        }
    }*/

    /**
     * <p>通过配置对象 ip 端口超时时间构建连接池</p>
     * @param config 配置对象
     * @param ip ip
     * @param prot 端口
     * @param timeout 超时时间
     */
/*    public RedisUtil(JedisPoolConfig config ,String ip,int prot ,int timeout,String pass){
        if (pool ==null) {
            pool =new JedisPool(config,ip,prot,timeout,pass);
        }
    }*/

    /**
     * <p>通过连接池对象构建一个连接池</p>
     *
     * @param pool 连接池对象
     */
    public RedisUtil(JedisPool pool) {
        if (this.pool == null) {
            this.pool = pool;
        }
    }


    /**
     * 通过key获取hashmap中的值
     * <p>并释放连接</p>
     *
     * @param key
     * @return成功返回value失败返回null
     */
    public List hmget(String key, String files) {
        Jedis jedis = null;
        List value = null;
        try {
            jedis = pool.getResource();
            value = jedis.hmget(key, files);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return value;
    }

    /*
    *   写入值key是个String  值是一个map
    *
    *
    * */
    public String  hmset(String key, Map<String,String> files) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return  jedis.hmset(key, files);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            return "0";
        } finally {
            returnResource(pool, jedis);
        }
    }


    /**
     * <p>通过key获取储存在redis中的value</p>
     * <p>并释放连接</p>
     *
     * @param key
     * @return成功返回value失败返回null
     */
    public String get(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return value;
    }

    /**
     * <p>向redis存入key和value,并释放连接资源</p>
     * <p>如果key已经存在则覆盖</p>
     *
     * @param key
     * @param value
     * @return成功返回OK失败返回 0
     */
    public String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            return "0";
        } finally {
            returnResource(pool, jedis);
        }
    }


    /**
     * <p>删除指定的key,也可以传入一个包含key的数组</p>
     *
     * @param keys 一个key 也可以使 string数组
     * @return返回删除成功的个数
     */
    public Long del(String... keys) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.del(keys);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            return 0L;
        } finally {
            returnResource(pool, jedis);
        }
    }

    /**
     * <p>通过key向指定的value值追加值</p>
     *
     * @param key
     * @param str
     * @return成功返回添加后value的长度失败返回添加的 value 的长度 异常返回0L
     */
    public Long append(String key, String str) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = pool.getResource();
            res = jedis.append(key, str);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            return 0L;
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }

    /**
     * <p>判断key是否存在</p>
     *
     * @param key
     * @return true OR false
     */
    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            return false;
        } finally {
            returnResource(pool, jedis);
        }
    }

    /**
     * <p>设置key value,如果key已经存在则返回0,nx==> not exist</p>
     *
     * @param key
     * @param value
     * @return成功返回1如果存在和发生异常返回 0
     */
    public Long setnx(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.setnx(key, value);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            return 0L;
        } finally {
            returnResource(pool, jedis);
        }
    }

    /**
     * <p>设置key value并制定这个键值的有效期</p>
     *
     * @param key
     * @param value
     * @param seconds 单位:秒
     * @return成功返回OK失败和异常返回null
     */
    public String setex(String key, String value, int seconds) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            res = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return res;
    }


    /**
     * <p>通过批量的key获取批量的value</p>
     *
     * @param keys string数组也可以是一个key
     * @return成功返回value的集合,失败返回null的集合 , 异常返回空
     */
    public List<String> mget(String... keys) {
        Jedis jedis = null;
        List<String> values = null;
        try {
            jedis = pool.getResource();
            values = jedis.mget(keys);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return values;
    }


    /**
     * 返还到连接池
     *
     * @param pool
     * @param jedis
     */
    public static void returnResource(JedisPool pool, Jedis jedis) {
        if (jedis != null) {
            pool.returnResource(jedis);
        }
    }
    //测试两个
/*    public static void main(String[]args)throws Exception{
        RedisUtil r = new RedisUtil("112.35.29.197",17479,10000,"$1$samtonre$qy/tQZ1rQFL5IvDa6vrss.");
       // String t = r.set("wb","wbwbwb");
        List s = r.hmget("carEquipment","358732058206083");
        System.out.println(s);

*//*        ApplicationContext context = new ClassPathXmlApplicationContext("spring-mvc-redis.xml");
        JedisPool pool = (JedisPool) context.getBean("jedisPool");
        Jedis jedis = pool.getResource();
        List l=jedis.hmget("carEquipment","358732058206083");
        System.out.println(l);
        pool.returnResource(jedis);*//*
    }*/

}
