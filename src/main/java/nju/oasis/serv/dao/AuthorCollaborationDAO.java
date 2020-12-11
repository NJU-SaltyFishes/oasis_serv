package nju.oasis.serv.dao;

import nju.oasis.serv.domain.AuthorCollaboration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthorCollaborationDAO {

    List<AuthorCollaboration> findAuthorCollaborationByAuthorId(
            @Param("authorId") long authorId, @Param("minDistance") double minDistance,
            @Param("maxDistance") double maxDistance,@Param("maxNum")int maxNum);
}
