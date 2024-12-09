package com.example.eliteedu_prism.EliteEdu_Prism.controller;


import com.example.eliteedu_prism.EliteEdu_Prism.pojo.*;
import com.example.eliteedu_prism.EliteEdu_Prism.service.CourseService;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.AliOSSUtils;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.GetTokenUserId;
import com.example.eliteedu_prism.EliteEdu_Prism.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@Slf4j
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    AliOSSUtils aliOSSUtils;

    /**
     * 获取课程列表（需要根据token确定是谁的课程列表）
     * @param request
     * @return
     */
    @GetMapping("/courses")
    public Result getCourse(HttpServletRequest request){
        List<Course> courses;
        try {
            String userId = GetTokenUserId.getUserId(request);

            String role=courseService.getrole(userId);
            if(role.equals("teacher")){
                courses=courseService.getCourseList(userId);

                Map<String, Object> data = new HashMap<>();
                data.put("role", role);
                data.put("courses", courses);
                return Result.success(data);
            }
            else {

                courses=courseService.getCourseList2(userId);
                Map<String, Object> data = new HashMap<>();
                data.put("role", role);
                data.put("courses", courses);
                return Result.success(data);
            }





        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("Internal server error");
        }

    }

    /**
     * 获取单个课程信息
     * @param request
     * @param courseId
     * @param teacherId
     * @return
     */
    @GetMapping("/getspcourse/{courseId}/teacher/{teacherId}")
    public Result getCourseInfo(HttpServletRequest request, @PathVariable("courseId")int courseId,  @PathVariable("teacherId")int teacherId){

        Course course=courseService.getspcourse(courseId,teacherId);




        return Result.success(course);
    }

    /**
     * 更新课程信息（只有老师可以），request没啥用，用不到token
     * @param request
     * @param courseId
     * @param teacherId
     * @param courseData
     * @return
     */
    @PostMapping("/course/{courseId}/teacher/{teacherId}")
    public Result updateCourseInfo(HttpServletRequest request, @PathVariable("courseId")int courseId,  @PathVariable("teacherId")int teacherId,@RequestBody Course courseData){


        courseService.updateCourseInfo(courseId,teacherId,courseData);


        return Result.success();
    }

    /**
     * 获取课件列表
     * @param request
     * @param courseId
     * @param teacherId
     * @return
     */
    @GetMapping("/coursewares/course/{courseId}/teacher/{teacherId}")
    public Result getCoursewares(HttpServletRequest request,@PathVariable("courseId") int courseId,@PathVariable("teacherId") int teacherId){

        List<CourseResource> coursewares=courseService.getCoursewares(courseId,teacherId);


        return Result.success(coursewares);
    }

    @GetMapping("/exam/course/{courseId}/teacher/{teacherId}")
    public Result getCourseexam(HttpServletRequest request,@PathVariable("courseId") int courseId,@PathVariable("teacherId") int teacherId){

        List<CourseResource> coursewares=courseService.getCourseexam(courseId,teacherId);

        return Result.success(coursewares);
    }


    @GetMapping("/exercise/course/{courseId}/teacher/{teacherId}")
    public Result getCourseexercise(HttpServletRequest request,@PathVariable("courseId") int courseId,@PathVariable("teacherId") int teacherId){

        List<CourseResource> coursewares=courseService.getCourseexercise(courseId,teacherId);

        return Result.success(coursewares);
    }


    /**
     * 上传课件
     * @param request
     * @param courseId
     * @param teacherId
     * @param file
     * @return
     * @throws IOException
     */

    @PostMapping("/coursewares/upload/course/{courseId}/teacher/{teacherId}")
    public Result uploadCourseware(HttpServletRequest request,@PathVariable("courseId") int courseId,@PathVariable("teacherId") int teacherId,@RequestPart("file") MultipartFile file ,@RequestParam("name") String resourceDescription) throws IOException {
        String url = aliOSSUtils.upload(file);

        courseService.uploadCourseware(courseId,teacherId,url,resourceDescription);

        return Result.success();
    }

    @PostMapping("/exam/upload/course/{courseId}/teacher/{teacherId}")
    public Result uploadCourseexam(HttpServletRequest request,@PathVariable("courseId") int courseId,@PathVariable("teacherId") int teacherId,@RequestPart("file") MultipartFile file ,@RequestParam("name") String resourceDescription) throws IOException {
        String url = aliOSSUtils.upload(file);

        courseService.uploadCourseexam(courseId,teacherId,url,resourceDescription);

        return Result.success();
    }

    @PostMapping("/exercise/upload/course/{courseId}/teacher/{teacherId}")
    public Result uploadCourseexercise(HttpServletRequest request,@PathVariable("courseId") int courseId,@PathVariable("teacherId") int teacherId,@RequestPart("file") MultipartFile file ,@RequestParam("name") String resourceDescription) throws IOException {
        String url = aliOSSUtils.upload(file);

        courseService.uploadCourseexercise(courseId,teacherId,url,resourceDescription);

        return Result.success();
    }


    @DeleteMapping("/coursewares/delete/course/{courseId}/teacher/{teacherId}/resource/{resourceId}")
    public Result deleteCourseware(HttpServletRequest request,@PathVariable("courseId") int courseId,@PathVariable("teacherId") int teacherId,@PathVariable("resourceId") int resourceId){

        courseService.deleteCourseware(courseId,teacherId,resourceId);
        return Result.success();

    }

    @PostMapping("/updateteacher/{courseId}/teacher/{teacherId}")
    public Result updateTeacher(HttpServletRequest request,@PathVariable("courseId") int courseId,@PathVariable("teacherId") int teacherId,@RequestBody User courseData){

        String profilePic = courseData.getProfilePic();
        courseService.updateTeacher(teacherId,profilePic);

        return Result.success();
    }


    @GetMapping("/getCollection")
    public Result getCollection(HttpServletRequest request){
        String userId = GetTokenUserId.getUserId(request);

        List<Collection> collection = courseService.getCollection(userId);

        return Result.success(collection);

    }

    @PostMapping("addFavorite")
    public Result addFavorite(HttpServletRequest request,@RequestParam("resourceId") int resourceId,@RequestParam("collectionId") int collectionId){
        String userId = GetTokenUserId.getUserId(request);
//        System.out.println(resourceId);
//        System.out.println(collectionId);
//        System.out.println(userId);
        //todo 将资源添加到收藏夹
        CourseResource courseResource =courseService.getResource(resourceId);
        String resourceDescription = courseResource.getResourceDescription();
        String resourceUrl = courseResource.getResourceUrl();
        courseService.addFavorite(collectionId,resourceDescription,resourceUrl);

        return Result.success();
    }


    @PostMapping("getTeacherInfo")
    public Result getTeacherInfo(HttpServletRequest request,@RequestBody Map<String,String> data){
        String userId = GetTokenUserId.getUserId(request);
        String courseId = data.get("courseId");
        String teacherId = data.get("teacherId");

        User user = courseService.getTeacherInfo(userId,courseId,teacherId);


        return Result.success(user);
    }


    @GetMapping("getCourseSyllabusInfo")
    public Result getCourseSyllabusInfo(HttpServletRequest request,@RequestBody Map<String,String> data){
        String courseId = data.get("courseId");
        String teacherId = data.get("teacherId");

        String userId = GetTokenUserId.getUserId(request);
        Course course = courseService.getCourseSyllabusInfo(userId,courseId,teacherId);

        return Result.success(course);

    }

    @PostMapping("updateOutline/{courseId}")
    public Result updateOutline(HttpServletRequest request,@PathVariable("courseId") int courseId,@RequestBody Course courseData){

        courseService.updateOutline(courseId,courseData);

        return Result.success();
    }


}
