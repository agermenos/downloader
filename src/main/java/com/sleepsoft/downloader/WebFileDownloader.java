package com.sleepsoft.downloader;

/**
 * Created by Alejandro on 3/29/16.
 */
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class WebFileDownloader {

    public static void main(String[] args) {
        WebFileDownloader wfd = new WebFileDownloader();
        String url = "http://www.journaldev.com/sitemap.xml";

        try {
            downloadUsingNIO(url, "/Users/Alejandro/sitemap.xml");

            downloadUsingStream(url, "/Users/Alejandro/sitemap_stream.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadUsingStream(String urlStr, String file) throws IOException{
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

    public static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

}
