package cn.firstdraft.controller;

import cn.firstdraft.entity.ScheduleList;
import cn.firstdraft.utils.Response;
import cn.firstdraft.utils.Result;
import cn.firstdraft.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/curriculum")
    public Response getAll()
    {
        Result result = scheduleService.getAll();
        return result.isSuccess() ?
                Response.ok().setData(result.getData()) : Response.error();
    }

    @PostMapping("/curriculum")
    public Response insertTodo(@RequestBody ScheduleList scheduleList)
    {
        Result result = scheduleService.updateSchedule(scheduleList);
        return result.isSuccess() ? Response.ok() : Response.error();
    }

}
