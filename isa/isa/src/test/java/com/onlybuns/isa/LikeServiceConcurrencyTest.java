package com.onlybuns.isa;

import static org.junit.jupiter.api.Assertions.*;

import com.onlybuns.isa.dto.LikeDto;
import com.onlybuns.isa.model.Post;
import com.onlybuns.isa.model.User;
import com.onlybuns.isa.repository.PostRepository;
import com.onlybuns.isa.repository.UserRepository;
import com.onlybuns.isa.service.LikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;


import java.util.concurrent.*;
import java.util.List;
@SpringBootTest
public class LikeServiceConcurrencyTest {

    @Autowired
    private LikeService likeService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private Long postId;
    private Long userId1;
    private Long userId2;

    @BeforeEach
    public void setup() {
        User user1 = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("User 1 not found"));
        User user2 = userRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("User 2 not found"));

        userId1 = user1.getId();
        userId2 = user2.getId();

        User postOwner = userRepository.findById(3L)
                .orElseThrow(() -> new RuntimeException("Post owner not found"));

        Post post = new Post();
        post.setDescription("Test post");
        post.setUser(postOwner);
        postRepository.save(post);

        postId = post.getId();
    }




    @Test
    public void testConcurrentLikes() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<LikeDto> user1Task = () -> likeService.likePost(postId, userId1);
        Callable<LikeDto> user2Task = () -> likeService.likePost(postId, userId2);

        Future<LikeDto> future1 = executor.submit(user1Task);
        Future<LikeDto> future2 = executor.submit(user2Task);

        LikeDto like1 = future1.get();
        LikeDto like2 = future2.get();

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        assertNotNull(like1);
        assertNotNull(like2);
        assertNotEquals(like1.getUser(), like2.getUser());

        List<?> likes = likeService.findByPostId(postId);
        assertEquals(2, likes.size());
    }
}
