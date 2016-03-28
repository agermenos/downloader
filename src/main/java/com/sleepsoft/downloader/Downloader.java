package com.sleepsoft.downloader;

import com.sleepsoft.downloader.implementations.WebListReader;
import com.sleepsoft.downloader.interfaces.IListReader;
import com.sleepsoft.downloader.pojos.Series;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public void runTest(){
        List<Series> items = (List<Series>) dummyFileReader.getList("fake list");
        items.stream().
                map(m -> m.getTitle() + " s" + String.format("%02d", m.getSeason()) + "e" + String.format("%02d", m.getEpisode())).
                forEach(searchItem -> searchForItem(searchItem));
    }

    private void searchForItem(String searchItem) {
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("searchCriteria", searchItem);
        StringBuffer searchUri = new StringBuffer(WebListReader.BASE);
        searchUri.append("/{searchCriteria}/");
        String result = restTemplate.getForObject(searchUri.toString(), String.class, vars);
        Document doc = Jsoup.parse(result);

        // Searches the Document for the Magnet Title
        Elements tds = doc.getElementsByAttributeValue(WebListReader.MAGNET_ATTRIBUTE, WebListReader.MAGNET_VALUE);
    }

    public static void main(String args[]){
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "/spring/beanlocations.xml");
        Downloader downloader = (Downloader) context.getBean("downloader");
        downloader.runTest();
    }

}
