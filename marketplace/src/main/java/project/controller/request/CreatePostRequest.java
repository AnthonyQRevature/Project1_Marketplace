package project.controller.request;

import java.util.List;
import java.util.List;
public class CreatePostRequest {

    private String description;
    private double price;
    private String status; // available, unlisted, sold
    private List<Integer> tags;
    private List<Media> media;


    public static class Media {
        private String media_encoded;
        private String media_type; // "image" or "video"

        public Media() {}

        public Media(String media_encoded, String media_type) {
            this.media_encoded = media_encoded;
            this.media_type = media_type;
        }

        public String getMedia_encoded() {
            return media_encoded;
        }

        public void setMedia_encoded(String media_encoded) {
            this.media_encoded = media_encoded;
        }

        public String getMedia_type() {
            return media_type;
        }

        public void setMedia_type(String media_type) {
            this.media_type = media_type;
        }
    }

    public CreatePostRequest() {}

    public CreatePostRequest(String description, double price, String status, List<Integer> tags, List<Media> media) {
        this.description = description;
        this.price = price;
        this.status = status;
        this.tags = tags;
        this.media = media;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }
}


