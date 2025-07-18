package com.onlybuns.isa.repository;

import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.User;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
   // User create(User user);
    public Page<User> findAll(Pageable pageable);
    public List<User> findAll();
    public List<User> findByNameContainingIgnoreCase(String name);
    public List<User> findBySurnameContainingIgnoreCase(String surname);
    public List<User> findByEmailContainingIgnoreCase(String email);
    @Query("SELECT u FROM User u WHERE (SELECT COUNT(p) FROM Post p WHERE p.user = u) BETWEEN :min AND :max")
    public List<User> findByPostsNumber(@Param("min") int min, @Param("max") int max);
    public Page<User> findAllByOrderByEmailAsc(Pageable pageable);
    Optional<User> findByUsername(String username);
    //Optional<User> findByActivationToken(String activationToken);
    long count();
    List<User> findByLastLoginTimeBefore(LocalDateTime dateTime);
    List<User> findByActivatedFalse();
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select * from users where id = :id for update", nativeQuery = true)
    User findByIdForUpdate(@Param("id") Long id);

    @Query("SELECT COUNT(u) FROM User u WHERE u.lastActivityTime >= :since")
    int countActiveUsersSince(@Param("since") LocalDateTime since);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);



}
