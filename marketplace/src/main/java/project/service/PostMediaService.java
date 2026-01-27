package project.service;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import project.Repository.Entities.PostMediaEntity;
import project.Repository.Entities.PostMediaEntity.MediaTypeEnum;
import project.Repository.dao.PostMediaDao;
import project.util.FileEncoder;
import project.util.exception.InvalidRequestException;

@Service
public class PostMediaService {

    PostMediaDao dao;
    FileEncoder encoder;
    Set<String> acceptedMediaTypes;
    Rectangle targetDimensions;

    /*
    private String assignFilename(MediaTypeEnum type, String extension)
    {
        String fileName = "";
        int attempts=0;
        do
        {
            fileName = String.format("type:%s,time:%d,attempts:%d%s", type.toString(), System.nanoTime(), attempts, extension);
            fileName = DigestUtils.md5DigestAsHex(fileName.getBytes());
            attempts++;
        } while (fileSystem.exists(fileName));
        return fileName;
    }
    */
    private PostMediaEntity entityOf(MultipartFile file, Integer postId) throws IOException
    {
        MediaTypeEnum type = MediaTypeEnum.image;
        String encodedFile = "";

        switch (file.getContentType())
        {
            case "image/png":
            case "image/jpg":
                type=MediaTypeEnum.image;
                break;
            default:
                System.out.printf("Found file type %s\n", file.getContentType());
                throw new RuntimeException(String.format("Found file type %s\n", file.getContentType()));
        }

        //base64 encode the image
        try (InputStream stream = file.getInputStream())
        {
            //removes the alpha channel
            BufferedImage resized = encoder.cropAndResize(ImageIO.read(stream), targetDimensions);
            encodedFile = encoder.base64Encode(resized);
        }
        return new PostMediaEntity(type, encodedFile, postId);
    }

    @Autowired
    public PostMediaService(PostMediaDao dao, FileEncoder encoder) 
    {
        this.dao = dao;
        this.encoder = encoder;

        this.acceptedMediaTypes = new HashSet<>();
        this.acceptedMediaTypes.add("image/png");
        this.acceptedMediaTypes.add("image/jpeg");

        this.targetDimensions = new Rectangle(200, 200);
    }

    public PostMediaEntity addMedia(MultipartFile file, Integer postId) throws 
        InvalidRequestException,
        IOException
    {
        if (file.isEmpty()) 
        {
            throw new InvalidRequestException("Failed to store empty file.");
        }
        if (!acceptedMediaTypes.contains(file.getContentType()))
        {
            throw new InvalidRequestException(String.format("File type '%s' not supported", file.getContentType()));
        }

        var entity = entityOf(file, postId);
        entity = dao.save(entity);
        return entity;

        /*
        try (InputStream inputStream = file.getInputStream()) 
        {
            Image img = ImageIO.read(inputStream);
            BufferedImage resize = cropAndResize(img);
            
            //saving the file
            Path destinationFile = fileSystem.resolve(entity.mediaUrl);
            fileSystem.storeImage(resize, destinationFile);
            entity = dao.save(entity);
            return entity;
        }
        catch (IOException | FileNotFoundException e)
        {
            throw e;
        }
        */
    }

}
