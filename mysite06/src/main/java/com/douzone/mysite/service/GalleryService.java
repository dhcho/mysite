package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GalleryRepository;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	@Autowired
	GalleryRepository galleryRepository;
	
	public void uploadGallery(GalleryVo vo) {
		galleryRepository.insert(vo);
	}
	
	public List<GalleryVo> getGalleryList() {
		return galleryRepository.findAll();
	}
	
	public void deleteGallery(Long no) {
		GalleryVo vo = new GalleryVo();
		vo.setNo(no);
		
		galleryRepository.delete(vo);
	}
	
}
