package net.cot_pr1.dao;

import java.util.List;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import net.cot_pr1.domain.FreeReply;



public class FreeReplyDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	private DataSource dataSource;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<FreeReply> list(Integer bnum) {
		
		return sqlSession.selectList("FreeReplyMapper.listReply", bnum);
	}
	
	public void create(FreeReply vo) {
		sqlSession.insert("FreeReplyMapper.insertReply", vo);
		//sqlSession.update("replyMapper.setreGroup");
	}
	
	
    public void update(FreeReply vo) throws Exception {
        sqlSession.update("FreeReplyMapper.updateReply",vo);
    }
    // ¥Ò±€ ªË¡¶
   
    public void delete(Integer rnum)  {
    	sqlSession.delete("FreeReplyMapper.deleteReply", rnum);
 
    }
	public FreeReply detail(Integer rnum) {
		return sqlSession.selectOne("FreeReplyMapper.replymodifyview", rnum);
	}
	public void createcomment(FreeReply vo) {
		sqlSession.insert("FreeReplyMapper.insertReplyComment",vo);
	}

	public void stepshape(FreeReply vo){
		sqlSession.update("FreeReplyMapper.replyStepShape", vo);	
	}
	public void create_setgroup(FreeReply vo) {
		sqlSession.update("FreeReplyMapper.create_setgroup",vo);
		
	}
}