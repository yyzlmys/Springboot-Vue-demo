package cn.firstdraft.service;

import cn.firstdraft.entity.Course;
import cn.firstdraft.entity.User;
import cn.firstdraft.mapper.CourseMapper;
import cn.firstdraft.utils.JwtUtils;
import cn.firstdraft.utils.Result;
import cn.firstdraft.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpServletRequest request;

    public Result getAll()
    {
        String token = request.getHeader("X-token");
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        Integer userId = user.getId();

        return Result.ok().data("items", courseMapper.selectListByUserId(userId));
    }

    public Result deleteById(Integer id)
    {
        int i = courseMapper.deleteById(id);
        return i > 0 ? Result.ok() : Result.error();
    }

    public Result insert(Course course)
    {
        String token = request.getHeader("X-token");
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        Integer userId = user.getId();

        course.setUserId(userId);
        if(courseMapper.selectByCourseName(course.getName())==null)
        {
            int i = courseMapper.insert(course);
            return i > 0 ? Result.ok() : Result.error();
        }
        return Result.error();
    }

    public Boolean deleteByUserId(Integer userId)
    {
        return courseMapper.deleteByUserId(userId);
    }


}
