package nju.oasis.serv.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CollaborationAffiliation {

    @JSONField(name = "affiliation_id",ordinal = 0)
    private long affiliationId;

    @JSONField(name = "affiliation_name",ordinal = 1)
    private String name;


    @JSONField(name = "collaboration_count",ordinal = 2)
    private int collaborationNum;
}
