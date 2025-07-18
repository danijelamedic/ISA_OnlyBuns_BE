package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.LikeDto;
import com.onlybuns.isa.model.Like;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.repository.LikeRepository;
import com.onlybuns.isa.repository.PostRepository;
import com.onlybuns.isa.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    public List<Like> findByPostId(Long postId) { return likeRepository.findByPostId(postId); }
    public Like save(Like like) { return likeRepository.save(like); }
    public boolean existsByPostAndUser(Post post, User user) { return likeRepository.existsByPostAndUser(post, user); }

    @Transactional
    public LikeDto likePostWithLock(Long postId, Long userId) throws InterruptedException {
        System.out.println("User " + userId + " pokušava da lajkuje post " + postId + " - počinje transakciju i zaključavanje");

        // 1. Zaključaj post za pisanje (pessimistic write lock)
        Post post = em.find(Post.class, postId, LockModeType.PESSIMISTIC_WRITE);

        // 2. Proveri da li je korisnik već lajkovao post (da ne duplira lajk)
        User user = em.find(User.class, userId);
        boolean alreadyLiked = post.getLikes().stream()
                .anyMatch(like -> like.getUser().getId().equals(userId));

        if (alreadyLiked) {
            System.out.println("User " + userId + " je već lajkovao post " + postId + ", bacam exception");
            throw new IllegalStateException("User has already liked this post");
        }

        System.out.println("User " + userId + " spava 1s u transakciji (simulacija konkurentnog pristupa)");
        Thread.sleep(1000);
        System.out.println("User " + userId + " je završio spavanje i nastavlja sa lajkovanjem");

        // 4. Kreiraj i dodaj lajk
        Like like = new Like();
        like.setPost(post);
        like.setUser(user);

        post.addLike(like);

        // 5. Sa CascadeType.ALL, dovoljno je da sačuvaš post
        postRepository.save(post);

        // 6. Update aktivnosti korisnika
        user.setLastActivityTime(LocalDateTime.now());
        userRepository.save(user);

        System.out.println("User " + userId + " je uspešno lajkovao post " + postId);

        return new LikeDto(like);
    }


    @Transactional
    public LikeDto likePost(Long postId, Long userId) throws InterruptedException {
        Post post = em.find(Post.class, postId, LockModeType.PESSIMISTIC_WRITE);

        User user = em.find(User.class, userId);

        boolean alreadyLiked = post.getLikes().stream()
                .anyMatch(like -> like.getUser().getId().equals(userId));

        if (alreadyLiked) {
            throw new IllegalStateException("User has already liked this post");
        }

        Thread.sleep(1000); // simulacija konkurencije

        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        post.addLike(like);

        postRepository.save(post);

        user.setLastActivityTime(LocalDateTime.now());
        userRepository.save(user);

        return new LikeDto(like);
    }


    public int countNewLikes(Long userId, LocalDateTime since) {
        if (since == null) {
            // Ako nema vremena od kojeg računamo, možeš vratiti ukupno ili 0
            return 0;
        }
        // Broj lajkova na postove koje je korisnik kreirao od datuma "since"
        return likeRepository.countByPostUserIdAndCreationTimeAfter(userId, since);
    }
}
