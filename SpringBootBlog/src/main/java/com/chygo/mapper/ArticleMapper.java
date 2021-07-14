package com.chygo.mapper;

import com.chygo.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * The mapper class for pojo Article.
 *
 * @author jingjiejiang
 * @history Jul 14, 2021
 *
 */
@Mapper
public interface ArticleMapper {

    @Select("SELECT * FROM T_ARTICLE")
    List<Article> getArticles();
}
