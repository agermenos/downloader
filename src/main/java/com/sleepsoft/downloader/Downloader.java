package com.sleepsoft.downloader;

import com.sleepsoft.downloader.implementations.WebListReader;
import com.sleepsoft.downloader.interfaces.IListReader;
import com.sleepsoft.downloader.pojos.Series;
import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alejandro on 3/20/16.
 */

@Service("downloader")
public class Downloader {
    @Autowired
    IListReader dummyFileReader;
    @Autowired
    RestTemplate restTemplate;

    private String mainPath = "/Users/Alejandro/output/";
    private String torrentPath = "/Users/Alejandro/torrents/";

    public void runTest() throws IOException, NoSuchAlgorithmException {
        List<Series> items = (List<Series>) dummyFileReader.getList("fake list");
        for (Series item:items) {
            String episode = item.getTitle() + " s" + String.format("%02d", item.getSeason()) + "e" + String.format("%02d", item.getEpisode());
            addToDownloader(episode, item.getOutput());
        }
//        items.stream().
//                map(m -> m.getTitle() + " s" + String.format("%02d", m.getSeason()) + "e" + String.format("%02d", m.getEpisode())).
//                forEach(searchItem -> searchForItem(searchItem));
    }

    private void addToDownloader(String itemSearch, String pathToDownload) throws IOException, NoSuchAlgorithmException {
        String href = searchForItem(itemSearch);
        // Download the torrent file

        WebFileDownloader.downloadUsingNIO(href, torrentPath + pathToDownload + ".torrent");
        // First, instantiate the Client object.
        Client client = new Client(
            // This is the interface the client will listen on (you might need something
            // else than localhost here).
            InetAddress.getLocalHost(),

            // Load the torrent from the torrent file and use the given
            // output directory. Partials downloads are automatically recovered.
            SharedTorrent.fromFile(
                    new File(torrentPath + pathToDownload + ".torrent"),
                    new File(mainPath + pathToDownload)));

        // You can optionally set download/upload rate limits
        // in kB/second. Setting a limit to 0.0 disables rate
        // limits.
        client.setMaxDownloadRate(0);
        client.setMaxUploadRate(5.0);

        // At this point, can you either call download() to download the torrent and
        // stop immediately after...
        client.download();

        // Or call client.share(...) with a seed time in seconds:
        // client.share(3600);
        // Which would seed the torrent for an hour after the download is complete.

        // Downloading and seeding is done in background threads.
        // To wait for this process to finish, call:
        client.waitForCompletion();

        // At any time you can call client.stop() to interrupt the download.
    }

    private String searchForItem(String searchItem) {
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("searchCriteria", searchItem);
        StringBuffer searchUri = new StringBuffer(WebListReader.BASE);
        searchUri.append("/{searchCriteria}/");
        String result = restTemplate.getForObject(searchUri.toString(), String.class, vars);
        Document doc = Jsoup.parse(result);

        // Searches the Document for the Magnet Title
        Elements tds = doc.getElementsByAttributeValue(WebListReader.MAGNET_ATTRIBUTE, WebListReader.DOWNLOAD_FILE);
        return ("https:" + tds.get(0).attr("href"));

    }

    public static void main(String args[]){
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "/spring/beanlocations.xml");
        Downloader downloader = (Downloader) context.getBean("downloader");
        try {
            downloader.runTest();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
