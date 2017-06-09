package com.moui.test;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * Created by zhulei on 2017/6/9.
 */
public class MyJedisConnectionFactory extends JedisConnectionFactory {


    @Override
    public RedisConnection getConnection() {
        if (cluster == null) {
            throw new InvalidDataAccessApiUsageException("Cluster is not configured!");
        }
        return new JedisClusterConnection(cluster, clusterCommandExecutor);
    }
}
