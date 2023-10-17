package com.myblog10.service;


import com.myblog10.payload.PostDto;
import com.myblog10.payload.PostResponse;


import java.util.List;

public interface PostService {
    PostDto savePost(PostDto postDto);


    void deletePost(long id);

    public PostDto updatePost(long id, PostDto postDto);

PostDto getPostById(long id);


    PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir);
}
