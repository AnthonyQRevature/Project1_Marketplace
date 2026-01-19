package project.service;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import project.Repository.Entities.PostMediaEntity;
import project.Repository.Entities.PostMediaEntity.MediaTypeEnum;
import project.Repository.dao.PostMediaDao;
import project.util.FileSystemProvider;
import project.util.exception.FileNotFoundException;
import project.util.exception.InvalidRequestException;

@Service
public class PostMediaService {

    MediaService media;
    FileSystemProvider fileSystem;
    PostMediaDao dao;
    Set<String> acceptedMediaTypes;
    Rectangle targetDimensions;

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
    private PostMediaEntity entityOf(MultipartFile file, Integer postId)
    {
        MediaTypeEnum type = MediaTypeEnum.image;
        String extension = ".jpg";
        String name = "";

        switch (file.getContentType())
        {
            case "image/png":
            case "image/jpg":
                type=MediaTypeEnum.image;
                name=assignFilename(type, ".jpg");
                break;
            default:
                System.out.printf("Found file type %s\n", file.getContentType());
        }

        name = DigestUtils.md5DigestAsHex(name.getBytes()) + extension;

        return new PostMediaEntity(type, name, postId);
    }

    @Autowired
    public PostMediaService(PostMediaDao dao, MediaService media, FileSystemProvider fileSystem) 
    {
        this.dao = dao;
        this.media = media;
        this.fileSystem = fileSystem;

        this.acceptedMediaTypes = new HashSet<>();
        this.acceptedMediaTypes.add("image/png");
        this.acceptedMediaTypes.add("image/jpeg");

        this.targetDimensions = new Rectangle(640, 640);
    }

    public BufferedImage cropAndResize(Image img)
    {
        double sfx, sfy;
        int imgWidth, imgHeight;
        imgWidth = img.getWidth(null);
        imgHeight = img.getHeight(null);
        sfx = (double)targetDimensions.width/imgWidth;
        sfy = (double)targetDimensions.height/imgHeight;
        Rectangle printBox = new Rectangle(0, 0, targetDimensions.width, targetDimensions.height);
        //not perfect
        if (sfx > sfy)
        {
            //offset the y
            double sf = sfx;
            printBox.y = -(int)Math.round((sf * imgHeight - targetDimensions.height) / 2);
            printBox.width = (int)Math.round(imgWidth * sf);
            printBox.height = (int)Math.round(imgHeight * sf);
        }
        else if (sfy > sfx)
        {
            //offset the x
            double sf = sfy;
            printBox.x = -(int)Math.round((sf * imgWidth - targetDimensions.width) / 2);
            printBox.width = (int)Math.round(imgWidth * sf);
            printBox.height = (int)Math.round(imgHeight * sf);
        }
        

        int imageType = BufferedImage.TYPE_INT_RGB;
        BufferedImage result = new BufferedImage(targetDimensions.width, targetDimensions.height, imageType);
        Graphics2D g = result.createGraphics();
        g.drawImage(img, printBox.x, printBox.y, printBox.width, printBox.height, null);
        System.out.println(printBox.toString());
        return result;
    }

    public PostMediaEntity addMedia(MultipartFile file, Integer postId) throws 
        InvalidRequestException,
        IOException,
        FileNotFoundException
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
    }

}
