package cn.firstdraft.entity;

import lombok.Data;

@Data
public class Schedule {
    private Integer id;
    private String courseName;
    private Integer timeIndex;
    private Integer dayIndex;
    private Integer userId;
}
