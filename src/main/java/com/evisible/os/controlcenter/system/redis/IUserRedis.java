package com.evisible.os.controlcenter.system.redis;

import org.springframework.stereotype.Service;

import com.evisible.os.controlcenter.model.Message;
import com.evisible.os.controlcenter.system.entity.User;


/**
 * <p>Redis实现
 * 用户信息缓存Dao接口</p>
 * @author TengDong
 *@Date 20160202
 */
@Service
public interface IUserRedis {

	/**
	 * @param User 用户实体
	 * @return Message 消息
	 */
	public Message put(final User user);
	
	/**
	 * @param key User对象
	 * @return Message 消息
	 */
	public Message delete(User key);
	
	/**
	 * @param user 更新对象
	 * @return Message消息
	 */
	public Message update(final User user);
	
	/**
	 * @param key User对象
	 * @return 返回用户实体
	 */
	public User get(final User key);
}
