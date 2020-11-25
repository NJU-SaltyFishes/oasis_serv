package nju.oasis.serv.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CollaborationPublication {

    @JSONField(name = "affiliation_id",ordinal = 0)
    private long affiliationId;

    @JSONField(name = "affiliation_name",ordinal = 1)
    private String affiliationName;


    @JSONField(name = "collaboration_count",ordinal = 2)
    private int collaborationCount;
}
