package project.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.service.MediaService;
import project.service.PostMediaService;
import project.util.AllowCORS;
import project.util.exception.FileNotFoundException;
import project.util.exception.InvalidRequestException;
import project.util.FileSystemProvider;

@RestController
public class MediaController {
    private final PostMediaService storageService;
    private FileSystemProvider fileSystem;
    
    @Autowired
    public MediaController(PostMediaService storageService, FileSystemProvider fileSystem) {
        this.storageService = storageService;
        this.fileSystem = fileSystem;
    }
    
    @GetMapping("/media")
    public Object listUploadedFiles(Model model) {
        /*
        model.addAttribute("files", storageService.loadAll().map(
            path -> MvcUriComponentsBuilder.fromMethodName(MediaController.class,
                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));
        */
        return fileSystem.loadAll().map(
            path -> MvcUriComponentsBuilder.fromMethodName(MediaController.class,
                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList());
    }
            
    @GetMapping("/media/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try
        {
            Resource file = fileSystem.loadAsResource(filename);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        }
        catch (FileNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }
    }
        
    @PostMapping("/media")
    @AllowCORS
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
    RedirectAttributes redirectAttributes)
    {
        try{
            storageService.addMedia(file, null);
            redirectAttributes.addFlashAttribute("message",
            "You successfully uploaded " + file.getOriginalFilename() + "!");
            
            return ResponseEntity.ok().build();
        }
        catch (InvalidRequestException | FileNotFoundException | IOException e)
        {
            return ResponseEntity.status(400).header("Cause", e.getMessage()).build();
        }
    }
    
    /*
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
    return ResponseEntity.notFound().build();
    }
    */
}
