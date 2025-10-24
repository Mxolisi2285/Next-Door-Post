package za.ac.nextdoorpost.NextDoorPost.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import za.ac.nextdoorpost.NextDoorPost.service.NewsService;

@Controller
public class NewsPageController {

    private final NewsService newsService;

    public NewsPageController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public String newsPage(Model model) {
        // Fetch news JSON as a list of Map objects (title, description, url, image, date)
        var newsArticles = newsService.getNewsArticles();
        model.addAttribute("newsArticles", newsArticles);
        return "news"; // Thymeleaf template: news.html
    }
}


