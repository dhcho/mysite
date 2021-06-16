package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GalleryVo;

@Repository
public class GalleryRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public Boolean insert(GalleryVo vo) {
		int count = sqlSession.insert("gallery.insert", vo);
		
		return count == 1;
	}
	
	public List<GalleryVo> findAll() {
		return sqlSession.selectList("gallery.findAll");
	}
	
	public Boolean delete(GalleryVo vo) {
		int count = sqlSession.delete("gallery.delete", vo);
		
		return count == 1;
	}
}
