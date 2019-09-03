package com.evisible.os.controlcenter.system.redis.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import com.evisible.os.controlcenter.base.RedisGenerator;
import com.evisible.os.controlcenter.model.Message;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.system.redis.IUserRedis;
import com.evisible.os.controlcenter.util.constant.MsgConstant;



/**
 * <p>Redis实现
 * 用户信息缓存Dao接口</p>
 * @author TengDong
 * @Date 20160202
 */
@Service("userRedis")
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class UserRedisImpl extends RedisGenerator<String,User> implements IUserRedis {
	private static Logger log = LoggerFactory.getLogger(UserRedisImpl.class);  
	/**
	 * @param user 用户实体
	 * @return Message 消息
	 */

	public Message put(final User user){
				 HashOperations ops=redisTemplate.opsForHash();
				 ops.put(user.getUuid(),  user.getUuid(),user);
				 Message mes=new Message();
				 if(ops==null){
					 mes.setmsgCode(MsgConstant.REDISERRORCODE);
					 mes.setmsgContent(MsgConstant.REDISERRORCONTENT);
				 }
				 mes.setmsgCode(MsgConstant.REDISERRORCODE);
				 mes.setmsgContent(MsgConstant.REDISERRORCONTENT);
			return mes;
	}
	/**
	 * @param key User对象
	 * @return Message 消息
	 */
	public Message delete(User key){
		HashOperations ops=redisTemplate.opsForHash();
		ops.delete(key.getUuid(),key);
		 Message mes=new Message();
		
		return mes;
	}
	
	/**
	 * @param user 更新对象
	 * @return Message消息
	 */
	public Message update(final User user){
		HashOperations ops=redisTemplate.opsForHash();
		ops.put(user.getUuid(),  user.getUuid(),user);
		Message mes=new Message();
		return mes;
	}
	
	/**
	 * @param key User对象
	 * @return 返回用户实体
	 */
	public User get(final User key){
		HashOperations ops=redisTemplate.opsForHash();
		ops.get(key.getUuid(),key);
		return (User)ops;
	}
	


}
