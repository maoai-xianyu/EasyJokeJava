package com.mao.easyjokejava.model;

import java.util.List;

/**
 * @author zhangkun
 * @time 2020-04-20 10:39
 * @Description
 */
public class NewsModel {


    /**
     * code : 200
     * message : 成功!
     * result : [{"path":"https://news.163.com/20/0419/09/FAILN3KD0001899O.html","image":"http://cms-bucket.ws.126.net/2020/0419/29fa7d4ap00q90hsg00cxc000s600e3c.png?imageView&thumbnail=140y88&quality=85","title":"＂拍好看点＂!贵州女子醉驾被查 全程撒娇还比剪刀手","passtime":"2020-04-19 10:00:33"},{"path":"https://news.163.com/20/0419/09/FAIKRCKI0001899O.html","image":"http://cms-bucket.ws.126.net/2020/0419/dd2694dap00q90h5900nlc000s600e3c.png?imageView&thumbnail=140y88&quality=85","title":"新增降到个位数！韩国新增8例确诊累计10661例","passtime":"2020-04-19 10:00:33"},{"path":"https://news.163.com/20/0419/09/FAIK7BJP0001899O.html","image":"http://cms-bucket.ws.126.net/2020/0419/c34c7dc7p00q90grm00x3c000s600e3c.png?imageView&thumbnail=140y88&quality=85","title":"湖北无新增确诊病例 新增无症状感染者22例","passtime":"2020-04-19 10:00:33"},{"path":"https://news.163.com/20/0419/09/FAIJO0RB00019B3E.html","image":"http://cms-bucket.ws.126.net/2020/0419/886a1d3dj00q90gqj000tc000s600e3c.jpg?imageView&thumbnail=140y88&quality=85","title":"全国昨日新增确诊16例：本土7例 境外输入9例","passtime":"2020-04-19 10:00:33"},{"path":"https://news.163.com/20/0419/08/FAIJ2LUH0001899O.html","image":"http://cms-bucket.ws.126.net/2020/0419/0ab0d78cj00q90icb005pc000s600e3c.jpg?imageView&thumbnail=140y88&quality=85","title":"全球顶级明星抗疫义演:LadyGaga策划 古特雷斯致辞","passtime":"2020-04-19 10:00:33"},{"path":"https://news.163.com/20/0419/08/FAIIAIO300019B3E.html","image":"http://cms-bucket.ws.126.net/2020/0419/2981094bp00q90f1e00dzc000s600e3c.png?imageView&thumbnail=140y88&quality=85","title":"武汉病毒所专家：不相信人类有智慧能合成这个病毒","passtime":"2020-04-19 10:00:33"},{"path":"https://news.163.com/20/0419/08/FAII0AIL00019B3E.html","image":"http://cms-bucket.ws.126.net/2020/0419/dce71b36j00q90evx000ec000s600e3c.jpg?imageView&thumbnail=140y88&quality=85","title":"南通大学虐狗学生被处罚后疑再次虐狗 校方回应","passtime":"2020-04-19 10:00:33"},{"path":"https://news.163.com/20/0419/08/FAIHN2LG0001899O.html","image":"http://cms-bucket.ws.126.net/2020/0419/2a360a5dp00q90ekx00p0c000s600e3c.png?imageView&thumbnail=140y88&quality=85","title":"美国新冠肺炎确诊超73万例 死亡病例38664例","passtime":"2020-04-19 10:00:33"},{"path":"https://tech.163.com/20/0418/22/FAHEORAQ000999D8.html","image":"http://cms-bucket.ws.126.net/2020/0418/fd5e8f83j00q8zn1n00wic000s600e3c.jpg?imageView&thumbnail=140y88&quality=85","title":"从备受追捧到被遗忘，陷入绯闻的张大奕又红了","passtime":"2020-04-19 10:00:33"},{"path":"https://news.163.com/20/0419/08/FAIHLG950001899O.html","image":"http://cms-bucket.ws.126.net/2020/0419/516ed042j00q90eop000gc000s600e3c.jpg?imageView&thumbnail=140y88&quality=85","title":"全球新冠肺炎累计确诊近233万例 死亡病例超16万","passtime":"2020-04-19 10:00:33"},{"path":"https://news.163.com/20/0419/08/FAIH2GAK0001899O.html","image":"http://cms-bucket.ws.126.net/2020/0419/4e521e3dp00q90e2s00e5c000s600e3c.png?imageView&thumbnail=140y88&quality=85","title":"除了世卫组织，美国还欠哪些国际组织的钱？","passtime":"2020-04-19 10:00:33"},{"path":"https://news.163.com/20/0419/08/FAIG63BN0001899O.html","image":"http://cms-bucket.ws.126.net/2020/0419/16f17f37p00q90dgq00tmc000s600e3c.png?imageView&thumbnail=140y88&quality=85","title":"黑龙江省内新增确诊病例6例 新增无症状感染者4例 ","passtime":"2020-04-19 10:00:33"},{"path":"https://news.163.com/20/0419/07/FAIFSHEF0001899O.html","image":"http://cms-bucket.ws.126.net/2020/0419/5f95227bp00q90de9011dc000s600e3c.png?imageView&thumbnail=140y88&quality=85","title":"上海新增境外输入确诊病例7例 其中3例来自俄罗斯","passtime":"2020-04-19 10:00:33"},{"path":"https://news.163.com/20/0419/07/FAIFA3IQ0001899O.html","image":"http://cms-bucket.ws.126.net/2020/0419/776c10fep00q90cn200h4c000s600e3c.png?imageView&thumbnail=140y88&quality=85","title":"有人持湖北＂绿码＂却被跨省确诊 张文宏给出解释","passtime":"2020-04-19 10:00:33"},{"path":"https://news.163.com/20/0419/07/FAIER91A0001899O.html","image":"http://cms-bucket.ws.126.net/2020/0419/ee8a90d7p00q90ca100fwc000s600e3c.png?imageView&thumbnail=140y88&quality=85","title":"纽约警方4347人确诊 请假人数比疫情高峰时明显下降","passtime":"2020-04-19 10:00:33"},{"path":"https://news.163.com/20/0419/07/FAIEF6Q60001899O.html","image":"http://cms-bucket.ws.126.net/2020/0419/3624c162p00q90c2r00prc000s600e3c.png?imageView&thumbnail=140y88&quality=85","title":"吉林新增俄罗斯输入确诊2例 此前多次检测为阴性","passtime":"2020-04-19 10:00:33"},{"path":"https://news.163.com/20/0419/07/FAIDTHAV0001899O.html","image":"http://cms-bucket.ws.126.net/2020/0419/1f6c68c1p00q90bjb00wxc000s600e3c.png?imageView&thumbnail=140y88&quality=85","title":"德国新增新冠肺炎确诊病例2078例 累计143475例","passtime":"2020-04-19 10:00:33"},{"path":"https://news.163.com/20/0419/05/FAI732370001899O.html","image":"http://cms-bucket.ws.126.net/2020/0419/277802a5p00q9060b00foc000s600e3c.png?imageView&thumbnail=140y88&quality=85","title":"黑龙江副省长:坚决堵住防控漏洞 尽快遏制疫情反弹","passtime":"2020-04-19 10:00:33"},{"path":"https://news.163.com/20/0419/04/FAI510DQ0001899O.html","image":"http://cms-bucket.ws.126.net/2020/0419/36f6da0bp00q904c000i4c000s600e3c.png?imageView&thumbnail=140y88&quality=85","title":"美州长:联邦政府不加大援助力度 或现＂全国性灾难＂","passtime":"2020-04-19 10:00:33"},{"path":"https://news.163.com/20/0419/04/FAI49I800001899O.html","image":"http://cms-bucket.ws.126.net/2020/0419/2a6020b3p00q903qd00kbc000s600e3c.png?imageView&thumbnail=140y88&quality=85","title":"加拿大感染人口比例约为美1/3 两国差异为何如此大","passtime":"2020-04-19 10:00:33"}]
     */

    private int code;
    private String message;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * path : https://news.163.com/20/0419/09/FAILN3KD0001899O.html
         * image : http://cms-bucket.ws.126.net/2020/0419/29fa7d4ap00q90hsg00cxc000s600e3c.png?imageView&thumbnail=140y88&quality=85
         * title : ＂拍好看点＂!贵州女子醉驾被查 全程撒娇还比剪刀手
         * passtime : 2020-04-19 10:00:33
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
            return "ResultBean{" +
                    "path='" + path + '\'' +
                    ", image='" + image + '\'' +
                    ", title='" + title + '\'' +
                    ", passtime='" + passtime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
