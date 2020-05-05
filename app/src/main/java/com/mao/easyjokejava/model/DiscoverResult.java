package com.mao.easyjokejava.model;

/**
 * @author zhangkun
 * @time 2020-05-05 16:53
 * @Description
 */
public class DiscoverResult {


    /**
     * path : https://news.163.com/20/0505/09/FBRQD7KR00019B3E.html
     * image : http://cms-bucket.ws.126.net/2020/0505/d1e9904dp00q9u35100d8c000s600e3c.png?imageView&thumbnail=140y88&quality=85
     * title : 美国又有一猪肉厂出现群体感染 373名工人核酸阳性
     * passtime : 2020-05-05 10:00:34
     */

    private String path;
    private String image;
    private String title;
    private String passtime;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPasstime() {
        return passtime;
    }

    public void setPasstime(String passtime) {
        this.passtime = passtime;
    }

    @Override
    public String toString() {
        return "DiscoverResult{" +
                "path='" + path + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", passtime='" + passtime + '\'' +
                '}';
    }
}
