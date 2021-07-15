package com.chygo.controller;

import com.chygo.pojo.Article;
import com.chygo.service.ArticleService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * The controller for article service.
 *
 * @author jingjiejiang
 * @history Jul 15, 2021
 *
 */
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/articles")
    public List<Article> getAllArticles() {

        return articleService.getArticles();
    }

    @RequestMapping("/index")
    public String findArticlesByPage(Model model,
                                     @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "2") int pageSize) {

        Page<Article> articlePage = articleService.findArticlesByPage(pageNum, pageSize);
        model.addAttribute("page", articlePage);

        return "index";
    }

}
