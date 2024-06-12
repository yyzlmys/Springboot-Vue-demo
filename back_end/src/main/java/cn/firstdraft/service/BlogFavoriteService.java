package cn.firstdraft.service;

import cn.firstdraft.entity.BlogFavorite;
import cn.firstdraft.entity.User;
import cn.firstdraft.mapper.BlogFavoriteMapper;
import cn.firstdraft.utils.JwtUtils;
import cn.firstdraft.utils.Result;
import cn.firstdraft.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogFavoriteService {

    @Autowired
    private BlogFavoriteMapper blogFavoriteMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpServletRequest request;

    public List<BlogFavorite> getFavoritessByBlogId(Integer blogId)
    {
        return blogFavoriteMapper.selectByBlogId(blogId);
    }

    public Integer getFavoriteSizeByBlogId(Integer blogId)
    {
        return blogFavoriteMapper.getFavoriteSizeByBlogId(blogId);
    }

    public Boolean deleteFavoritesByBlogId(Integer blogId)
    {
        return blogFavoriteMapper.deleteFavoritesByBlogId(blogId);
    }

    public Boolean deleteAllByUserId(Integer userId)
    {
        return blogFavoriteMapper.deleteAllByUserId(userId);
    }

    public Boolean isMyFavorite(Integer blogId, Integer userId)
    {
        System.out.println(blogFavoriteMapper.getMyFavoriteId(blogId, userId));
        return !blogFavoriteMapper.getMyFavoriteId(blogId, userId).isEmpty();
    }

    public Integer getMyFavoriteId(Integer blogId, Integer userId)
    {
        List<Integer> ids = blogFavoriteMapper.getMyFavoriteId(blogId, userId);
        if(ids.isEmpty())
            return -1;
        else
            return ids.getFirst();
    }

    public Result insert(Integer blogId)
    {
        String token = request.getHeader("X-token");
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        Integer userId = user.getId();
        BlogFavorite blogFavorite = new BlogFavorite();
        blogFavorite.setId(0);
        blogFavorite.setUserId(userId);
        blogFavorite.setBlogId(blogId);
        int i = blogFavoriteMapper.insert(blogFavorite);
        return i > 0 ? Result.ok() : Result.error();
    }

    public Result delete(Integer id)
    {
        int i = blogFavoriteMapper.deleteById(id);
        return i > 0 ? Result.ok() : Result.error();
    }
}
