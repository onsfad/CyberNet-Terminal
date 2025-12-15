package com.austine.blog.controller;

import com.austine.blog.Dto.PostDto;
import com.austine.blog.MessageUtil.ApiResponse;
import com.austine.blog.MessageUtil.CustomMessages;
import com.austine.blog.exceptions.RecordNotFoundException;
import com.austine.blog.model.Post;
import com.austine.blog.service.AppService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private AppService service;



    @ApiOperation("To Create a Post record and save in the server")
    @PostMapping(value = "/create")
    public ResponseEntity createPost(@RequestParam("image") MultipartFile file, @RequestParam ("postDto") String postDto) throws IOException{
        return service.savePostToServer(file, postDto);
    }


    @ApiOperation("To Approve Post")
    @PostMapping(value = "/approve/post", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPurchaseOrder(@RequestBody Post post) {
        Post ins = service.getPostRespository().save(post);
        return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, ins));
    }


    @ApiOperation("To Create a Post record and save in the db")
    @PostMapping(value = "/createPost")
    public ResponseEntity addPost(@RequestParam("image") MultipartFile file, @RequestParam ("postDto") String postDto) throws IOException{
        return service.saveSupplyOrder(file, postDto);
    }

    @ApiOperation("To return all Post")
    @GetMapping("/allPosts")
    public List<Post> getAllRecords(){
        return service.getPostRespository().findAll();
    }



    @ApiOperation("To return a post by ID")
    @GetMapping("/Post/{id}")
    public ResponseEntity getPostById(@PathVariable("id") Long id){
        return service.getPostRespository().findById(id).map(record->{
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, record));
        }).orElseThrow(()-> new RecordNotFoundException("No Post Found for: "+ id));
    }

    @ApiOperation("To delete a Post by ID")
    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity deletePostById(@PathVariable("id")Long id){
        return service.getPostRespository().findById(id)
                .map(record-> {
                    service.getPostRespository().deleteById(id);
                    return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Deleted, CustomMessages.DeletedMessage));
                }).orElseThrow(()-> new RecordNotFoundException("No Post Found for: "+ id));
    }

}
