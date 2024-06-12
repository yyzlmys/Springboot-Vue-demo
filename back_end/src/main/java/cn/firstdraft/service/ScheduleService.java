package cn.firstdraft.service;

import cn.firstdraft.entity.Schedule;
import cn.firstdraft.entity.ScheduleList;
import cn.firstdraft.entity.User;
import cn.firstdraft.mapper.ScheduleMapper;
import cn.firstdraft.utils.JwtUtils;
import cn.firstdraft.utils.Result;
import cn.firstdraft.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleMapper scheduleMapper;

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


        List<Schedule> list = scheduleMapper.selectListByUserId(userId);
        return Result.ok().data("items", list);
    }


    public Result updateSchedule(ScheduleList scheduleList)
    {
        String token = request.getHeader("X-token");
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        User user = userMapper.selectByUsername(username);
        Integer userId = user.getId();

        scheduleMapper.deleteByUserId(userId);

        List<Schedule> list = scheduleList.getScheduleList();
        for(Schedule schedule: list) {
            if(schedule.getCourseName() !=null && !schedule.getCourseName().isEmpty() &&
                    !schedule.getCourseName().equals("暂无"))
            {
                schedule.setUserId(userId);
                schedule.setId(0);
                scheduleMapper.insert(schedule);
            }
        }
        return Result.ok();
    }

    public Boolean deleteByUserId(Integer userId)
    {
        return scheduleMapper.deleteByUserId(userId);
    }







}
