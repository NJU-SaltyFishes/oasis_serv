package nju.oasis.serv.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AffiliationInfo {

    private long affiliationId;

    private String affiliationName;

    private double averageCitationPerArticle;

    private int citationCount;

    private int publicationCount;

    private int startYear;

    private int endYear;

    private int availableDownload;

    private double averageDownloadPerArticle;

}
