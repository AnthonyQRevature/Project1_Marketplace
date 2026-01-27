package project.controller.model;

import java.sql.Date;
import java.util.List;

public class PostModel {
    private Integer id;
    private Integer sellerId;
    private String description;
    private Double price;
    private Date created_at;
    private Date last_edit_time;
    private List<String> media;
    private List<String> tags;
    private Double latitudeOfSeller;
    private Double longitudeOfSeller;
    private Integer postStatus;

    public PostModel() {}

    public PostModel(Integer postStatus, Double longitudeOfSeller, Double latitudeOfSeller, List<String> tags, List<String> media, Date last_edit_time, Date created_at, Double price, String description, Integer sellerId, Integer id) {
        this.postStatus = postStatus;
        this.longitudeOfSeller = longitudeOfSeller;
        this.latitudeOfSeller = latitudeOfSeller;
        this.tags = tags;
        this.media = media;
        this.last_edit_time = last_edit_time;
        this.created_at = created_at;
        this.price = price;
        this.description = description;
        this.sellerId = sellerId;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getLast_edit_time() {
        return last_edit_time;
    }

    public void setLast_edit_time(Date last_edit_time) {
        this.last_edit_time = last_edit_time;
    }

    public List<String> getMedia() {
        return media;
    }

    public void setMedia(List<String> media) {
        this.media = media;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Double getLatitudeOfSeller() {
        return latitudeOfSeller;
    }

    public void setLatitudeOfSeller(Double latitudeOfSeller) {
        this.latitudeOfSeller = latitudeOfSeller;
    }

    public Double getLongitudeOfSeller() {
        return longitudeOfSeller;
    }

    public void setLongitudeOfSeller(Double longitudeOfSeller) {
        this.longitudeOfSeller = longitudeOfSeller;
    }

    public Integer getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(Integer postStatus) {
        this.postStatus = postStatus;
    }
}
