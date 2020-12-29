package nju.oasis.serv.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DirectionDAO {

    List<String>getDirectionsByPrefix(String prefix);
}
