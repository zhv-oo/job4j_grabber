package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.Post;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    private final DateTimeParser dateTimeParser;

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    public static void main(String[] args) {
        SqlRuParse sqlRuParse = new SqlRuParse(new SqlRuDateTimeParser());
        List<Post> newPost = sqlRuParse.list("https://www.sql.ru/forum/job-offers/");
        newPost.forEach(System.out::println);
    }

    @Override
    public List<Post> list(String link) {
        List<Post> rsl = new ArrayList<>();
        Elements row = new Elements();
        try {
            for (int i = 1; i < 5; i++) {
                Document doc = Jsoup.connect(link + i).get();
                Elements rowTmp = doc.select(".postslisttopic");
                row.addAll(rowTmp);
            }
            for (int i = 3; i < row.size(); i++) {
                Element href = row.get(i).child(0);
                rsl.add(this.detail(href.attr("href")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public Post detail(String link) {
        Post rsl = null;
        try {
            Document doc = Jsoup.connect(link).get();
            Elements rowTmp = doc.select(".msgBody");
            String desc = rowTmp.get(1).text();
            String title = rowTmp.parents().get(1).children().get(0).text();
            LocalDateTime create = new SqlRuDateTimeParser().parse(
                    rowTmp.parents().get(1).children().get(2).text().split("\\[")[0]);
            rsl = new Post(title, link, desc, create);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsl;
    }
}