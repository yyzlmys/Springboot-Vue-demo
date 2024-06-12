package cn.firstdraft.service;

import cn.firstdraft.entity.User;
import cn.firstdraft.utils.JwtUtils;
import cn.firstdraft.utils.PasswordUtils;
import cn.firstdraft.utils.Result;
import cn.firstdraft.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService
{
    @Autowired
    private UserMapper userMapper;

    public Result login(User user)
    {
        User selectUser = userMapper.selectByUsername(user.getUsername());
        if(selectUser != null)
        {
            String salt = selectUser.getSalt();
            String curHashPassword = PasswordUtils.hashPassword(user.getPassword(), salt);
            if(curHashPassword.equals(selectUser.getPassword()))
            {
                String token = JwtUtils.generateToken(user.getUsername());
                System.out.println(token);
                return Result.ok().data("token", token);
            }
        }
        return Result.error();
    }

    public Result signup(User user)
    {
        user.setSalt(PasswordUtils.generateSalt());
        user.setPassword(PasswordUtils.hashPassword(user.getPassword(), user.getSalt()));
        User selectUser = userMapper.selectByUsername(user.getUsername());
        if(selectUser == null)
        {
            int i = userMapper.insert(user);
            if(i > 0)
                return Result.ok();
        }
        return Result.error();
    }


}
