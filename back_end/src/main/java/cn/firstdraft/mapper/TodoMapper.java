package cn.firstdraft.mapper;

import cn.firstdraft.entity.Todo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TodoMapper extends BaseMapper<Todo> {
    @Select("SELECT * FROM todo WHERE user_id = #{userId}")
    List<Todo> selectListByUserId(Integer userId);

    @Delete("DELETE FROM todo WHERE user_id = #{userId}")
    Boolean deleteByUserId(Integer userId);
}