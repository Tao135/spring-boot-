package com.czt.utils;

import com.czt.model.domain.Article;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class Commons {

    public static String site_url() {
        return site_url("/page/1");
    }

    public static String site_url(String sub) {
        return site_option("site_url") + sub;
    }


    public static String site_option(String key) {
        return site_option(key, "");
    }


    public static String site_option(String key, String defalutValue) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        return defalutValue;
    }


    public static String substr(String str, int len) {
        if (str.length() > len) {
            return str.substring(0, len);
        }
        return str;
    }


    public static String dateFormat(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    public static String permalink(Integer aid) {
        return site_url("/article/" + aid.toString());
    }


    public static String intro(Article article, int len) {
        String value = article.getContent();
        int pos = value.indexOf("<!--more-->");
        if (pos != -1) {
            String html = value.substring(0, pos);
            return MyUtils.htmlToText(MyUtils.mdToHtml(html));
        } else {
            String text = MyUtils.htmlToText(MyUtils.mdToHtml(value));
            if (text.length() > len) {
                return text.substring(0, len) + "......";
            }
            return text;
        }
    }


    public static String article(String value) {
        if (StringUtils.isNotBlank(value)) {
            value = value.replace("<!--more-->", "\r\n");
            return MyUtils.mdToHtml(value);
        }
        return "";
    }


    public static String show_thumb(Article article) {
        if (StringUtils.isNotBlank(article.getThumbnail())) {
            return article.getThumbnail();
        }
        int cid = article.getId();
        int size = cid % 24;
        size = size == 0 ? 1 : size;
        return "/user/img/rand/" + size + ".png";
    }


    public static String emoji(String value) {
        return EmojiParser.parseToUnicode(value);
    }

}
