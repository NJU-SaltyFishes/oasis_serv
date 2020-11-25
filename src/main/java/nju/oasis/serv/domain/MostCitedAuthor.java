package nju.oasis.serv.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MostCitedAuthor {

    @JSONField(name = "author_id",ordinal = 0)
    private long authorId;

    @JSONField(name = "author_name",ordinal = 1)
    private String authorName;

    @JSONField(name = "cited_num",ordinal = 2)
    private int citedNum;
}
