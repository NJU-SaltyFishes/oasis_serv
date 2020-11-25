package nju.oasis.serv.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AffiliationPublication {

    @JSONField(name = "2010",ordinal = 0)
    private int count2010;

    @JSONField(name = "2011",ordinal = 1)
    private int count2011;

    @JSONField(name = "2012",ordinal = 2)
    private int count2012;

    @JSONField(name = "2013",ordinal = 3)
    private int count2013;

    @JSONField(name = "2014",ordinal = 4)
    private int count2014;

    @JSONField(name = "2015",ordinal = 5)
    private int count2015;

    @JSONField(name = "2016",ordinal = 6)
    private int count2016;

    @JSONField(name = "2017",ordinal = 7)
    private int count2017;

    @JSONField(name = "2018",ordinal = 8)
    private int count2018;

    @JSONField(name = "2019",ordinal = 9)
    private int count2019;

    @JSONField(name = "2020",ordinal = 10)
    private int count2020;

    @JSONField(name = "2021",ordinal = 11)
    private int count2021;
}
