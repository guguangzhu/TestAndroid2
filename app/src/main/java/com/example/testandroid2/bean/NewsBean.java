package com.example.testandroid2.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Guge on 16/1/6.
 */
public class NewsBean {


    /**
     * date : 20170223
     * stories : [{"images":["http://pic4.zhimg.com/ac521ef4b0eac9852b0dee96e44469ab.jpg"],"type":0,"id":9244497,"ga_prefix":"022315","title":"- 能用 Apple Pay 付款吗？\r\n- 什么派？你扫这个就行"},{"images":["http://pic4.zhimg.com/1603ae289d30adc254ce33038217c2bf.jpg"],"type":0,"id":9244337,"ga_prefix":"022314","title":"宁泽涛离开国家队暴露出游泳中心管理方式的哪些问题？"},{"images":["http://pic2.zhimg.com/36ae1d8786097fa2f38e1fd22acb74d5.jpg"],"type":0,"id":9241585,"ga_prefix":"022312","title":"如何拯救你的旧手机：要不把它挂到墙上试试？"},{"images":["http://pic1.zhimg.com/6b870148c858f04d5214ac629935fe68.jpg"],"type":0,"id":9244292,"ga_prefix":"022312","title":"职人介绍所第六期：「大姐你创业吗？要钱吗要钱吗要钱吗？」"},{"images":["http://pic3.zhimg.com/f7503085184324845ae964cde2cc3b36.jpg"],"type":0,"id":9244150,"ga_prefix":"022312","title":"和杨振宁一起成为中国科学院院士的姚期智教授有哪些学术成果？"},{"images":["http://pic1.zhimg.com/0e22a5e88f7b325339bad5f3bd330e4c.jpg"],"type":0,"id":9243949,"ga_prefix":"022312","title":"大误 · 吹出一个世界观"},{"images":["http://pic4.zhimg.com/afca5fbbd8330f04e6295b7776705dc3.jpg"],"type":0,"id":9243238,"ga_prefix":"022310","title":"为什么 CT 片、胸片等不直接在电脑上看，非要打印出来？"},{"images":["http://pic4.zhimg.com/bf664b49edf3ea4d97d58aefeef6dee7.jpg"],"type":0,"id":9243066,"ga_prefix":"022309","title":"A4 纸，谋杀案和囊括宇宙的数字"},{"images":["http://pic2.zhimg.com/3b3333a19eccff2def9833c1183a966d.jpg"],"type":0,"id":9241667,"ga_prefix":"022308","title":"小说改编而成的游戏里，为什么它是史上最佳？"},{"images":["http://pic1.zhimg.com/05f163531e1f8d2d46de757165880508.jpg"],"type":0,"id":9243228,"ga_prefix":"022307","title":"想去新西兰打工度假，现在开始准备正是时候"},{"images":["http://pic2.zhimg.com/c45d222453cc212ed3208de40b7196a9.jpg"],"type":0,"id":9243029,"ga_prefix":"022307","title":"互联网从业者 35 岁后只能等着被裁员吗？"},{"images":["http://pic3.zhimg.com/8b7f018052d1ba5138477e00979bfcf6.jpg"],"type":0,"id":9242281,"ga_prefix":"022307","title":"无人机屡屡干扰航空安全，这么危险的事到底怎么管？"},{"images":["http://pic1.zhimg.com/23c8683c0b5780cf9f7b8ed42a7cae8c.jpg"],"type":0,"id":9239674,"ga_prefix":"022306","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic2.zhimg.com/ebc18c64cdcb01f76da3768dd818497d.jpg","type":0,"id":9244337,"ga_prefix":"022314","title":"宁泽涛离开国家队暴露出游泳中心管理方式的哪些问题？"},{"image":"http://pic3.zhimg.com/80283a9e817e9372805506c0b8478436.jpg","type":0,"id":9244292,"ga_prefix":"022312","title":"职人介绍所第六期：「大姐你创业吗？要钱吗要钱吗要钱吗？」"},{"image":"http://pic2.zhimg.com/c59e0fd9ad8d3631d936809806f6d7f9.jpg","type":0,"id":9244150,"ga_prefix":"022312","title":"和杨振宁一起成为中国科学院院士的姚期智教授有哪些学术成果？"},{"image":"http://pic1.zhimg.com/6fbd9f9e9156480d25bdcb37a21a4cb8.jpg","type":0,"id":9243029,"ga_prefix":"022307","title":"互联网从业者 35 岁后只能等着被裁员吗？"},{"image":"http://pic4.zhimg.com/40f362b1bd445b8eb38b045d3ae0b67f.jpg","type":0,"id":9242281,"ga_prefix":"022307","title":"无人机屡屡干扰航空安全，这么危险的事到底怎么管？"}]
     */

    private String date;
    private List<StoriesEntity> stories;
    private List<TopStoriesEntity> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesEntity> getStories() {
        return stories;
    }

    public void setStories(List<StoriesEntity> stories) {
        this.stories = stories;
    }

    public List<TopStoriesEntity> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesEntity> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesEntity implements Serializable{
        /**
         * images : ["http://pic4.zhimg.com/ac521ef4b0eac9852b0dee96e44469ab.jpg"]
         * type : 0
         * id : 9244497
         * ga_prefix : 022315
         * title : - 能用 Apple Pay 付款吗？
         - 什么派？你扫这个就行
         */

        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesEntity implements Serializable{
        /**
         * image : http://pic2.zhimg.com/ebc18c64cdcb01f76da3768dd818497d.jpg
         * type : 0
         * id : 9244337
         * ga_prefix : 022314
         * title : 宁泽涛离开国家队暴露出游泳中心管理方式的哪些问题？
         */

        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
