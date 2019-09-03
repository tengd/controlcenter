package com.evisible.os.controlcenter.system.shiro;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.evisible.os.controlcenter.system.entity.SPermission;
import com.evisible.os.controlcenter.system.entity.SRole;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.system.redis.impl.UserRedisImpl;
import com.evisible.os.controlcenter.system.service.ISRoleService;
import com.evisible.os.controlcenter.system.service.impl.PermissionService;
import com.evisible.os.controlcenter.system.service.impl.UserService;


/**
 * <p>互联网+Shiro域</p>
 * <p>Shiro从从Realm获取安全数据（如用户、角色、权限）</p>
 * @author TengDong
 * @Date 20160317
 */
public class UserRealm extends AuthorizingRealm {
	private UserService userService;
	private UserRedisImpl userRedis;
	private ISRoleService sroleService;
	private PermissionService permissionService;

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @param userRedis the userRedis to set
	 */
	public void setUserRedis(UserRedisImpl userRedis) {
		this.userRedis = userRedis;
	}
	/**
	 * @param sroleService the sroleService to set
	 */
	public void setSroleService(ISRoleService sroleService) {
		this.sroleService = sroleService;
	}


	/**
	 * @param permissionService the permissionService to set
	 */
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	/** 
	    * 表示根据用户身份获取授权信息。
	     * 为当前登录的Subject授予角色和权限 
	     * @see  经测试:本例中该方法的调用时机为需授权资源被访问时 
	     * @see  经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache 
	     * @see  个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持,则可灵活决定是否启用AuthorizationCache 
	     * @see  比如说这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache 
	     * @param PrincipalCollection principals验证身份
     */  
	@SuppressWarnings({ "null", "unused", "unchecked", "rawtypes" })
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		  //获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()  
        String currentUsername = (String)super.getAvailablePrincipal(principals);  
        User user=new User();
        user.setuName(currentUsername);
        //获取用户信息
        User userInfo=userService.getUser(user);
         //用户查询角色
        List<SRole> roles=null;
        Collection<String> collecntionRole=null;
        if(userInfo!=null){
        	roles=sroleService.getRolesByUser(userInfo.getuName());
        	for(SRole role:roles){
        		collecntionRole.add(role.getUuid());
        	}
        }
        List<SPermission> permissions=null;
        Collection<String> collecntionPermission=null;
        if(collecntionRole!=null){
        	permissions=permissionService.getPermissionsByRoleId(((List)collecntionRole));
        	for(SPermission permission:permissions){
        		collecntionPermission.add(permission.getPermission());
        	}
        }
        //当前用户设置角色与权限
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        simpleAuthorInfo.addRoles(collecntionRole);
        simpleAuthorInfo.addStringPermissions(collecntionPermission);
        return simpleAuthorInfo;
	}
	/** 
	 * 表示获取身份验证信息；
     * 验证当前登录的Subject 
     * @see  经测试:本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时 
     */ 
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
	   //获取基于用户名和密码的令牌  
       //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的  
        //两个token的引用都是一样的
        String account = (String)authcToken.getPrincipal();  
        String password=new String((char[])authcToken.getCredentials());
        System.out.println("验证当前Subject时获取到token为" + ReflectionToStringBuilder.toString(authcToken, ToStringStyle.MULTI_LINE_STYLE));  
	    User userToken=new User();
	    userToken.setAccount(account);
        userToken.setPas(password);
        User user = userService.getUser(userToken);
	      if(null != user){  
	          AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getAccount(), user.getPas(),this.getName());  
	          this.setSession("currentUser", user);  
	         /* userRedis.put(user);*/
	          return authcInfo;  
	      }else{  
	          return null;  
	      }  
	}
	   
    /** 
     * 将一些数据放到ShiroSession中,以便于其它地方使用 
     * @see  比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到 
     */  
    private void setSession(Object key, Object value){  
        Subject currentUser = SecurityUtils.getSubject();  
        if(null != currentUser){  
            Session session = currentUser.getSession();  
            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");  
            if(null != session){  
                session.setAttribute(key, value);  
            }  
        }  
    }  
    

}
