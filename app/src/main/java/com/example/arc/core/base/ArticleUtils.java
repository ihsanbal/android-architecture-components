package com.example.arc.core.base;

import android.text.TextUtils;

import com.example.arc.model.data.Article;
import com.example.arc.model.data.Articles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author ihsan on 2/28/18.
 */

public class ArticleUtils {

    public static Articles formatDate(Articles articles) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",
                Locale.getDefault());
        for (Article article : articles.getArticles()) {
            if (!TextUtils.isEmpty(article.getPublishedAt())) {
                Date date = simpleDateFormat.parse(article.getPublishedAt());
                article.setPublishedAt(new SimpleDateFormat("h:mm a", Locale.getDefault()).format(date));
            }
        }
        return articles;
    }
}
