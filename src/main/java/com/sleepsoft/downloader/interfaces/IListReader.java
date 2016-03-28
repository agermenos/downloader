package com.sleepsoft.downloader.interfaces;

import com.sleepsoft.downloader.pojos.Item;

import java.util.List;

/**
 * Created by Alejandro on 3/20/16.
 */
public interface IListReader {
    public List<? extends Item> getList(String source);

}
