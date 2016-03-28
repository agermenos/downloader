package com.sleepsoft.downloader.implementations;

import com.sleepsoft.downloader.pojos.Item;
import com.sleepsoft.downloader.interfaces.IListReader;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Alejandro on 3/27/16.
 */
@Service("webListReader")
public class WebListReader implements IListReader {
    public static final String BASE = "https://kat.cr/usearch/";
    public static final String MAGNET_ATTRIBUTE = "title";
    public static final String MAGNET_VALUE = "Torrent magnet link";

    public List<? extends Item> getList(String source) {
        return null;
    }
}
