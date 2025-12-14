package com.austine.blog.model;

import com.austine.blog.model.auth.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "blog")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="TITLE")
    private String title;

    @Column(name="IMAGE_NAME")
    private String imageName;

    @Column(name="IMAGE_URL")
    private String imageUrl;

    @Column(name="DECRIPTION")
    private String description;

    @Column(name="SLUG")
    private String slug;



    @Column(name="EXERPT")
    private String excerpt;

    @Column(name="IMAGE")
    private byte[] image;

    @Column(name="DATE")
    private Date dateCreated;

    @Column(name="APPROVE")
    private boolean approved;

    @JoinColumn(name = "CATEGORY", referencedColumnName = "ID")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Category categoryId;

    @JoinColumn(name = "CREATED_BY", referencedColumnName = "id")
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private User createdBy;


    public Post() {
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

//    public User getUserId() {
//        return userId;
//    }
//
//    public void setUserId(User userId) {
//        this.userId = userId;
//    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
