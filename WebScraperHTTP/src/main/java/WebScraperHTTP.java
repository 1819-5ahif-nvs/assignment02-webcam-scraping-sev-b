import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;
import java.util.StringTokenizer;

public class WebScraperHTTP {

    public static final String DEFAULT_FILE = "index.html";
    private static final int PORT = 8080;
    // verbose mode
    public static final boolean verbose = true;

    public static void main(String[] args){
        try {
            ServerSocket serverConnect = new ServerSocket(PORT);

            IndexWriter indexWriter = new IndexWriter();
            Thread writerThread = new Thread(indexWriter);
            writerThread.start();
            // we listen until user halts server execution
            while (true) {
                WebScraperServer httpServer = new WebScraperServer(serverConnect.accept());

                if (verbose) {
                    System.out.println("Connecton opened. (" + new Date() + ")");
                }

                Thread serverThread = new Thread(httpServer);
                serverThread.start();
            }

        } catch (IOException e) {
            System.err.println("Server Connection error : " + e.getMessage());
        }
    }
}
