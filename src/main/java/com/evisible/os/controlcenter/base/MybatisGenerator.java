package com.evisible.os.controlcenter.base;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * <p>封装mybatisSqlSessionTemplate</p>
 * @author TengDong
 * @Date   20160129
 * @param <Dao> T
 */

public class MybatisGenerator<Dao> {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	

	private Class<Dao> daoClass;

	public MybatisGenerator() {
	}

	public MybatisGenerator(final Class<Dao> daoClass) {
		this.daoClass = daoClass;
	}

	public Dao getDao() {
		//DataSourceHolder.setDataSource("system");
		return sqlSessionTemplate.getMapper(daoClass);
	}
	public Dao getDao(String dataSource) {
		//DataSourceHolder.setDataSource(dataSource);
		Dao dao = sqlSessionTemplate.getMapper(daoClass);
		return dao;
	}
	
	
	/**
	 * @return the sqlSessionTemplate
	 */
	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	/**
	 * @param sqlSessionTemplate the sqlSessionTemplate to set
	 */
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	
	
	
}
