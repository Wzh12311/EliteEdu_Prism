package com.example.eliteedu_prism.EliteEdu_Prism.mapper;

import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Collection;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.CollectionContent;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CollectionMapper {
    @Select("select * from collection where user_id=#{userId}")
    List<Collection> getusercollection(int userId);

    /**
     * 查找关注用户的收藏夹
     *
     * @param userId
     * @return
     */
    @Select("select * from collection where collection.collection_id in (select collection_id from collection_favorites where user_id =#{userId})")
    List<Collection> getothercollection(int userId);

    @Select("select * from collection_content where collection_id=#{collectionId}")
    List<CollectionContent> getcontent(int collectionId);

    @Insert("insert into collection_favorites(user_id,collection_id) values(#{userId},#{collectionId})")
    void addmylike(int userId, int collectionId);

    @Delete("delete from collection_favorites where user_id = #{userId} and collection_id = #{collectionId}")
    void deleteotherFavorite(int userId, Integer collectionId);

    @Insert("insert into collection_like(user_id,collection_id) values(#{userId},#{collectionId})")
    void likeCollection(int userId, int collectionId);

    @Update("UPDATE collection SET likes=likes+1 WHERE collection_id=#{collectionId}")
    void addlikenumber(int collectionId);

    @Insert("insert into collection(user_id,collection_name,collection_description,is_private) values(#{userId},#{collectionName},#{collectionDescription},#{isPrivate})")
    void addNewCollection(int userId, String collectionName, String collectionDescription, int isPrivate);


    @Update("UPDATE collection SET collection_name=#{collectionName},collection_description=#{collectionDescription},is_private=#{isPrivate} WHERE collection_id=#{collectionId} and user_id=#{userId}")
    void editCollection(int userId, int collectionId, String collectionName, String collectionDescription, int isPrivate);

    @Delete("delete from collection where user_id = #{userId} and collection_id = #{collectionId}")
    void deleteMyFavorite(int userId, int collectionId);

    @Insert("insert into collection_content(collection_id, content_type, title, description, content) values(#{collectionId}, #{contentType}, #{title}, #{description}, #{content})")
    void addContent(CollectionContent collectionContent);

    @Delete("delete from collection_content where content_id = #{contentId} and collection_id = #{collectionId}")
    void deleteContent(CollectionContent collectionContent);
}

