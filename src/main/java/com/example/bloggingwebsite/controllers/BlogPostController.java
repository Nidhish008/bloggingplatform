package com.example.bloggingwebsite.controllers;

import com.example.bloggingwebsite.models.BlogPost;
import com.example.bloggingwebsite.payload.BlogPostDto;
import com.example.bloggingwebsite.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {
    @Autowired
    private BlogPostService blogPostService;

    @GetMapping
    public List<BlogPostDto> getAllPosts() {
        return blogPostService.getAllPosts();
    }

    @GetMapping("/{id}")
    public BlogPostDto getPostById(@PathVariable Long id) {
        return blogPostService.getPostById(id);
    }

    @PostMapping
    public String createPost(@RequestBody BlogPostDto blogPostDto) {
        return blogPostService.createPost(blogPostDto);
    }

    @PutMapping("/{id}")
    public BlogPostDto updatePost(@PathVariable Long id, @RequestBody BlogPostDto updatedPostDto) {
        return blogPostService.updatePost(id, updatedPostDto);
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        return blogPostService.deletePost(id);
    }

    @GetMapping("/search")
    public List<BlogPostDto> searchPosts(@RequestParam String title) {
        return blogPostService.searchPosts(title);
    }
}
