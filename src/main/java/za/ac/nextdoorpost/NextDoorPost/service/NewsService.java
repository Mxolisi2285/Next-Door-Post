package za.ac.nextdoorpost.NextDoorPost.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsService {

    @Value("${thenewsapi.api.key}")
    private String apiKey;

    @Value("${thenewsapi.base.url}")
    private String baseUrl;

    @Value("${thenewsapi.default.language}")
    private String defaultLanguage;

    @Value("${thenewsapi.default.country}")
    private String defaultCountry;

    @Value("${thenewsapi.default.category}")
    private String defaultCategory;

    @Value("${thenewsapi.default.limit}")
    private int defaultLimit;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Map<String, String>> getNewsArticles() {
        String url = String.format("%s?api_token=%s&language=%s&country=%s&category=%s&limit=%d",
                baseUrl, apiKey, defaultLanguage, defaultCountry, defaultCategory, defaultLimit);

        List<Map<String, String>> articlesList = new ArrayList<>();
        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response).get("data"); // "data" contains articles
            if (root != null && root.isArray()) {
                for (JsonNode article : root) {
                    Map<String, String> map = new HashMap<>();
                    map.put("title", article.path("title").asText());
                    map.put("description", article.path("description").asText(""));
                    map.put("url", article.path("url").asText("#"));
                    map.put("imageUrl", article.path("image").asText("/img/news-placeholder.jpg"));
                    map.put("publishedAt", article.path("published_at").asText(""));
                    articlesList.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articlesList;
    }
}


