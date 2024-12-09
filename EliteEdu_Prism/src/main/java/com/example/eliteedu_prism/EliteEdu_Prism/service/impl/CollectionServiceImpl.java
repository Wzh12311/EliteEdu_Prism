package com.example.eliteedu_prism.EliteEdu_Prism.service.impl;


import com.example.eliteedu_prism.EliteEdu_Prism.mapper.CollectionMapper;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Attachments;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Collection;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.CollectionContent;
import com.example.eliteedu_prism.EliteEdu_Prism.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    CollectionMapper collectionMapper;
    @Override
    public List<Collection> getusercollection(int userId) {

        return collectionMapper.getusercollection( userId) ;
    }

    @Override
    public List<Collection> getothercollection(int userId) {
        return collectionMapper.getothercollection( userId) ;
    }

    @Override
    public List<CollectionContent> getcontent(int collectionId) {
        return collectionMapper.getcontent( collectionId) ;
    }

    @Override
    public void addmylike(int userId, int collectionId) {
        collectionMapper.addmylike(userId,collectionId);
    }

    @Override
    public void deleteotherFavorite(int userId, Integer collectionId) {
        collectionMapper.deleteotherFavorite(userId,collectionId);
    }

    @Override
    public void likeCollection(int userId, int collectionId) {
        collectionMapper.likeCollection(userId,collectionId);
    }

    @Override
    public void addlikenumber(int collectionId) {
        collectionMapper.addlikenumber(collectionId);
    }

    @Override
    public void addNewCollection(int userId, String collectionName, String collectionDescription, int isPrivate) {
        collectionMapper.addNewCollection(userId,collectionName,collectionDescription,isPrivate);
    }

    @Override
    public void editCollection(int userId, int collectionId, String collectionName, String collectionDescription, int isPrivate) {
        collectionMapper.editCollection(userId,collectionId,collectionName,collectionDescription,isPrivate);
    }

    @Override
    public void deleteMyFavorite(int userId, int collectionId) {
        collectionMapper.deleteMyFavorite(userId,collectionId);
    }

    @Override
    public void addContent(CollectionContent collectionContent) {
         collectionMapper.addContent(collectionContent);
    }

    @Override
    public void deleteContent(CollectionContent collectionContent) {
        collectionMapper.deleteContent(collectionContent);
    }
}
