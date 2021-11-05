package com.karrotmvp.ourapt.v1.article.comment.repository;


import com.karrotmvp.ourapt.v1.article.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String>, CommentCustomRepository<Comment, String> {
//  @Query(
//    "SELECT new com.karrotmvp.ourapt.v1.article.comment.repository.projection.CommentCount(c.parent.id, COUNT(c)) " +
//      "FROM Comment c " +
//      "WHERE c.parent.id IN :parentIds GROUP BY c.parent.id")
//  List<CommentCount> findCountPerParentIdIn(@Param("parentIds") Set<String> parentIds);
//
//  long countByParentId(String parentId);
}
