package com.example.bloggingwebsite.repository;

import com.example.bloggingwebsite.models.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    List<BlogPost> findByTitleContainingIgnoreCase(String title);
    BlogPost getBlogPostsById(Long id);
}
