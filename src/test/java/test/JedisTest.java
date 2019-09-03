package test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	public static void main(String[] args) {
		JedisPool pool = new JedisPool("192.168.1.242", 6379);
        Jedis jedis = pool.getResource();
        //清空数据
        jedis.flushAll();
 
       //操作String
        jedis.set("site", "dataguru");
        System.out.println("redis输出:"+jedis.get("site"));
 
       //操作list
        jedis.lpush("nosql", "hbase");
        jedis.lpush("nosql", "redis");
        jedis.lpush("nosql", "mongodb");
        jedis.lpush("nosql", "couchdb");
        jedis.lpush("nosql", "neo4j");
        
        /*User user=new User();
        user.setUuid("d09fa7ec51a84b278b8ce10c0830e811");
        UserRedisImpl redis=new UserRedisImpl();*/
       
        System.out.println(jedis.lrange("nosql", 0, -1));
    }
}
