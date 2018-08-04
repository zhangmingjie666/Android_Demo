package com.example.nrbzms17.data.model;

import java.io.Serializable;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.data.model
 * @filename AddressBean
 * @date on 2018/8/4 17:13
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class AddressBean implements Serializable {

    public String id;
    public String name;
    public String url;

    public AddressBean(String id, String name, String url) {

        this.id = id;
        this.name = name;
        this.url = url;
    }

}
