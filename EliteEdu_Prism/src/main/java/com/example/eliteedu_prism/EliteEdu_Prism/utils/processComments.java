package com.example.eliteedu_prism.EliteEdu_Prism.utils;

import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Comments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class processComments {

    /**
     * 构建评论树
     * @param list
     * @return
     */
    public static List<Comments> processComments(List<Comments> list) {
        Map<Integer, Comments> map = new HashMap<>();   // (id, Comment)
        List<Comments> result = new ArrayList<>();
        // 将所有根评论加入 map
        for(Comments comment : list) {
            if(comment.getParentId() == 0)
                result.add(comment);
            map.put(comment.getId(), comment);
        }
        // 子评论加入到父评论的 child 中
        for(Comments comment : list) {
            int id = comment.getParentId();
            if(id != 0) {   // 当前评论为子评论
                Comments p = map.get(id);
                if(p.getChildren() == null)    // child 为空，则创建
                    p.setChildren(new ArrayList<>());
                p.getChildren().add(comment);
            }
        }
        return result;
    }

}
