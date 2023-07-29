package com.example.demo.Repository;

import com.example.demo.Model.Post;
import org.springframework.data.repository.CrudRepository;


public interface PostRepository extends CrudRepository<Post,Long> {
}
