package com.evisible.os.controlcenter.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * <p>JdbcTempLate简单封装，提供一个接口</p>
 * @author TengDong
 * @Date 20160316
 *
 */
public abstract class SpringJDBC {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
}
