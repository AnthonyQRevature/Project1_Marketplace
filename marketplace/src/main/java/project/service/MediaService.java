//probably gonna delete this one
package project.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import project.Repository.Entities.PostMediaEntity;
import project.util.FileSystemProvider;
import project.util.exception.FileNotFoundException;
import project.util.exception.InvalidRequestException;

@Service
public class MediaService 
{
    private final Set<String> acceptedMediaTypes;
    private final FileSystemProvider fileSystem;
    
    @Autowired
    public MediaService(FileSystemProvider fileSystem) {
        this.fileSystem = fileSystem;

        this.acceptedMediaTypes = new HashSet<>();
        this.acceptedMediaTypes.add("image/png");
        this.acceptedMediaTypes.add("image/jpeg");
    }
    
    private static BufferedImage cropAndResize(Image img)
    {
        return null;
    }

    /**
     * stores a file into the file structure
     */
    public void store(MultipartFile file, PostMediaEntity fileData) throws 
        InvalidRequestException,
        FileNotFoundException
    {
        try 
        {
            /*
            if (file.isEmpty()) 
            {
                throw new InvalidRequestException("Failed to store empty file.");
            }
            if (!acceptedMediaTypes.contains(file.getContentType()))
            {
                throw new InvalidRequestException(String.format("File type '%s' not supported", file.getContentType()));
            }
            */
            

            try (InputStream inputStream = file.getInputStream()) 
            {
                Image img = ImageIO.read(inputStream);
                BufferedImage resize = cropAndResize(img);
                
                //saving the file
                Path destinationFile = fileSystem.resolve(file.getOriginalFilename());
                
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }
    /**
     * returns a list of all files in the file structure
     */
    public Stream<Path> loadAll() {
        return fileSystem.loadAll();
    }
    /**
     * returns the path of a file named filename in the file structure
     */
    public Path load(String filename) {
        return fileSystem.resolve(filename);
    }
    /**
     * returns a Resource representing the file named filename in the file structure
     */
    public Resource loadAsResource(String filename) throws FileNotFoundException
    {
        try 
        {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new FileNotFoundException(
                    "Could not read file: " + filename);
                
            }
        }
        catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not read file: " + filename, e);
        }
    }
    /**
     * deletes all files in the file structure
     */
    public void deleteAll() {
        fileSystem.deleteAll();
    }
    /*
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }*/
}