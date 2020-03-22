package cn.sbx0.zhibei.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

public class TechnicalAchievementsDataSourcePageProcessor implements PageProcessor {

    private Site site = Site
            .me()
            .setRetryTimes(5)
            .setSleepTime(1 * 1000)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/82.0.4080.0 Safari/537.36 Edg/82.0.453.2");

    @Override
    public void process(Page page) {
        System.out.println(page.getUrl());
        System.out.println(page.getUrl().get().equals("http://localhost:8085/market.html"));
        if (page.getUrl().get().equals("http://localhost:8085/market.html")) {
            List<String> urls = page.getHtml().xpath("//li[@class='wys_seal']/dl/dt/a").regex("/bsp/jscg/info.do\\?pkid=[A-Z]*[0-9]*").all();
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
                    .xpath("//div[@class='infor fl wys_seal']//h2[@class='ellipsis']//text()")
                    .get().replaceAll("\\s*", "")
            );
            // 发布时间
            page.putField("postTime", page
                    .getHtml()
                    .xpath("//div[@class='infor fl wys_seal']//span//text()")
                    .regex("[0-9]*\\-[0-9]*\\-[0-9]*")
                    .get().replaceAll("\\s*", "")
            );
            int index = 1;
            // 行业领域
            page.putField("classification", page
                    .getHtml()
                    .xpath("//div[@class='infor fl wys_seal']//dl//dd[" + index++ + "]//text()")
                    .get().replaceAll("\\s*", "")
            );
            // 专利信息
            String zlxx = page
                    .getHtml()
                    .xpath("//div[@class='infor fl wys_seal']//dl//dd[" + index++ + "]//text()")
                    .get().replaceAll("\\s*", "");
            page.putField("zlxx", zlxx);
            if (!zlxx.equals("非专利技术")) index += 2;
            // 成熟度
            page.putField("maturity", page
                    .getHtml()
                    .xpath("//div[@class='infor fl wys_seal']//dl//dd[" + index++ + "]//text()")
                    .get().replaceAll("\\s*", "")
            );
            // 技术合作方式
            page.putField("cooperationMethod", page
                    .getHtml()
                    .xpath("//div[@class='infor fl wys_seal']//dl//dd[" + index++ + "]//text()")
                    .get().replaceAll("\\s*", "")
            );
            // 技术推广方式
            page.putField("jstgfs", page
                    .getHtml()
                    .xpath("//div[@class='infor fl wys_seal']//dl//dd[" + index++ + "]//text()")
                    .get().replaceAll("\\s*", "")
            );
            // 技术交易价格
            page.putField("price", page
                    .getHtml()
                    .xpath("//div[@class='infor fl wys_seal']//dl//dd[" + index++ + "]//span//text()")
                    .get().replaceAll("\\s*", "")
            );
            index = 1;
            // 应用行业领域
            page.putField("industry", page
                    .getHtml()
                    .xpath("//table[@class='wys_techtable']//tr[" + index++ + "]//td//text()")
                    .get().replaceAll("\\s*", "")
            );
            // 适用范围
            page.putField("scope", page
                    .getHtml()
                    .xpath("//table[@class='wys_techtable']//tr[" + index++ + "]//td//text()")
                    .get().replaceAll("\\s*", "")
            );
            // 成果内容简介
            page.putField("context", page
                    .getHtml()
                    .xpath("//table[@class='wys_techtable']//tr[" + index++ + "]//td//text()")
                    .get().replaceAll("\\s*", "")
            );
            // 前期应用示范情况
            page.putField("earlyApplication", page
                    .getHtml()
                    .xpath("//table[@class='wys_techtable']//tr[" + index++ + "]//td//text()")
                    .get().replaceAll("\\s*", "")
            );
            // 获得研发资助情况
            page.putField("funding", page
                    .getHtml()
                    .xpath("//table[@class='wys_techtable']//tr[" + index++ + "]//td//text()")
                    .get().replaceAll("\\s*", "")
            );
            // prospects
            page.putField("funding", page
                    .getHtml()
                    .xpath("//table[@class='wys_techtable']//tr[" + index++ + "]//td//text()")
                    .get().replaceAll("\\s*", "")
            );
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new TechnicalAchievementsDataSourcePageProcessor())
//                .addUrl("http://www.51jishu.com/bsp/jscg/list.do?indexCode=zhejiang")
                .addUrl("http://localhost:8085/market.html")
                .addPipeline(new JsonFilePipeline("F:\\WebMagic"))
                .thread(4)
                .run();
    }
}