package project.util;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import project.properties.MediaProperties;
import project.util.exception.FileNotFoundException;

@Component
public class FileSystemProvider {
    
    private final Path rootLocation;

    public FileSystemProvider(MediaProperties properties) 
    {
        if(properties.getLocation().trim().length() == 0)
        {
            throw new RuntimeException("File upload location can not be Empty."); 
        }

        this.rootLocation = Paths.get(properties.getLocation());
    }

    public Path resolve(String fileName)
    {
        return this.rootLocation.resolve(
                Paths.get(fileName))
                .normalize().toAbsolutePath();
    }

    public boolean exists(String fileName) { return exists(resolve(fileName)); }
    public boolean exists(Path file)
    {
        return file.toFile().exists();
    }

    public void storeImage(RenderedImage file, Path location) throws 
        FileNotFoundException,
        IOException
    {
        if (!location.getParent().equals(this.rootLocation.toAbsolutePath())) 
        {
            // This is a security check
            throw new FileNotFoundException(
                "Cannot store file outside current directory.");
        }

        System.out.printf("stored file %s\n", location.toString());
        ImageIO.write(file, "jpg", location.toFile());
    }

    public Resource loadAsResource(String filename) throws FileNotFoundException
    {
        try 
        {
            Path file = resolve(filename);
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

    public Stream<Path> loadAll()
    {
        try {
            return Files.walk(this.rootLocation, 1)
                .filter(path -> !path.equals(this.rootLocation))
                .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to read stored files", e);
        }
    }

    public void deleteAll()
    {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
