package com.onlybuns.isa.service;

import com.onlybuns.isa.dto.PostDto;
import com.onlybuns.isa.model.Post;

public interface IPostService {
    Post create(PostDto post) throws Exception;
    //void likePost(Long postId) throws Exception;
}
