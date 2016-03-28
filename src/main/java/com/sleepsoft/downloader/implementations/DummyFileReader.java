package com.sleepsoft.downloader.implementations;

import com.sleepsoft.downloader.interfaces.IListReader;
import com.sleepsoft.downloader.pojos.Item;
import com.sleepsoft.downloader.pojos.Series;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro on 3/27/16.
 */
@Service("dummyFileReader")
public class DummyFileReader implements IListReader {
    public List<? extends Item> getList(String source) {
        List<Series> selection = new ArrayList<>();
        selection.add(new Series(2,7,"The Flash", 2016));
        selection.add(new Series(1,17,"Supergirl", 2016));
        return selection;
    }
}
