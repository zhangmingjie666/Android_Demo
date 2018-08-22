package com.example.nrbzms17.data.model;

import java.io.Serializable;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.data.model
 * @filename StraightBean
 * @date on 2018/8/22 18:12
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class StraightBean implements Serializable {


    public String id;
    public String name;
    public StraightBean(String id,String name){
        this.id = id;
        this.name =name;
    }

}
