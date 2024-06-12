package cn.firstdraft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.firstdraft.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    @Select("SELECT id, user_id, time, title, is_public FROM blog")
    public List<Blog> selectBasicBlogs();

    @Select("SELECT * FROM blog WHERE user_id = #{userId}")
    public List<Blog> selectByUserId(Integer userId);


}