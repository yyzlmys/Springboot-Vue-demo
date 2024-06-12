package cn.firstdraft.service;

import cn.firstdraft.entity.Todo;
import cn.firstdraft.entity.User;
import cn.firstdraft.utils.JwtUtils;
import cn.firstdraft.utils.Result;
import cn.firstdraft.mapper.TodoMapper;
import cn.firstdraft.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService
{
    @Autowired
    private TodoMapper todoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpServletRequest request;

    public Result listAll()
    {
        String token = request.getHeader("X-token");
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        Integer userId = user.getId();

        return Result.ok().data("items",todoMapper.selectListByUserId(userId));
    }

    public Result insert(Todo todo)
    {
        String token = request.getHeader("X-token");
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        Integer userId = user.getId();

        todo.setUserId(userId);
        int i = todoMapper.insert(todo);
        return i > 0 ? Result.ok() : Result.error();
    }

    public Result updateStatus(Todo todo)
    {
        int i = todoMapper.updateById(todo);
        return i > 0 ? Result.ok() : Result.error();
    }

    public Result deleteById(Integer id)
    {
        int i = todoMapper.deleteById(id);
        return i > 0 ? Result.ok() : Result.error();
    }

    public Boolean deleteAllByUserId(Integer userId)
    {
        return todoMapper.deleteByUserId(userId);
    }


}
