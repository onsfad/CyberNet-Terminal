package com.austine.blog.service;

import com.austine.blog.Dto.PostDto;
import com.austine.blog.MessageUtil.ApiResponse;
import com.austine.blog.MessageUtil.CustomMessages;
import com.austine.blog.model.Category;
import com.austine.blog.model.Post;
import com.austine.blog.repository.CategoryRepository;
import com.austine.blog.repository.PageRepository;
import com.austine.blog.repository.PostRespository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Transactional
@Service
public class AppService {

    @Autowired
    private PostRespository postRespository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PageRepository pageRepository;


    public ResponseEntity saveSupplyOrder(@RequestParam("image") MultipartFile file, @RequestParam("postDto") String postDto) throws JsonParseException, JsonMappingException, IOException {


        PostDto newPost = new ObjectMapper().readValue(postDto, PostDto.class);
        Optional<Category> categoryName = categoryRepository.findByCategoryName(newPost.getCategoryId());
        Post post = new Post();
        post.setCategoryId(categoryName.get());
        post.setDateCreated(new Date());
        post.setDescription(newPost.getDescription());
        post.setSlug(newPost.getSlug());
        post.setTitle(newPost.getTitle());
        post.setExcerpt(newPost.getExcerpt());
        post.setImage(file.getBytes());
        post.setImageName(file.getOriginalFilename());
        Post post1 = postRespository.save(post);
        if (post1 != null) {
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, HttpStatus.OK));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.NotSaved, HttpStatus.BAD_REQUEST));
        }
    }

    @Autowired
    private ServletContext context;


    public ResponseEntity savePostToServer(@RequestParam("image") MultipartFile file, @RequestParam("postDto") String postDto) throws JsonParseException, JsonMappingException, IOException {


        PostDto newPost = new ObjectMapper().readValue(postDto, PostDto.class);
        Optional<Category> categoryName = categoryRepository.findByCategoryName(newPost.getCategoryId());
        boolean isExist = new File(context.getRealPath("/postimages/")).exists();
        if (!isExist) {
            new File(context.getRealPath("/postimages/")).mkdir();
        }

        String filename = file.getOriginalFilename();
        String modifiedFileName = FilenameUtils.getBaseName(filename) + "_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(filename);
        File serverfile = new File(context.getRealPath("/postimages/" + File.separator + modifiedFileName));
        try {
            FileUtils.writeByteArrayToFile(serverfile, file.getBytes());


        } catch (Exception e) {
            e.printStackTrace();
        }
        Post post = new Post();
        post.setCategoryId(categoryName.get());
        post.setDateCreated(new Date());
        post.setDescription(newPost.getDescription());
        post.setSlug(newPost.getSlug());
        post.setTitle(newPost.getTitle());
        post.setExcerpt(newPost.getExcerpt());
//        post.setImage(file.getBytes());
        post.setImageName(modifiedFileName);
        post.setApproved(newPost.isApproved());
        post.setImageUrl(newPost.getImageUrl());
        post.setCreatedBy(newPost.getCreatedBy());
        Post post2 = postRespository.save(post);
        if (post2 != null) {
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, HttpStatus.OK));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.NotSaved, HttpStatus.BAD_REQUEST));
        }
    }


    public PostRespository getPostRespository() {
        return postRespository;
    }

    public void setPostRespository(PostRespository postRespository) {
        this.postRespository = postRespository;
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public PageRepository getPageRepository() {
        return pageRepository;
    }

    public void setPageRepository(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }
}
