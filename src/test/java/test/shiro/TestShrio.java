package test.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
public class TestShrio {
	/*DefaultSecurityManager securityManager = new DefaultSecurityManager();
	//设置authenticator(认证)
	ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
	authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
	securityManager.setAuthenticator(authenticator);
	//设置authorizer(授权)
	ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
	authorizer.setPermissionResolver(new WildcardPermissionResolver());
	securityManager.setAuthorizer(authorizer);
	//设置Realm(域)
	DruidDataSource ds = new DruidDataSource();
	ds.setDriverClassName("com.mysql.jdbc.Driver");
	ds.setUrl("jdbc:mysql://localhost:3306/shiro");
	ds.setUsername("root");
	ds.setPassword("");
	JdbcRealm jdbcRealm = new JdbcRealm();
	jdbcRealm.setDataSource(ds);
	jdbcRealm.setPermissionsLookupEnabled(true);
	securityManager.setRealms(Arrays.asList((Realm) jdbcRealm));
	//将SecurityManager设置到SecurityUtils 方便全局使用
	SecurityUtils.setSecurityManager(securityManager);
	Subject subject = SecurityUtils.getSubject();
	UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
	subject.login(token);
	Assert.assertTrue(subject.isAuthenticated());*/
}
