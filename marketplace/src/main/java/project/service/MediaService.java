package project.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import project.properties.MediaProperties;
import project.util.exception.FileNotFoundException;
import project.util.exception.InvalidRequestException;

@Service
public class MediaService {
    
    private final Path rootLocation;
    private Set<String> acceptedMediaTypes;
    
    @Autowired
    public MediaService(MediaProperties properties) {
        
        if(properties.getLocation().trim().length() == 0){
            throw new RuntimeException("File upload location can not be Empty."); 
        }
        
        this.rootLocation = Paths.get(properties.getLocation());
        this.acceptedMediaTypes = new HashSet<>();
        this.acceptedMediaTypes.add("image/png");
        this.acceptedMediaTypes.add("image/jpeg");
    }
    
    /**
     * stores a file into the file structure
     */
    public void store(MultipartFile file) throws 
        InvalidRequestException,
        FileNotFoundException
    {
        try 
        {
            if (file.isEmpty()) 
            {
                throw new InvalidRequestException("Failed to store empty file.");
            }
            if (!acceptedMediaTypes.contains(file.getContentType()))
            {
                throw new InvalidRequestException(String.format("File type '%s' not supported", file.getContentType()));
            }
            Path destinationFile = this.rootLocation.resolve(
                Paths.get(file.getOriginalFilename()))
                .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) 
            {
                // This is a security check
                throw new FileNotFoundException(
                    "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) 
            {
                Files.copy(inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
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
        try {
            return Files.walk(this.rootLocation, 1)
                .filter(path -> !path.equals(this.rootLocation))
                .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to read stored files", e);
        }
    }
    /**
     * returns the path of a file named filename in the file structure
     */
    public Path load(String filename) {
        return rootLocation.resolve(filename);
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
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
    
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }
}