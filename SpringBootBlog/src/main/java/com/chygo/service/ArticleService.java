package com.chygo.service;

import com.chygo.pojo.Article;
import com.chygo.mapper.ArticleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * The service class for Article mapper.
 *
 * @author jingjiejiang
 * @history Jul 14, 2021
 *
 */
@Service
public class ArticleService {

    Logger logger = LoggerFactory.getLogger(ArticleService.class);

    @Autowired
    private ArticleMapper articleDao;

    public List<Article> getArticles() {

        List<Article> articleList = articleDao.getArticles();
        logger.info("查询出来的Article信息，{}", articleList.toString());

        return articleList;
    }
}
