package com.evisible.os.controlcenter.system.controller;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.evisible.os.controlcenter.system.controller.base.BaseController;
import com.evisible.os.controlcenter.system.entity.SRole;
import com.evisible.os.controlcenter.system.entity.User;
import com.evisible.os.controlcenter.system.service.IFunService;
import com.evisible.os.controlcenter.system.service.ISRoleService;
import com.evisible.os.controlcenter.util.VerifyCodeUtil;
import com.evisible.os.controlcenter.util.MD5.MD5;



/**
 * <p>对应登录界面</p>
 * @author TengDong
 *  @Date 20160322
 */
@Controller
public class LoginController extends BaseController{
		private static Logger log = LoggerFactory.getLogger(LoginController.class);
		@Autowired
		IFunService funService;
		@Autowired
		ISRoleService roleService;
		/**
		 * 跳转到登陆页面
		 * @return
		 */
		@RequestMapping("/to_login")
		public String toLogin(){
			return "login";
		}
		
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
	        //将验证码放到HttpSession里面 
	        request.getSession().setAttribute("verifyCode", verifyCode);  
	        System.out.println("本次生成的验证码为[" + verifyCode + "],已存放到HttpSession中");  
	        //设置输出的内容的类型为JPEG图像  
	        response.setContentType("image/jpeg");  
	        BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 90, 30, 3, true, Color.WHITE, Color.BLACK, null);  
	        //写给浏览器  
	        ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());  
	    }  
	      
	    /** 
	     * 账户登录 
	     */  
	    @RequestMapping(value="/loginUrl", method=RequestMethod.POST)  
	    public String login(HttpServletRequest request){  
	        String resultPageURL = InternalResourceViewResolver.REDIRECT_URL_PREFIX+ "/to_login";  
	        String account = request.getParameter("account").toString().trim();
	        MD5 md5Password = new MD5(request.getParameter("password").toString().trim());
	        String password=md5Password.mac32();
	        //获取HttpSession中的验证码  
	        String verifyCode = (String)request.getSession().getAttribute("verifyCode");  
	        //获取账户请求表单中输入的验证码  
	        String submitCode = WebUtils.getCleanParam(request, "verifyCode");  
	        
	        System.out.println("账户[" + account + "]登录时输入的验证码为[" + submitCode + "],HttpSession中的验证码为[" + verifyCode + "]");  
	        if (StringUtils.isEmpty(submitCode) || !StringUtils.equals(verifyCode, submitCode.toLowerCase())){  
	            request.setAttribute("message_login", "验证码不正确");  
	            return resultPageURL;  
	        }  
	        UsernamePasswordToken token = new UsernamePasswordToken(account, password);  
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
	        System.out.println("为了验证登录账户而封装的token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));  
	        //获取当前的Subject  
	        Subject currentUser = SecurityUtils.getSubject();
	        try {  
	            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查  
	            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应  
	            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法  
	            System.out.println("对账号[" + account + "]进行登录验证..验证开始");  
	            currentUser.login(token);
	           //日志
				try {
					this.Log(request, "", account+"登录");
				} catch (Exception e) {
					log.debug("------日志处理异常---------"+e.getMessage());
				}
	            System.out.println("对账户[" + account + "]进行登录验证..验证开始");  
	            System.out.println("对账户[" + account + "]进行登录验证..验证通过");  
	            resultPageURL = "main";  
	        }catch(UnknownAccountException uae){  
	            System.out.println("对账户[" + account + "]进行登录验证..验证未通过,未知账户");  
	            request.setAttribute("message_login", "未知账户");  
	        }catch(IncorrectCredentialsException ice){  
	            System.out.println("对账户[" + account + "]进行登录验证..验证未通过,错误的凭证");  
	            request.setAttribute("message_login", "密码不正确");  
	        }catch(LockedAccountException lae){  
	            System.out.println("对账户[" + account + "]进行登录验证..验证未通过,账户已锁定");  
	            request.setAttribute("message_login", "账户已锁定");  
	        }catch(ExcessiveAttemptsException eae){  
	            System.out.println("对账户[" + account + "]进行登录验证..验证未通过,错误次数过多");  
	            request.setAttribute("message_login", "账户名或密码错误次数过多");  
	        }catch(AuthenticationException ae){  
	            //通过处理Shiro的运行时AuthenticationException就可以控制账户登录失败或密码错误时的情景  
	            System.out.println("对账户[" + account + "]进行登录验证..验证未通过,堆栈轨迹如下");  
	            ae.printStackTrace();  
	            request.setAttribute("message_login", "账户名或密码不正确");  
	        }  
	        //验证是否登录成功  
	        if(currentUser.isAuthenticated()){  
	            System.out.println("账户[" + account + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");  
	            System.out.println("[TOKEN信息为："+token+"]");  
	            //登用户验证通过之后将该用户按照功能拥有的权限放入Session中
				User user = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
				String userId = user.getUuid();
				List<SRole> roleList = roleService.getRolesByUser(userId);
				Map<String , Set<String>> permissionMap = funService.getFunPermissionsByUserId(userId);
				//将权限放入Session
	            SecurityUtils.getSubject().getSession().setAttribute("permissions", permissionMap);
	            //将role放入session
	            SecurityUtils.getSubject().getSession().setAttribute("roles", roleList);
	            
	        }else{  
	            token.clear();  
	        }  
	        return resultPageURL;  
	    }  
	       
	       
	    /** 
	     * 账户登出 
	     */  
	    @RequestMapping("/logout")  
	    public String logout(HttpServletRequest request){
	    	 // 当前用户
	    	 User user = (User) request.getSession().getAttribute("currentUser");
	    	 //日志
			 try {
				this.Log(request, "", user.getuName()+"登出");
			 } catch (Exception e) {
				log.debug("------日志处理异常---------"+e.getMessage());
			 }
	    	 SecurityUtils.getSubject().logout();
	         return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";  
	    }  
}
