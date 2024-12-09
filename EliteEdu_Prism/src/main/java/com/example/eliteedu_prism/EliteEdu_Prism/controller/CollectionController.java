package com.example.eliteedu_prism.EliteEdu_Prism.controller;


import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Collection;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.CollectionContent;
import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Result;
import com.example.eliteedu_prism.EliteEdu_Prism.service.CollectionService;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.AliOSSUtils;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.GetTokenUserId;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@Slf4j
public class CollectionController {

    @Autowired
    CollectionService collectionService;
    @Autowired
    AliOSSUtils aliOSSUtils;


    /**
     * 获取用户收藏夹
     * @param request
     * @return
     */
    @GetMapping("/getusercollection")
    private Result getusercollection(HttpServletRequest request){
        String userId = GetTokenUserId.getUserId(request);
        List<Collection> collections ;
        collections=collectionService.getusercollection(Integer.parseInt(userId));

        return Result.success(collections);
    }

    /**
     * 获取其他用户收藏夹
     * @param request
     * @return
     */
    @GetMapping("/getothercollection")
    private Result getothercollection(HttpServletRequest request){
        String userId = GetTokenUserId.getUserId(request);
        List<Collection> collections ;

        collections=collectionService.getothercollection(Integer.parseInt(userId));
        return Result.success(collections);
    }

    @GetMapping("/getcontent")
    private Result getcontent(@RequestParam String collectionId){

        List<CollectionContent> list;
        list = collectionService.getcontent(Integer.parseInt(collectionId));
        System.out.println(list);
        return Result.success(list);
    }

    @GetMapping("/getOtherCollection")
    private Result getOtherCollection(@RequestParam("userId") String userId){

        List<Collection> list;
        list = collectionService.getusercollection(Integer.parseInt(userId));

        return Result.success(list);
    }

    @PostMapping("/addmylike")
    private Result addmylike(@RequestBody Map<String, Object> requestBody, HttpServletRequest request){
        String userId = GetTokenUserId.getUserId(request);
        Integer collectionId = (Integer) requestBody.get("collectionId");
        collectionService.addmylike(Integer.parseInt(userId),collectionId);
        return Result.success();
    }

    @DeleteMapping("deleteotherFavorite")
    private Result deleteotherFavorite(@RequestBody Map<String, Object> requestBody, HttpServletRequest request){
        String userId = GetTokenUserId.getUserId(request);
        Integer collectionId = (Integer) requestBody.get("collectionId");
        collectionService.deleteotherFavorite(Integer.parseInt(userId),collectionId);
        return Result.success();
    }

    @PostMapping("/likeCollection")
    private Result likeCollection(@RequestBody Map<String, Object> requestBody, HttpServletRequest request){
        String userId = GetTokenUserId.getUserId(request);
        int collectionId = (int) requestBody.get("collectionId");
        collectionService.likeCollection(Integer.parseInt(userId),collectionId);
        collectionService.addlikenumber(collectionId);

        return Result.success();
    }

    @PostMapping("/addNewCollection")
    private Result addNewCollection(@RequestBody Collection collection, HttpServletRequest request){
        String userId = GetTokenUserId.getUserId(request);

        collectionService.addNewCollection(Integer.parseInt(userId), collection.getCollectionName(),collection.getCollectionDescription(),collection.getIsPrivate());

        return Result.success();
    }


    @PutMapping("/editCollection")
    private Result editCollection(@RequestBody Collection collection, HttpServletRequest request){
        String userId = GetTokenUserId.getUserId(request);

        collectionService.editCollection(Integer.parseInt(userId), collection.getCollectionId(), collection.getCollectionName(),collection.getCollectionDescription(),collection.getIsPrivate());

        return Result.success();

    }

    @DeleteMapping("/deleteMyFavorite")
    public Result deleteMyFavorite(@RequestParam("collectionId") int collectionId ,HttpServletRequest request){
        String userId = GetTokenUserId.getUserId(request);
        collectionService.deleteMyFavorite(Integer.parseInt(userId),collectionId);

        return Result.success();
    }

    @PostMapping("/uploadDocument")
    private Result uploadDocument(@RequestParam("file") MultipartFile file,
                                  @RequestParam("title") String title,
                                  @RequestParam("description") String description,
                                  @RequestParam("contentType") String contentType,
                                  @RequestParam("collectionId") String collectionId) throws IOException {
        CollectionContent collectionContent = new CollectionContent();
        collectionContent.setCollectionId(Integer.parseInt(collectionId));
        collectionContent.setContentType(contentType);
        collectionContent.setTitle(title);
        collectionContent.setDescription(description);
        collectionContent.setContent(aliOSSUtils.upload(file));
        collectionService.addContent(collectionContent);

        return Result.success();

    }
    @PostMapping("/addContent")
    private Result addContent(@RequestBody CollectionContent collectionContent){

        collectionService.addContent(collectionContent);
        return Result.success();
    }

    @DeleteMapping("/deleteContent")
    public Result deleteContent(@RequestBody CollectionContent collectionContent) {
        collectionService.deleteContent(collectionContent);
        return Result.success();
    }




}
