package cn.sbx0.zhibei.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class TechnicalRequirementsDataSourcePageProcessor implements PageProcessor {

    private Site site = Site
            .me()
            .setRetryTimes(10)
            .setCycleRetryTimes(10)
            .setTimeOut(1 * 60 * 1000)
            .setSleepTime(1 * 1000)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/82.0.4080.0 Safari/537.36 Edg/82.0.453.2");

    @Override
    public void process(Page page) {
        if (page.getUrl().get().equals("http://51jishu.com/bsp/jsxq/list.do")) {
            List<String> urls = page.getHtml().xpath("//li[@class='wys_seal']/dl/dt/a").regex("/bsp/jsxq/info.do\\?pkid=[A-Z]*[0-9]*").all();
            List<String> newUrls = new ArrayList<>();
//            int i = 0;
            for (String url : urls) {
//                if (i > 5) break;
                newUrls.add("http://www.51jishu.com" + url);
                System.out.println("http://www.51jishu.com" + url);
//                i++;
            }
            page.addTargetRequests(newUrls);
        } else {
            // 标题
            page.putField("name", page
                    .getHtml()
                    .xpath("//div[@class='wys_borderbg wys_xqinfo wys_xqinfo1 h210 pl30 pr30 pb20 wys_seal']//h3[@class='ellipsis']//text()")
                    .get().replaceAll("\\s*", "")
            );
            // 发布时间
            page.putField("postTime", page
                    .getHtml()
                    .xpath("//div[@class='wys_borderbg wys_xqinfo wys_xqinfo1 h210 pl30 pr30 pb20 wys_seal']//span[2]//text()")
                    .regex("[0-9]*\\-[0-9]*\\-[0-9]*")
                    .get().replaceAll("\\s*", "")
            );
            // 行业分类
            page.putField("classification", page
                    .getHtml()
                    .xpath("//div[@class='wys_borderbg wys_xqinfo wys_xqinfo1 h210 pl30 pr30 pb20 wys_seal']//dl//dd[1]//text()")
                    .get().replaceAll("\\s*", "")
            );
            // 合作方式
            String cooperationMethod = page
                    .getHtml()
                    .xpath("//div[@class='wys_borderbg wys_xqinfo wys_xqinfo1 h210 pl30 pr30 pb20 wys_seal']//dl//dd[2]//text()")
                    .get().replaceAll("\\s*", "");
            page.putField("cooperationMethod", cooperationMethod);
            // 时间
            String time = page
                    .getHtml()
                    .xpath("//div[@class='wys_borderbg wys_xqinfo wys_xqinfo1 h210 pl30 pr30 pb20 wys_seal']//dl//dd[6]//text()")
                    .get().replaceAll("\\s*", "");
            page.putField("time", time);
            // 内容
            page.putField("context", page
                    .getHtml()
                    .xpath("//table[@class='wys_techtable']//tbody//tr[1]//td//text()")
                    .get().replaceAll("\\s*", "")
            );
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider spider = Spider.create(new TechnicalRequirementsDataSourcePageProcessor());
        int startPage = 41;
        int step = 10 - 1;
        int endPage = startPage + step;
        for (int i = startPage; i <= endPage; i++) {
            Request request = new Request("http://51jishu.com/bsp/jsxq/list.do");
            request.setMethod(HttpConstant.Method.POST);
            Map<String, Object> params = new Hashtable<>();
            params.put("pageNumber", i);
            params.put("pageSize", 50);
            request.setRequestBody(HttpRequestBody.form(params, "utf-8"));
            spider.addRequest(request);
        }
        spider.addPipeline(new JsonFilePipeline("F:\\WebMagic"))
                .thread(10)
                .run();
        System.out.println(startPage + " to " + endPage + " pages downloaded");
    }
}