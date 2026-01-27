package project.controller.response;



import java.util.List;

public class ListingResponse {

    public Integer id;
    public String description;
    public double price;
    public String status;
    public List<MediaResponse> media;
    public List<TagResponse> tags;

    public static class MediaResponse {
        public String mediaEncoded;
        public String mediaType;

        public MediaResponse(String mediaEncoded, String mediaType) {
            this.mediaEncoded = mediaEncoded;
            this.mediaType = mediaType;
        }
    }

    public static class TagResponse {
        public Integer id;
        public String tag_name;

        public TagResponse(Integer id, String tag_name) {
            this.id = id;
            this.tag_name = tag_name;
        }
    }
}

