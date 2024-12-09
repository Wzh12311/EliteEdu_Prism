package com.example.eliteedu_prism.EliteEdu_Prism.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;//0表示成功，1表示失败
    private String msg;//提示信息
    private Object data;//要返回的数据
    public static Result success() {//增删改响应成功

        return new Result(1, "success", null);
    }
    public static Result success(Object data) {//增删改响应成功

        return new Result(1, "success", data);
    }
    public static Result error(String msg) {//增删改响应成功

        return new Result(0, msg, null);
    }

}
