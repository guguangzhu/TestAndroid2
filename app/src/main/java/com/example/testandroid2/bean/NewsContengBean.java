package com.example.testandroid2.bean;

import java.util.List;

/**
 * Created by Guge on 16/1/15.
 */
public class NewsContengBean {


    /**
     * body : <div class="main-wrap content-wrap">
     <div class="headline">

     <div class="img-place-holder"></div>



     </div>

     <div class="content-inner">




     <div class="question">
     <h2 class="question-title">ZARA、H&amp;M、GAP 和优衣库各自的竞争优势是什么？</h2>

     <div class="answer">

     <div class="meta">
     <img class="avatar" src="http://pic1.zhimg.com/da8e974dc_is.jpg">
     <span class="author">曹暘暘</span>
     </div>

     <div class="content">

     </div>


     <div class="view-more"><a href="http://www.zhihu.com/question/20874566">查看知乎讨论<span class="js-question-holder"></span></a></div>

     </div>


     </div>
     </div>
     * image_source : Angel Abril Ruiz / CC BY
     * title : 卖衣服的新手段：把耐用品变成「不停买新的」
     * image : http://p4.zhimg.com/30/59/30594279d368534c6c2f91b2c00c7806.jpg
     * share_url : http://daily.zhihu.com/story/3892357
     * js : []
     * ga_prefix : 050615
     * type : 0
     * id : 3892357
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=77778"]
     */

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private String ga_prefix;
    private int type;
    private int id;
    private List<?> js;
    private List<String> css;

    public void setBody(String body) {
        this.body = body;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJs(List<?> js) {
        this.js = js;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public String getBody() {
        return body;
    }

    public String getImage_source() {
        return image_source;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getShare_url() {
        return share_url;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public List<?> getJs() {
        return js;
    }

    public List<String> getCss() {
        return css;
    }
}
