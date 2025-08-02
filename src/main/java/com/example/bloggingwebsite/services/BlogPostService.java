package com.example.bloggingwebsite.services;

import com.example.bloggingwebsite.models.BlogPost;
import com.example.bloggingwebsite.models.User;
import com.example.bloggingwebsite.payload.BlogPostDto;
import com.example.bloggingwebsite.repository.BlogPostRepository;
import com.example.bloggingwebsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogPostService {
    @Autowired
    private BlogPostRepository blogPostRepository;
    
    @Autowired
    private UserRepository userRepository;

    public List<BlogPostDto> getAllPosts() {
        List<BlogPost> blogPosts = blogPostRepository.findAll();
        return blogPosts.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public BlogPostDto getPostById(Long id) {
        BlogPost blogPost = blogPostRepository.findById(id).orElse(new BlogPost());
        return mapToDto(blogPost);
    }

    public String createPost(BlogPostDto blogPostDto) {
        BlogPost blogPost = mapToEntity(blogPostDto);
        
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        blogPost.setUser(currentUser);
        blogPost.setCreatedAt(LocalDateTime.now());
        blogPost.setUpdatedAt(LocalDateTime.now());
        
        blogPostRepository.save(blogPost);
        return "Blog posted successfully";
    }

    public BlogPostDto updatePost(Long id, BlogPostDto updatedPostDto) {
        BlogPost existingPost = blogPostRepository.getBlogPostsById(id);
        
        existingPost.setTitle(updatedPostDto.getTitle());
        existingPost.setContent(updatedPostDto.getContent());
        existingPost.setAuthor(updatedPostDto.getAuthor());
        existingPost.setUpdatedAt(LocalDateTime.now());
        
        BlogPost updatedPost = blogPostRepository.save(existingPost);
        return mapToDto(updatedPost);
    }

    public String deletePost(Long id) {
        blogPostRepository.deleteById(id);
        return "Blog deleted successfully";
    }

    public List<BlogPostDto> searchPosts(String title) {
        List<BlogPost> blogPosts = blogPostRepository.findByTitleContainingIgnoreCase(title);
        return blogPosts.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    // Convert Entity to DTO
    private BlogPostDto mapToDto(BlogPost blogPost) {
        BlogPostDto blogPostDto = new BlogPostDto();
        blogPostDto.setId(blogPost.getId());
        blogPostDto.setTitle(blogPost.getTitle());
        blogPostDto.setContent(blogPost.getContent());
        blogPostDto.setAuthor(blogPost.getAuthor());
        blogPostDto.setCreatedAt(blogPost.getCreatedAt());
        blogPostDto.setUpdatedAt(blogPost.getUpdatedAt());
        if (blogPost.getUser() != null) {
            blogPostDto.setUserId(blogPost.getUser().getId());
        }
        return blogPostDto;
    }
    
    // Convert DTO to Entity
    private BlogPost mapToEntity(BlogPostDto blogPostDto) {
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(blogPostDto.getTitle());
        blogPost.setContent(blogPostDto.getContent());
        blogPost.setAuthor(blogPostDto.getAuthor());
        
        if (blogPostDto.getId() != null) {
            blogPost.setId(blogPostDto.getId());
        }
        
        return blogPost;
    }
}
