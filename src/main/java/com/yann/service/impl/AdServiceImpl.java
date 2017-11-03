package com.yann.service.impl;

import com.yann.dao.AdDao;
import com.yann.dto.AdDto;
import com.yann.entity.Ad;
import com.yann.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class AdServiceImpl implements AdService{

    @Autowired
    private AdDao adDao;

    @Value("${adImage.savePath}")
    private String adImageSavePath;
    public boolean add(AdDto adDto) {
        Ad ad = new Ad();
        ad.setTitle(adDto.getTitle());
        ad.setLink(adDto.getLink());
        ad.setWeight(adDto.getWeight());
        if( adDto.getImgFile() != null && adDto.getImgFile().getSize()>0) {
            String fileName = System.currentTimeMillis()+"_"+adDto.getImgFile().getOriginalFilename();
            File file = new File(adImageSavePath+fileName);
            File fileFolder = new File(adImageSavePath);
            if(!fileFolder.exists()){
                fileFolder.mkdirs();
            }
            try {
                adDto.getImgFile().transferTo(file);
                ad.setImgFileName(fileName);
                adDao.insert(ad);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }else {
            return false;
        }
    }

    public List<AdDto> searchByPage(AdDto adDto) {
        return null;
    }

    public boolean remove(Long id) {
        return false;
    }

    public AdDto getById(Long id) {
        return null;
    }

    public boolean modify(AdDto adDto) {
        return false;
    }
}
