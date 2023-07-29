package com.example.demo.Controllers;

import com.example.demo.Model.Post;
import com.example.demo.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("title","This page");
        return "home";
    }


    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("title","About Page");
        return "about";
    }


    @GetMapping("/blog")
    public String blog(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts",posts);
        return "blog-main";
    }


    @GetMapping("blog/add")
    String addBlogg(){
        return "blog-add";
    }


    @PostMapping("/blog/add")
    String saveBlogg(@RequestParam String title,
                     @RequestParam String anons,
                     @RequestParam String full_text, Model model){

        postRepository.save(new Post(title, anons, full_text));
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    String blogDetails(@PathVariable(value = "id") long  id, Model model){
        if (!postRepository.existsById(id))
            return "redirect:/blog";
        Optional<Post> res = postRepository.findById(id);
        ArrayList<Post> post = new ArrayList<>();
        res.ifPresent(post :: add);
        model.addAttribute("post", post);
        return "blog-details";
    }


    @GetMapping("/blog/{id}/edit")
    String blogEditSave(@PathVariable(value = "id") long id, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        ArrayList<Post> p = new ArrayList<>();
        p.add(post);
        model.addAttribute("onePost",p);
        return "blog-edit";
    }


    @PostMapping("/blog/{id}/edit")
    String saveEditSave(@PathVariable(value = "id") long id,
                        @RequestParam String title,
                        @RequestParam String anons,
                        @RequestParam String full_text){

        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);

        return "redirect:/blog";
    }


    @PostMapping("/blog/{id}/remove")
    String blogDelete(@PathVariable(value = "id") long id){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }

}
