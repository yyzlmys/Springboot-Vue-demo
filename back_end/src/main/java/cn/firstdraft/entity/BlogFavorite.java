package cn.firstdraft.entity;

import lombok.Data;

@Data
public class BlogFavorite {
    private Integer id;
    private Integer userId;
    private Integer blogId;
    private String createdAt;
}
