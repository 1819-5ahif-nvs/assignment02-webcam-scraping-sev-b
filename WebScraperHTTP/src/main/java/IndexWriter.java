import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class IndexWriter implements Runnable {
    @Override
    public void run() {
        String latest = "";
        for(;;){
            latest = scrapWebcam();
            //if(!latest.contentEquals(latest)){
            writeToIndex(latest);
            //}
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String scrapWebcam() {
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
        return source;
    }

    private static void writeToIndex(String source) {
        try {
            File index = new File(WebScraperHTTP.DEFAULT_FILE);
            Document report = null;
            report = Jsoup.parse(source, "UTF-8");
            Elements dom = report.children();
            Timestamp ts = new Timestamp((long)(new Date().getTime()));
            dom.select("#link_table").append("<tr><td><a href=\"" + source + "\">"+ ts +"</a></td></tr>");
            if(!index.canWrite()){
                System.out.println("Can't write this file!");
            }
            BufferedWriter bw = null;
            bw = new BufferedWriter(new FileWriter(index));
            bw.write(dom.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
