package com.common.utils;

import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

public class RedisClient {

    private Jedis jedis;//非切片额客户端连接
    private JedisPool jedisPool;//非切片连接池
    private ShardedJedis shardedJedis;//切片额客户端连接
    private ShardedJedisPool shardedJedisPool;//切片连接池

    private static RedisClient redisClient = null;


    public RedisClient(){
        initialPool();
//        initialShardedPool();
//        shardedJedis = shardedJedisPool.getResource();
        jedis = jedisPool.getResource();
    }

    /**
     * 初始化非切片池
     */
    private void initialPool(){
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(200);
        config.setMaxIdle(50);
        config.setMinIdle(8);//设置最小空闲数
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
//Idle时进行连接扫描
        config.setTestWhileIdle(true);
//表示idle object evitor两次扫描之间要sleep的毫秒数
        config.setTimeBetweenEvictionRunsMillis(30000);
//表示idle object evitor每次扫描的最多的对象数
        config.setNumTestsPerEvictionRun(10);
//表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
        config.setMinEvictableIdleTimeMillis(60000);


        jedisPool = new JedisPool(config,"121.40.106.103",6379);
    }

    /**
     * 初始化切片池
     */
    private void initialShardedPool(){
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setMaxWaitMillis(10*1000l);
        config.setTestOnBorrow(false);
        // slave链接
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        shards.add(new JedisShardInfo("121.40.106.103", 6379, "master"));

        // 构造池
        shardedJedisPool = new ShardedJedisPool(config, shards);
    }

    /**
     * 设置redis
     * @param key
     * @param value
     * @throws Exception
     */
    public void setRedis(String key,String value)throws Exception{
        jedis.set(key,value);
    }

    /**
     * 通过key获取缓存值
     * @param key
     * @return
     * @throws Exception
     */
    public String getRedis(String key)throws Exception{
        return jedis.get(key);
    }

    /**
     * 获取redisclient
     * @return
     */
    public static RedisClient getRedisClient(){
        if(null == redisClient){
            redisClient = new RedisClient();
        }
        return redisClient;
    }



}
