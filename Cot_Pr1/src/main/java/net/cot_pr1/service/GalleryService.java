package net.cot_pr1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cot_pr1.dao.GalleryDao;
import net.cot_pr1.domain.Gallery;
@Service
public class GalleryService {
	@Autowired
	GalleryDao galleryDao;
	public int countboard(String searchOption, String keyword) {
		return galleryDao.countboard(searchOption, keyword);
	}

	public List<Gallery> viewimglist(int start, int end, String searchOption, String keyword) {
		return galleryDao.viewimglist(start, end, searchOption, keyword);
	}

	public void imageinsert(Gallery vo) {
		galleryDao.imageinsert(vo);	
	}

	public List<Gallery> uplist(int start, int end, String searchOption, String keyword) {
		return galleryDao.uplist(start, end, searchOption, keyword);
	}

	public void imagedelete(Integer imgid) {
		galleryDao.imagedelete(imgid);
	}

	public void imageup(Integer imgid) {
		galleryDao.imageup(imgid);
	}

}
