package at.htl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class WebScraper {

    public static void main(String[] args) throws InterruptedException {
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

        System.out.println(source);
    }
}
