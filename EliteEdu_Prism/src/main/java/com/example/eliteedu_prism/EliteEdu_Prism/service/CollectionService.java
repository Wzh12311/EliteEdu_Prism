package com.example.eliteedu_prism.EliteEdu_Prism.service;

import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Collection;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.CollectionContent;

import java.util.List;

public interface CollectionService {
    List<Collection> getusercollection(int userId);

    List<Collection> getothercollection(int userId);

    List<CollectionContent> getcontent(int collectionId);

    void addmylike(int userId, int collectionId);

    void deleteotherFavorite(int userId, Integer collectionId);

    void likeCollection(int userId, int collectionId);

    void addlikenumber(int collectionId);

    void addNewCollection(int userId, String collectionName, String collectionDescription, int isPrivate);

    void editCollection(int i, int collectionId, String collectionName, String collectionDescription, int isPrivate);

    void deleteMyFavorite(int userId, int collectionId);

    void addContent(CollectionContent collectionContent);

    void deleteContent(CollectionContent collectionContent);
}
