package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Post;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        SqlRuParse sqlRuParse = new SqlRuParse();
        List<Post> newPost = sqlRuParse.getPosts("https://www.sql.ru/forum/job-offers/");
        newPost.forEach(System.out::println);
    }

    public List<Post> getPosts(String link) throws IOException {
        List<Post> rsl = new ArrayList<>();
        Elements row = new Elements();
        for (int i = 1; i < 5; i++) {
            Document doc = Jsoup.connect(link + i).get();
            Elements rowTmp = doc.select(".postslisttopic");
            row.addAll(rowTmp);
        }
        for (int i = 3; i < row.size(); i++) {
            Element href = row.get(i).child(0);
            Document doc = Jsoup.connect(href.attr("href")).get();
            Elements rowTmp = doc.select(".msgBody");
            LocalDateTime create = new SqlRuDateTimeParser().parse(row.get(i)
                                                .parent().children().get(5).text());
            String desc = rowTmp.get(1).text();
            rsl.add(new Post(
                    href.text(), href.attr("href"), desc, create));
        }
        return rsl;
    }
}