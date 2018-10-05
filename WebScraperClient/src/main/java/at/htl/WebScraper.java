package at.htl;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class WebScraper {

    private static Logger logger = Logger.getLogger(WebScraper.class);

    public static void main(String[] args) throws InterruptedException, IOException {
        SimpleLayout layout = new SimpleLayout();
        FileAppender appender = new FileAppender(layout,"webCamScrapper.log",false);
        logger.addAppender(appender);

        for(;;){
            scrapWebcam();
            Thread.sleep(1000);
        }
    }

    private static void scrapWebcam() {
        Document doc = null;
        Element stream = null;
        String source = null;
        try{
            doc = Jsoup.connect("https://webtv.feratel.com/webtv/?cam=5132&design=v3&c0=0&c2=1&lg=en&s=0")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("http://www.google.com")
                    .get();
            stream = doc.getElementById("fer_video");
            source = stream.select("source").first().attr("src");
        } catch (IOException e){
            e.printStackTrace();
        }

        logger.info(source);
    }
}
