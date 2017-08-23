package net.cot_pr1.dao;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import net.cot_pr1.domain.User;

	@Repository
	public class UserDao {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private DataSource dataSource;

	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	public User findByID(String userId) {
		return sqlSession.selectOne("UserMapper.findByID",userId);
	}

	public void create(User user) {
		sqlSession.insert("UserMapper.create",user);
	}

	public void update(User user) {
		sqlSession.update("UserMapper.update",user);
	}
	
	public void imgmodify(User vo) {
		sqlSession.update("UserMapper.imgupdate",vo);		
	}
	
	public String findByprofile(String userId) {
		return sqlSession.selectOne("UserMapper.findByprofile", userId);
	}
	
	public int checkId(User vo) {
		return sqlSession.selectOne("UserMapper.checkId", vo);
	}

}