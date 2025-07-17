package com.onlybuns.isa.repository;

import com.onlybuns.isa.model.Comment;
import com.onlybuns.isa.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.time.LocalDateTime;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findByUserId(Long userId);
    @Query("SELECT COUNT(c) FROM Comment c WHERE MONTH(c.creationTime) = :month AND YEAR(c.creationTime) = :year")
    int countPerMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT COUNT(c) FROM Comment c WHERE WEEK(c.creationTime) = :week AND YEAR(c.creationTime) = :year")
    int countPerWeek(@Param("week") int week, @Param("year") int year);

    @Query("SELECT COUNT(c) FROM Comment c WHERE YEAR(c.creationTime) = :year")
    int countPerYear(@Param("year") int year);

    List<Comment> findByPostIdOrderByCreationTimeAsc(Long postId);

    int countByPostUserIdAndCreationTimeAfter(Long userId, LocalDateTime since);
}
