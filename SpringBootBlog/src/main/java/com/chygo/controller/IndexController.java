package com.chygo.controller;

import com.chygo.pojo.Article;
import com.chygo.service.ArticleService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private ArticleService articleService;

    // this is for testing purpose
    @RequestMapping("/home")
    public String index() {
        return "index";
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
