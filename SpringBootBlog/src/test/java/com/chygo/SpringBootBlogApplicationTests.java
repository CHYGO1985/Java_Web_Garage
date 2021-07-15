package com.chygo;

import com.chygo.pojo.Article;
import com.chygo.service.ArticleService;
import com.github.pagehelper.Page;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringBootBlogApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Autowired
    private ArticleService articleService;

    @Test
    public void testGetArticles() {
        List<Article> articleList = articleService.getArticles();
    }

    @Test
    public void testFindArticlesByPage() {
        Page<Article> articlePage = articleService.findArticlesByPage(0, 2);
        assertEquals(articlePage.getPages(), 3);
    }

}
