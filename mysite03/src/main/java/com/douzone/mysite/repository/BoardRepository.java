package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	public Boolean insert(BoardVo vo) {
		int count = sqlSession.insert("board.insert", vo);
			
		return count == 1;
	}
	
	public BoardVo findView(int index) {
		return sqlSession.selectOne("board.findByNo", index);
	}
	
	public int selectCnt() {
		return sqlSession.selectOne("board.findCnt");
	}
	
	public List<BoardVo> selectPage(int startPage, int onePageCnt, String kwd) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("startPage", startPage);
		parameters.put("onePageCnt", onePageCnt);
		parameters.put("kwd", kwd);

		return sqlSession.selectList("board.findPage", parameters);
	}
	
	public Boolean update(BoardVo vo) {
		int count = sqlSession.update("board.update", vo);
		
		return count == 1;
	}
	
	public Boolean updateHit(int index) {
		int count = sqlSession.update("board.updateHit", index);
		
		return count == 1;
	}
	
	public Boolean delete(BoardVo vo) {
		int count = sqlSession.delete("board.delete", vo);
		
		return count == 1;
	}

}
