package nju.oasis.serv.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@NoArgsConstructor
public class Article {

    private long id;

    private String title;

    private String abstractContent;

    private int referenceCount;

    private int citationCount;

    private String publication;

    private String publisher;

    private Date date;

    private String pdfLink;

    private int totalUsage;
}
