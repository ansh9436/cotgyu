package net.cot_pr1.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import net.cot_pr1.domain.WebBoard;
import net.cot_pr1.domain.WebReply;

public class AdminDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	private DataSource dataSource;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	public List<WebBoard> ViewList(int start, int end, String searchOption, String keyword) {
		//검색 옵션, 키워드 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		// BETWEEN #{start}, #{end}에 입력될 값을 맵에 
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList("AdminMapper.viewlist", map);
	}
	
	
	public int countboard(String searchOption, String keyword) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		
		return sqlSession.selectOne("AdminMapper.countboard", map);
	}
	
	
	
	public List<WebReply> Viewreplylist(int start, int end, String searchOption, String keyword) {
		//검색 옵션, 키워드 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		// BETWEEN #{start}, #{end}에 입력될 값을 맵에 
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList("AdminMapper.viewreplylist", map);
	}
	
	
	public int countreply(String searchOption, String keyword) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		
		return sqlSession.selectOne("AdminMapper.countreply", map);
	}

}
