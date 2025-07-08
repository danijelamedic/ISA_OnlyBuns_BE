package com.onlybuns.isa.repository;

import com.onlybuns.isa.dto.UserDto;
import com.onlybuns.isa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findById(long id); //kaze gpt nepotrebno ako vec nasledjuje jparepository??
   // User create(User user);
    public Page<User> findAll(Pageable pageable);
    public List<User> findAll();
    public List<User> findByNameContainingIgnoreCase(String name);
    public List<User> findBySurnameContainingIgnoreCase(String surname);
    public User findByEmailContainingIgnoreCase(String email);
    @Query("SELECT u FROM User u WHERE (SELECT COUNT(p) FROM Post p WHERE p.user = u) BETWEEN :min AND :max")
    public List<User> findByPostsNumber(@Param("min") int min, @Param("max") int max);
    public List<User> findAllByOrderByEmailAsc();
    Optional<User> findByUsername(String username);
    //Optional<User> findByActivationToken(String activationToken);
    long count();
    List<User> findByLastLoginTimeBefore(LocalDateTime dateTime);

}
