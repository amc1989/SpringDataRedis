package com.moui.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MOTUI on 2016/10/23.
 * <p>
 * 集群测试
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-redis-cluster.xml")
public class TestRedisClusterTemplate {

    @Autowired
    private RedisTemplate redisTemplate;



    @PostConstruct
    public void test() {
        System.out.println(redisTemplate);
    }

    /**
     * key-value 存储
     */
    @Test
    public void test01() {
        ValueOperations operations = redisTemplate.opsForValue();


//        operations.increment("b",3L);
//        System.err.println("b   :"+operations.get("b"));
//        SetOperations setOperations = redisTemplate.opsForSet();
//        final String serr = "a,b,c,e,f,f,f";
//        Long k = setOperations.add("h", serr.split(","));
//        System.err.println("k    长度:"+k);
//        Set k1 = setOperations.members("k");
//        k1.add("g");
//        k1.add("z");
//        k1.add("g");
//        String[] result = (String[]) k1.toArray(new String[0]);
//        Long k2 = setOperations.add("h", result);
//        System.err.println("k2   的长度 ："+k2);
//        Set j = setOperations.members("j");
//        j.size();
//
//        for(Object vv: j){
//            System.err.println(vv);
//        }
        /**
         * 操你妹的redistemplate不支持pipeline 坑爹！！！！！！！！！！！
         */
        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        RedisClusterConnection clusterConnection = connectionFactory.getClusterConnection();
        clusterConnection.openPipeline();
        System.err.println("Pipeline   :"+clusterConnection.isPipelined());
        final Map<String, Long> stringObjectMap = new HashMap<String, Long>();
        stringObjectMap.put("aa", 20L);
        stringObjectMap.put("AC", 30L);
        for (Map.Entry<String, Long> me : stringObjectMap.entrySet()) {
            String key = me.getKey();
            Long value = me.getValue();
            clusterConnection.incrBy(key.getBytes(), value);
        }
        clusterConnection.closePipeline();

       /* List list = redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                for (Map.Entry<String, Long> me : stringObjectMap.entrySet()) {
                    String key = me.getKey();
                    Long value = me.getValue();
                    connection.incrBy(key.getBytes(), value);
                }
                connection.closePipeline();
                return null;
            }
        });*/


    }


}
