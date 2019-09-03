package com.evisible.os.controlcenter.system.controller.soa;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.evisible.os.controlcenter.model.vo.Role;
import com.evisible.os.controlcenter.model.vo.UserRole;
import com.evisible.os.controlcenter.system.controller.base.BaseController;
import com.evisible.os.controlcenter.system.entity.SRole;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.system.service.ISRoleService;
import com.evisible.os.controlcenter.util.RequestUtil;
import com.evisible.os.controlcenter.util.ResponseUtil;
import com.evisible.os.controlcenter.util.VerifyCodeUtil;
import com.evisible.os.controlcenter.util.MD5.MD5;
import com.evisible.os.controlcenter.util.constant.ComErrorCodeConstants;




/**
 * <p>对应登录界面</p>
 * @author TengDong
 *  @Date 20160322
 */
@Controller("loginAppController")
@RequestMapping("loginApp")
public class LoginController extends BaseController{
		@Autowired
		private ISRoleService sroleService;
		
		private Session session = null;
		/** 
	     * 获取验证码图片和文本(验证码文本会保存在HttpSession中) 
	     */  
	    @RequestMapping("/getValidateCodeImage")  
	    public void getVerifyCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {  
	        //设置页面不缓存  
	        response.setHeader("Pragma", "no-cache");  
	        response.setHeader("Cache-Control", "no-cache");  
	        response.setDateHeader("Expires", 0);  
	        String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_ONLY, 4, null);  
	        
	        System.out.println("本次生成的验证码为[" + verifyCode + "],已存放到HttpSession中");  
	        
	      //将验证码放到HttpSession里面  
	        Subject currentVerifyCode = SecurityUtils.getSubject();  
	        if(null != currentVerifyCode){  
	             session = currentVerifyCode.getSession();  
	            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");  
	            if(null != session){  
	                session.setAttribute("verifyCode", verifyCode);  
	            }  
	        }  
	        System.out.println("已存放到HttpSession中的验证码为[" + currentVerifyCode.getSession().getAttribute("verifyCode") + ",session ID 为"+currentVerifyCode.getSession().getId()+"]");  
	        
	        
	        //设置输出的内容的类型为JPEG图像  
	        response.setContentType("image/jpeg");  
	        BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 90, 30, 3, true, Color.WHITE, Color.BLACK, null);  
	        //写给浏览器  
	        ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());  
	    }  
	      
	    /** 
	     * 用户登录 
	     */  
	    @ResponseBody
	    @RequestMapping(value="/loingPass", method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")  
	    public String login(HttpServletRequest request,
				HttpServletResponse response){  
	    	try{
	    		JSONObject validateObj = RequestUtil.ValidateToken(request);
	    		if (validateObj == null|| validateObj.getJSONObject("MobileHead") == null|| 
	    				validateObj.getJSONObject("MobileHead").getString("Code").equals("-1")) {
					return validateObj.toString();
				}
	    		// 验证传递的参数是否为空
				String params = validateObj.getJSONObject("MobileHead").getString("Message");
				JSONObject paramJson = JSONObject.fromObject(params);
				//传参数
				ArrayList<String> paramNames = new ArrayList<String>();
				paramNames.add("username");
				paramNames.add("password");
				paramNames.add("verifyCode");
				JSONObject pValidateObj = RequestUtil.ValidateParams(paramJson,
						paramNames);
				if (pValidateObj == null|| pValidateObj.getString("Code").equals("-1")) {
					return ResponseUtil.creComErrorResult(
							ComErrorCodeConstants.ErrorCode.Params_EMPTY_ERROR
									.getErrorCode(), pValidateObj
									.getString("Message"));
				}
				//取参数
				String username=paramJson.getString("username").trim();
				MD5 md5Password = new MD5(paramJson.getString("password").trim());
		        String password=md5Password.mac32();
				String submitCode=paramJson.getString("verifyCode").trim();
				
				System.out.println(session.getId());
				
				String verifyCode = (String)session.getAttribute("verifyCode");
		    	System.out.println("A:"+submitCode+" B:"+verifyCode);
				//验证码是否相等
				if(!submitCode.equals(verifyCode)){
					
					return ResponseUtil.creComErrorResult(ComErrorCodeConstants.ErrorCode.VerifyCodeError.getErrorCode(), "验证码错误");
				}
				
				UsernamePasswordToken token = new UsernamePasswordToken(username, password);  
		        token.setRememberMe(true); 
		        //获取客户端IP
		        String ip = request.getHeader("x-forwarded-for");  
		        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
		            ip = request.getHeader("Proxy-Client-IP");     
		        }  
		        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
		            ip = request.getHeader("WL-Proxy-Client-IP");     
		         }     
		        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
		             ip = request.getRemoteAddr();     
		        }     
		        token.setHost(ip);
		        System.out.println("为了验证登录用户而封装的token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));  
		        //获取当前的Subject  
		        Subject currentUser = SecurityUtils.getSubject();  
		        try {  
		            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查  
		            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应  
		            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法  
		            System.out.println("对用户[" + username + "]进行登录验证..验证开始");  
		            currentUser.login(token);  
		            System.out.println("对用户[" + username + "]进行登录验证..验证通过");  
		        }catch(UnknownAccountException uae){  
		            System.out.println("对用户[" + username + "]进行登录验证..验证未通过,未知账户");  
		            return ResponseUtil.creComErrorResult(ComErrorCodeConstants.ErrorCode.UnknownAccount.getErrorCode(), "未知账户");
		        }catch(IncorrectCredentialsException ice){  
		            System.out.println("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");  
		            return ResponseUtil.creComErrorResult(ComErrorCodeConstants.ErrorCode.PasswordError.getErrorCode(), "密码错误");
		        }catch(LockedAccountException lae){  
		            System.out.println("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");  
		            return ResponseUtil.creComErrorResult(ComErrorCodeConstants.ErrorCode.AccountLocked.getErrorCode(), "账户已锁定");
		        }catch(ExcessiveAttemptsException eae){  
		            System.out.println("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");  
		            return ResponseUtil.creComErrorResult(ComErrorCodeConstants.ErrorCode.UserNameOrPasswordValidateNumberError.getErrorCode(), "用户名或密码错误次数过多");
		        }catch(AuthenticationException ae){  
		            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景  
		            System.out.println("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
		            return ResponseUtil.creComErrorResult(ComErrorCodeConstants.ErrorCode.UserNamePasswordError.getErrorCode(), "用户名密码错误");
		        }  
		        //验证是否登录成功  
		        if(currentUser.isAuthenticated()){  
		            System.out.println("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");  
		            System.out.println("[TOKEN信息为："+token+"]");  
		            //放session
		          //将验证码放到HttpSession里面  
			        Subject currentToken = SecurityUtils.getSubject();  
			        if(null != currentToken){  
			            Session session = currentToken.getSession();  
			            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");  
			            if(null != session){  
			                session.setAttribute("token",new MD5(token).mac32());  
			            }  
			        }
			        System.out.println("已存放到HttpSession中的TOKEN为[" + currentToken.getSession().getAttribute("token") + "]");  
		        }else{  
		            token.clear();  
		        }  
        
				//获得用户角色
		        UserRole ur=new UserRole();
		        ur.setToken(new MD5(token).mac32());
		        // 当前用户
				User user = (User) request.getSession().getAttribute("currentUser");
		        ur.setUserId(user.getUuid());
		        
				//角色
		        List<SRole> sr=sroleService.getRolesByUser(user.getUuid());
		        
		        List<Role> roles=new ArrayList<Role>();
				for(SRole srList:sr){
					Role role=new Role();
					role.setRoleId(srList.getUuid());
					role.setRname(srList.getrName());
					role.setRemark(srList.getRemark());
					
					roles.add(role);
				}
				ur.setRoles(roles);
				
				return this.ajax(response, ResponseUtil.creObjSucResult(ur), "application/json; charset=utf-8");
				
	    	}catch(Exception e){
	    		logger.error("登录（EngineerLogin）异常信息：" + e.getMessage());
				return ResponseUtil.creComErrorResult(
						ComErrorCodeConstants.ErrorCode.User_Code_Exist
								.getErrorCode(), "服务器请求错误");
	    	}
	    		        
	      
	    }  
	       
	       
	    /** 
	     * 用户登出 
	     */  
	    @RequestMapping("/logout")  
	    public String logout(HttpServletRequest request){  
	         SecurityUtils.getSubject().logout();  
	         return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";  
	    }  
}
