package com.chygo.controller;

import com.chygo.pojo.Article;
import com.chygo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/index")
    public List<Article> getAllArticles() {

        return articleService.getArticles();
    }

}
