package cn.firstdraft.mapper;

import cn.firstdraft.entity.Schedule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ScheduleMapper extends BaseMapper<Schedule> {
    @Select("SELECT * FROM schedule WHERE user_id = #{userId}")
    List<Schedule> selectListByUserId(Integer userId);

    @Delete("DELETE FROM schedule WHERE user_id = #{userId}")
    Boolean deleteByUserId(Integer userId);

}