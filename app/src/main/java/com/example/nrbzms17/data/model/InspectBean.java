package com.example.nrbzms17.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.data.model
 * @filename InspectBean
 * @date on 2018/8/2 14:05
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class InspectBean implements Serializable {
    public List<Data> result;

    public class Data implements Serializable {

        public String code;
        public String customcode;
        public String companyname;
        public String materialname;
        public String craftname;
        public String colorname;
        public String volume;
        public String quantity;
        public String depot;
        public String typeName;
        public String status;
        public String checkunit;
        public String dyelot;
        public String employee;
        public String billdate;

    }
}
