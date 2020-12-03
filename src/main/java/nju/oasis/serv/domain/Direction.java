package nju.oasis.serv.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Direction {

    @JSONField(name = "keyword_id",ordinal = 0)
    private long directionId;

    @JSONField(name = "keyword_desc",ordinal = 1)
    private String name;

    @JSONField(name = "keyword_appear_num",ordinal = 2)
    private int appearNum;
}
