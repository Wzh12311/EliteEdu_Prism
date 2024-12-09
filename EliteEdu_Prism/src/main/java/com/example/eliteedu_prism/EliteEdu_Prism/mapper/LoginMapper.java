package com.example.eliteedu_prism.EliteEdu_Prism.mapper;


import com.example.eliteedu_prism.EliteEdu_Prism.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {
     @Select("select * from user where identification_number=#{identificationNumber} and password=#{password}")
      User login(User user);



}
