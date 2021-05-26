package com.somecoder.demo.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.somecoder.demo.blog.entity.*;
import com.somecoder.demo.blog.entity.constant.CommonConstant;
import com.somecoder.demo.blog.entity.enums.GenderEnum;
import com.somecoder.demo.blog.entity.request.*;
import com.somecoder.demo.blog.entity.response.*;
import com.somecoder.demo.blog.mapper.*;
import com.somecoder.demo.blog.service.IStudentService;
import com.somecoder.demo.blog.service.VerCodeService;
import com.somecoder.demo.common.exception.CustomException;
import com.somecoder.demo.common.util.CorrectEmailUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Resource
    StudentMapper studentMapper;

    @Resource
    LoginUserMapper loginUserMapper;

    @Resource
    private UpvoteMapper upvoteMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private ReplyMapper replyMapper;

    @Resource
    private LetterMapper letterMapper;

    @Resource
    private FriendMapper friendMapper;

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private CommentMessageMapper commentMessageMapper;

    @Value("${file.url}")
    private String url;

    @Resource
    private VerCodeService verCodeService;

    @Resource
    private JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    /**
     *
     * 显示审核学生列表
     */
    @Override
    public Page<ShowReviewStudentResponse> showReviewStudent(
            String userId, ShowReviewStudentRequest showReviewStudentRequest
    ) {
        // 模糊查询学号和姓名
        LambdaQueryWrapper<Student> studentListQuery = Wrappers.lambdaQuery(Student.class);
        if (StringUtils.isNotBlank(showReviewStudentRequest.getStudentName())) {
            studentListQuery.like(Student::getName, showReviewStudentRequest.getStudentName());
        }
        if (StringUtils.isNotBlank(showReviewStudentRequest.getStudentNumber())) {
            studentListQuery.like(Student::getStudentNumber, showReviewStudentRequest.getStudentNumber());
        }

        // 分页
        Page<Student> loginUserQueryPage = new Page<>();
        BeanUtils.copyProperties(showReviewStudentRequest.getPostListRequest(), loginUserQueryPage);
        Page<Student> loginUserPage = studentMapper.selectPage(loginUserQueryPage, studentListQuery);
        List<Student> loginUserList = loginUserPage.getRecords();
        Page<ShowReviewStudentResponse> showReviewStudentResponsePage = new Page<>();
        BeanUtils.copyProperties(loginUserPage, showReviewStudentResponsePage);
        List<ShowReviewStudentResponse> showReviewStudentResponseList = new ArrayList<>();
        // 待审核学生列表
        loginUserList.forEach(student -> {

            ShowReviewStudentResponse showReviewStudentResponse = new ShowReviewStudentResponse();
            LoginUser loginUser = loginUserMapper.selectOne(
                    Wrappers.lambdaQuery(LoginUser.class)
                            .eq(LoginUser::getUserId, student.getUserId())
            );
            if (loginUser.getUserType() == -1) {
                BeanUtils.copyProperties(student, showReviewStudentResponse);
                showReviewStudentResponseList.add(showReviewStudentResponse);
            }
        });
        showReviewStudentResponsePage.setRecords(showReviewStudentResponseList);
        return showReviewStudentResponsePage;
    }

    /**
     *
     * 显示学生信息
     */
    @Override
    public ShowInformationResponse showInformation(String userId) {
        Student student = studentMapper.selectOne(
                Wrappers.lambdaQuery(Student.class)
                        .eq(Student::getUserId, userId)
        );
        String birthday = student.getBirthday();
        ShowInformationResponse studentInformationResponse = new ShowInformationResponse();
        BeanUtils.copyProperties(student, studentInformationResponse);
        if (StringUtils.isNotBlank(birthday)) {
            String[] strings = birthday.split("-");
            studentInformationResponse.setBirthdayYear(strings[0]);
            studentInformationResponse.setBirthdayMonth(strings[1]);
            studentInformationResponse.setBirthdayDay(strings[2]);
        } else {
            studentInformationResponse.setBirthdayYear("");
            studentInformationResponse.setBirthdayMonth("");
            studentInformationResponse.setBirthdayDay("");
        }
        studentInformationResponse.setGender(GenderEnum.getMessageByGender(student.getGender()));
        studentInformationResponse.setLocation(student.getLocation());

        return studentInformationResponse;
    }

    /**
     *
     * 获取学生列表
     */
    @Override
    public Page<StudentListResponse> getStudentList(StudentListRequest studentListRequest) {
        // 模糊查找学生姓名
        String studentName = studentListRequest.getName();
        String studentNumber = studentListRequest.getNumber();
        LambdaQueryWrapper<Student> studentListQuery = Wrappers.lambdaQuery(Student.class);
        if (StringUtils.isNotBlank(studentName)) {
            studentListQuery.like(Student::getName, studentName);
        }
        if (StringUtils.isNotBlank(studentNumber)) {
            studentListQuery.like(Student::getStudentNumber, studentNumber);
        }
        // 分页查询
        Page<Student> studentListQueryPage = new Page<>();
        BeanUtils.copyProperties(studentListRequest.getPageRequest(), studentListQueryPage);
        Page<Student> studentPage = studentMapper.selectPage(studentListQueryPage, studentListQuery);
        List<Student> studentList = studentPage.getRecords();
        Page<StudentListResponse> studentListResponsePage = new Page<>();
        BeanUtils.copyProperties(studentPage, studentListResponsePage);

        List<LoginUser> loginUsers = loginUserMapper.selectList(
                Wrappers.lambdaQuery(LoginUser.class)
                        .ne(LoginUser::getUserType, -1)
        );
        studentList = new LinkedList<>(studentList);
        List<Student> list = new LinkedList<>();
        for (LoginUser loginUser : loginUsers) {
            for (Iterator<Student> iterator = studentList.iterator(); iterator.hasNext(); ) {
                Student next = iterator.next();
                if (next.getUserId().equals(loginUser.getUserId())) {
                    list.add(next);
                    iterator.remove();
                    break;
                }
            }
        }
        studentList = list;

        // 学生列表
        List<StudentListResponse> studentListResponseList = new ArrayList<>();
        for (Student student : studentList) {
            StudentListResponse studentListResponse = new StudentListResponse();
            BeanUtils.copyProperties(student, studentListResponse);
            studentListResponseList.add(studentListResponse);
        }
        studentListResponsePage.setRecords(studentListResponseList);
        return studentListResponsePage;
    }

    /**
     *
     * 修改学生信息
     */
    @Override
    public ChangeInformationResponse changeInformation(
            String userId, ChangeInformationRequest changeInformationRequest
    ) {
        Student student = studentMapper.selectOne(
                Wrappers.lambdaQuery(Student.class)
                        .eq(Student::getUserId, userId)
        );
        // 修改个性签名
        if (StringUtils.isNotBlank(changeInformationRequest.getPersonalSignature())) {
            student.setPersonalSignature(changeInformationRequest.getPersonalSignature());
        }
        // 修改性别
        if (StringUtils.isNotBlank(changeInformationRequest.getGender())) {
            if (changeInformationRequest.getGender().equals(CommonConstant.MALE)) {
                student.setGender(1);
            }
            if (changeInformationRequest.getGender().equals(CommonConstant.FEMALE)) {
                student.setGender(0);
            }
            if (changeInformationRequest.getGender().equals(CommonConstant.UNKNOWN)) {
                student.setGender(-1);
            }
        }
        // 修改生日
        if (StringUtils.isNotBlank(changeInformationRequest.getBirthdayYear()) ||
            StringUtils.isNotBlank(changeInformationRequest.getBirthdayMonth()) ||
            StringUtils.isNotBlank(changeInformationRequest.getBirthdayDay())
        ) {
            String year = changeInformationRequest.getBirthdayYear();
            String month = changeInformationRequest.getBirthdayMonth();
            String day =changeInformationRequest.getBirthdayDay();
            student.setBirthday(year + "-" + month + "-" + day  );
        }
        // 修改星座
        if (StringUtils.isNotBlank(changeInformationRequest.getConstellation())) {
            student.setConstellation(changeInformationRequest.getConstellation());
        }
        // 修改地址
        if (StringUtils.isNotBlank(changeInformationRequest.getLocation())) {
            student.setLocation(changeInformationRequest.getLocation());
        }
        studentMapper.updateById(student);
        ChangeInformationResponse studentInformationResponse = new ChangeInformationResponse();
        BeanUtils.copyProperties(student, studentInformationResponse);
        studentInformationResponse.setGender(GenderEnum.getMessageByGender(student.getGender()));
        studentInformationResponse.setLocation(student.getLocation());
        return studentInformationResponse;
    }

    /**
     *
     * 修改密码
     */
    @Override
    public void changePassword(String userId, ChangePasswordRequest changePasswordRequest) {
        LoginUser loginUser = loginUserMapper.selectOne(
                Wrappers.lambdaQuery(LoginUser.class)
                        .eq(LoginUser::getUserId, userId)
        );
        if (!changePasswordRequest.getPassword().equals(loginUser.getPassword())) {
            throw new CustomException(1, "原密码错误！");
        } else if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getNewPasswordAgain())) {
            throw new CustomException(1, "两次输入密码不一致！");
        } else if (changePasswordRequest.getNewPassword().length() < CommonConstant.MAXLENGTH) {
            throw new CustomException(1, "密码长度不得少于6位");
        } else {
            loginUser.setPassword(changePasswordRequest.getNewPassword());
            loginUserMapper.updateById(loginUser);
        }
    }

    /**
     *
     * 审核学生账号
     */
    @Override
    public void reviewStudentAccounts(ReviewStudentAccountsRequest reviewStudentAccountsRequest) {
        // 判断审核是否通过
        if (CommonConstant.YES.equals(reviewStudentAccountsRequest.getIsPass())) {
            // 通过则更新用户类型
            loginUserMapper.update(
                    null,
                    Wrappers.lambdaUpdate(LoginUser.class)
                            .set(LoginUser::getUserType, 0)
                            .eq(LoginUser::getUserId, reviewStudentAccountsRequest.getStudentId())
            );
        } else {
            // 不通过则从表中删除
            studentMapper.delete(
                    Wrappers.lambdaQuery(Student.class)
                            .eq(Student::getUserId, reviewStudentAccountsRequest.getStudentId())
            );
            loginUserMapper.delete(
                    Wrappers.lambdaQuery(LoginUser.class)
                            .eq(LoginUser::getUserId, reviewStudentAccountsRequest.getStudentId())
            );
        }
    }

    /**
     *
     * 删除学生
     */
    @Override
    public void deleteStudent(String userId, DeleteStudentRequest deleteStudentRequest) {
        Student student = studentMapper.selectOne(
                Wrappers.lambdaQuery(Student.class)
                        .eq(Student::getUserId, deleteStudentRequest.getUserId())
        );
        // 判断学生是否存在
        if (student == null) {
            throw new CustomException("该学生不存在");
        }
        studentMapper.delete(
                Wrappers.lambdaQuery(Student.class)
                        .eq(Student::getUserId, deleteStudentRequest.getUserId())
        );
        loginUserMapper.delete(
                Wrappers.lambdaQuery(LoginUser.class)
                        .eq(LoginUser::getUserId, deleteStudentRequest.getUserId())
        );
        upvoteMapper.delete(
                Wrappers.lambdaQuery(Upvote.class)
                        .eq(Upvote::getUserId, deleteStudentRequest.getUserId())
        );
        commentMapper.delete(
                Wrappers.lambdaQuery(Comment.class)
                        .eq(Comment::getUserId, deleteStudentRequest.getUserId())
        );
        collectMapper.delete(
                Wrappers.lambdaQuery(Collect.class)
                        .eq(Collect::getUserId, deleteStudentRequest.getUserId())
        );
        replyMapper.delete(
                Wrappers.lambdaQuery(Reply.class)
                        .eq(Reply::getFromUserId, deleteStudentRequest.getUserId())
                        .or()
                        .eq(Reply::getToUserId, deleteStudentRequest.getUserId())
        );
        friendMapper.delete(
                Wrappers.lambdaQuery(Friend.class)
                        .eq(Friend::getFriendUserId, deleteStudentRequest.getUserId())
                        .or()
                        .eq(Friend::getUserId, deleteStudentRequest.getUserId())
        );
        letterMapper.delete(
                Wrappers.lambdaQuery(Letter.class)
                        .eq(Letter::getToUserId, deleteStudentRequest.getUserId())
                        .or()
                        .eq(Letter::getFromUserId, deleteStudentRequest.getUserId())
        );
        messageMapper.delete(
                Wrappers.lambdaQuery(Message.class)
                        .eq(Message::getFromUserId, deleteStudentRequest.getUserId())
                        .or()
                        .eq(Message::getToUserId, deleteStudentRequest.getUserId())
        );
        commentMessageMapper.delete(
                Wrappers.lambdaQuery(CommentMessage.class)
                        .eq(CommentMessage::getFromUserId, deleteStudentRequest.getUserId())
                        .or()
                        .eq(CommentMessage::getToUserId, deleteStudentRequest.getUserId())
        );


    }

    /**
     *
     * 上传头像
     */
    @Override
    public String uploadHeadPic(MultipartFile file, LoginUser loginUser) throws IOException {
        // 获取工作目录
        String currentPath = System.getProperty("user.dir");
        // 如果不是定位到java目录，就添加路径
        if (!currentPath.endsWith("java")) {
            currentPath += "/java";
        }
        currentPath += "/upload/";

        String fileName = UUID.randomUUID().toString().replace("-", "");
        Path path = Paths.get(currentPath, fileName + ".jpg");
        file.transferTo(path);
        // 更新学生表的头像路径
        studentMapper.update(
                null,
                Wrappers.lambdaUpdate(Student.class)
                        .set(Student::getHeadPicUrl, url + fileName + ".jpg")
                        .eq(Student::getUserId, loginUser.getUserId())
        );

        return file.getOriginalFilename();
    }

    /**
     *
     * 显示头像
     */
    @Override
    public ShowHeadPicResponse showHeadPic(LoginUser loginUser) {
        Student student = studentMapper.selectOne(
                Wrappers.lambdaQuery(Student.class)
                        .eq(Student::getUserId, loginUser.getUserId())
        );
        ShowHeadPicResponse showHeadPicResponse = new ShowHeadPicResponse();
        showHeadPicResponse.setValue(student.getHeadPicUrl());
        showHeadPicResponse.setKey("url");
        return showHeadPicResponse;
    }

    /**
     *
     * 显示邮箱
     */
    @Override
    public String showEmail(LoginUser loginUser) {
        Student student = studentMapper.selectOne(
                Wrappers.lambdaQuery(Student.class)
                        .eq(Student::getUserId, loginUser.getUserId())
        );
        return student.getEmail();
    }

    /**
     *
     * 填写邮箱并请求验证码
     */
    @Override
    public void updateEmail(UpdateEmailRequest updateEmailRequest) {
        if (!CorrectEmailUtils.isValidEmail(updateEmailRequest.getNewEmail())) {
            throw new CustomException(1, "邮箱格式错误");
        }
        verCodeService.getCode(sender, mailSender, updateEmailRequest.getNewEmail());
    }

    /**
     *
     * 修改邮箱
     */
    @Override
    public void verCode(LoginUser loginUser, UpdateEmailVerCodeRequest updateEmailVerCodeRequest) {
        if (verCodeService.judgeVerCode(updateEmailVerCodeRequest.getCode())) {
            Student student = studentMapper.selectOne(
                    Wrappers.lambdaQuery(Student.class)
                            .eq(Student::getUserId, loginUser.getUserId())
            );
            student.setEmail(updateEmailVerCodeRequest.getNewEmail());
            studentMapper.updateById(student);
        }
    }

    /**
     * 显示其他学生
     */
    @Override
    public List<ShowOtherStudentResponse> showOtherStudent(LoginUser loginUser) {
        List<Student> students = studentMapper.selectList(
                Wrappers.lambdaQuery(Student.class)
                        .ne(Student::getUserId, loginUser.getUserId())
        );
        List<LoginUser> loginUsers = loginUserMapper.selectList(
                Wrappers.lambdaQuery(LoginUser.class)
                        .ne(LoginUser::getUserId, loginUser.getUserId())
                        .ne(LoginUser::getUserType, -1)
        );
        students = new LinkedList<>(students);
        List<Student> studentList = new ArrayList<>();
        for (LoginUser user : loginUsers) {
            for (Iterator<Student> iterator = students.iterator(); iterator.hasNext(); ) {
                Student next = iterator.next();
                if (next.getUserId().equals(user.getUserId())) {
                    studentList.add(next);
                    iterator.remove();
                    break;
                }
            }
        }
        students = studentList;
        List<ShowOtherStudentResponse> list = new ArrayList<>(students.size());
        students.forEach(student -> {
            ShowOtherStudentResponse showOtherStudentResponse = new ShowOtherStudentResponse();
            BeanUtils.copyProperties(student, showOtherStudentResponse);
            list.add(showOtherStudentResponse);
        });
        return list;
    }
}
