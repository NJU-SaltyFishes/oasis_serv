package nju.oasis.serv.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PublicationYear {

    private int year;

    private int amount;

    public PublicationYear(int year,int amount){
        this.year = year;
        this.amount = amount;
    }
}
