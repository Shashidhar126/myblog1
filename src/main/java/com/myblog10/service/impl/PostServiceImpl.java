package com.myblog10.service.impl;

import com.myblog10.entity.Post;

import com.myblog10.exception.ResourceNotFound;
import com.myblog10.payload.PostDto;
import com.myblog10.payload.PostResponse;
import com.myblog10.repository.PostRepository;

import com.myblog10.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelmapper) {
        this.postRepository = postRepository;
        this.modelmapper = modelmapper;
    }

    private PostRepository postRepository;

    private ModelMapper modelmapper;
    @Override
    public PostDto savePost(PostDto postDto) {

        Post post = mapToEntity(postDto);//converting dto to entity
        Post savedPost=postRepository.save(post);
        PostDto dto = mapToDto(savedPost);//converting entity to dto

        return dto;
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post =postRepository.findById(id).orElseThrow(

                ()->new ResourceNotFound("Post not found with id:"+id)//iam just throwing exception
        );//finding post by id,if not found throw exception
        post.setTitle(postDto.getTitle());//set new dto content into entity object
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post updatePost = postRepository.save(post);//save the edited post back to database
        PostDto dto = mapToDto(updatePost);//converting entity to dto
        return dto;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post =postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFound("Post not found with id:"+id)
        );
        PostDto dto = mapToDto(post);
        return dto;
    }

    @Override
    public PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

         Pageable pageable= PageRequest.of(pageNo, pageSize,sort);//pagerequest is a built in class and has lot of overloaded methods,it gives pageable objects


        Page<Post> pagePosts = postRepository.findAll(pageable);//it will return back page of posts


        List<Post> posts = pagePosts.getContent();



        List<PostDto> postDtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());



        PostResponse postResponse=new PostResponse();
        postResponse.setPostDto(postDtos);
        postResponse.setPageNo(pagePosts.getNumber());//pageposts has a method called get method
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setLast(pagePosts.isLast());
        postResponse.setTotalPages(pagePosts.getTotalPages());



        return postResponse;
    }

    PostDto mapToDto(Post post){
        PostDto dto = modelmapper.map(post, PostDto.class);
//        PostDto dto=new PostDto();
//        dto.setId(post.getId());
//        dto.setContent(post.getContent());
//        dto.setDescription(post.getDescription());
//        dto.setTitle(post.getTitle());
        return  dto;
    }
    Post mapToEntity(PostDto postDto){
        Post post = modelmapper.map(postDto, Post.class);
//        Post post=new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return  post;
    }
}

