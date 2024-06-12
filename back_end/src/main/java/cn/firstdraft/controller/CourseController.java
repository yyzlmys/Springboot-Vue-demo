package cn.firstdraft.controller;

import cn.firstdraft.entity.Course;
import cn.firstdraft.utils.Response;
import cn.firstdraft.utils.Result;
import cn.firstdraft.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/curriculum/course")
    public Response getAll()
    {
        Result result = courseService.getAll();
        return result.isSuccess() ?
                Response.ok().setData(result.getData()) : Response.error();
    }

    @PostMapping("/curriculum/course")
    public Response insertTodo(@RequestBody Course course)
    {
        Result result = courseService.insert(course);
        return result.isSuccess() ? Response.ok() : Response.error();
    }


    @DeleteMapping("/curriculum/course/{id}")
    public Response deleteByID(@PathVariable Integer id)
    {
        Result result = courseService.deleteById(id);
        return result.isSuccess() ? Response.ok() : Response.error();
    }
}
