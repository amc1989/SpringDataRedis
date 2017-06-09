package com.moui.test;

import cn.securitystack.ctucommon.redispipeline.JedisClusterPipeline;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import redis.clients.jedis.JedisCluster;

/**
 * Created by zhulei on 2017/6/9.
 */
public class MyJedisClusterConnection extends JedisClusterConnection {


    public JedisCluster getJedisCluster(){

    }

    public MyJedisClusterConnection(JedisCluster cluster) {
        super(cluster);
    }

    @Override
    public void openPipeline() {
        JedisClusterPipeline pipe = JedisClusterPipeline.pipelined();
        pipe.refreshCluster();
    }
}
