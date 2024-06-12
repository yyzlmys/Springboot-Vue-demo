package cn.firstdraft.service;

import cn.firstdraft.entity.User;
import cn.firstdraft.utils.JwtUtils;
import cn.firstdraft.utils.PasswordUtils;
import cn.firstdraft.utils.Result;
import cn.firstdraft.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalService
{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BlogService blogService;
    @Autowired
    private TodoService todoService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private HttpServletRequest request;     //用于解析请求头中的token

    public Result getInfo()
    {
        String token = request.getHeader("X-token");
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        if (user != null)
            return Result.ok().data("items", user);
        else
            return Result.error();
    }

    public Result changePassword(User user)
    {
        String token = request.getHeader("X-token");
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        String password = user.getPassword();
        User selectUser = userMapper.selectByUsername(username);
        if (selectUser != null)
        {
            selectUser.setSalt(PasswordUtils.generateSalt());
            selectUser.setPassword(PasswordUtils.hashPassword(password, selectUser.getSalt()));
            int i = userMapper.updateById(selectUser);
            if(i>0)
                return Result.ok();
        }
        return Result.error();
    }

    public Result updateUsername(User user)
    {
        System.out.println(user);
        if(userMapper.selectByUsername(user.getUsername()) != null)
            return Result.error();
        int i = userMapper.updateById(user);
        return i > 0 ? Result.ok() : Result.error();
    }

    public Result update(User user)
    {
        int i = userMapper.updateById(user);
        return i > 0 ? Result.ok() : Result.error();
    }

    public Result delete()
    {
        String token = request.getHeader("X-token");
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        Integer userId = user.getId();

        blogService.deleteAllByUserId(userId);
        todoService.deleteAllByUserId(userId);
        scheduleService.deleteByUserId(userId);
        courseService.deleteByUserId(userId);

        return userMapper.deleteById(userId) > 0 ?
                Result.ok() : Result.error();
    }

}
