package com.somecoder.demo.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.Student;
import com.somecoder.demo.blog.entity.request.*;
import com.somecoder.demo.blog.entity.response.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
public interface IStudentService extends IService<Student> {
    /**
     * 显示待审核学生账号
     *
     * @param userId 用户主键
     * @param showReviewStudentRequest 显示待审核学生列表请求
     * @return Page<ShowReviewStudentResponse> 显示待审核学生列表响应
     * @author lishan
     * @since 2021-01-28
     */
    Page<ShowReviewStudentResponse> showReviewStudent(String userId, ShowReviewStudentRequest showReviewStudentRequest);

    /**
     * 展示学生信息
     *
     * @param userId 用户主键
     * @return ShowInformationResponse 获取学生信息响应
     * @author liangkeyu
     * @since 2021-01-28
     */
    ShowInformationResponse showInformation(String userId);

    /**
     * 学生列表
     *
     * @param studentListRequest 学生列表请求
     * @return Page<StudentListResponse> 学生列表分页
     * @author liangkeyu
     * @since 2021-01-28
     */
    Page<StudentListResponse> getStudentList(StudentListRequest studentListRequest);

    /**
     * 修改学生信息
     *
     * @param userId 用户主键
     * @param changeInformationRequest 修改学生信息请求
     * @return ChangeInformationResponse 修改学生信息响应
     * @author liangkeyu
     * @since 2021-01-28
     */
    ChangeInformationResponse changeInformation(String userId, ChangeInformationRequest changeInformationRequest);

    /**
     * 修改密码
     *
     * @param userId 用户主键
     * @param changePasswordRequest 修改密码请求
     * @author liangkeyu
     * @since 2021-01-28
     */
    void changePassword(String userId, ChangePasswordRequest changePasswordRequest);

    /**
     * 审核学生账号
     *
     * @param reviewStudentAccountsRequest 审核学生账号请求
     * @author liangkeyu
     * @since 2021-01-28
     */
    void reviewStudentAccounts(ReviewStudentAccountsRequest reviewStudentAccountsRequest);

    /**
     * 删除学生账号
     *
     * @param userId 用户主键
     * @param deleteStudentRequest 删除学生账号请求
     * @author liangkeyu
     * @since 2021-01-28
     */
    void deleteStudent(String userId, DeleteStudentRequest deleteStudentRequest);

    /**
     * 上传头像
     * @param file 头像文件
     * @param loginUser 用户
     * @exception IOException 流异常
     * @return String 头像原文件名
     * @author lishan
     * @since 2021-01-28
     */
    String uploadHeadPic(MultipartFile file, LoginUser loginUser) throws IOException;

    /**
     * 显示头像
     * @param loginUser 用户
     * @return ShowHeadPicResponse 显示头像响应
     * @author lishan
     * @since 2021-01-28
     */
    ShowHeadPicResponse showHeadPic(LoginUser loginUser);

    /**
     * 显示邮箱
     * @param loginUser 用户
     * @return 邮箱
     */
    String showEmail(LoginUser loginUser);

    /**
     * 修改邮箱发送验证码
     * @param updateEmailRequest 修改邮箱请求
     */
    void updateEmail(UpdateEmailRequest updateEmailRequest);

    /**
     * 修改邮箱验证验证码
     * @param loginUser 用户
     * @param updateEmailVerCodeRequest 修改邮箱验证验证码请求
     */
    void verCode(LoginUser loginUser, UpdateEmailVerCodeRequest updateEmailVerCodeRequest);

    /**
     * 显示所有学生
     * @param loginUser 当前用户
     * @return 用户列表
     */
    List<ShowOtherStudentResponse> showOtherStudent(LoginUser loginUser);
}
