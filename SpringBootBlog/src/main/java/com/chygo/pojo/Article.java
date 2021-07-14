package com.chygo.pojo;

import lombok.Data;

import java.sql.Date;

/**
 *
 * The pojo for ops on table t_article.
 *
 * @author jingjiejiang
 * @history Jul 14, 2021
 *
 */
@Data
public class Article {

    private Integer id;
    private String title;
    private String content;
    private Date created;
    private Date modified;
    private String categories;
    private String tags;
    private byte allow_comment;
    private String thumbnail;

}
