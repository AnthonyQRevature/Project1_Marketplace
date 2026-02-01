package project.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import project.service.PostMediaService;
import project.service.UserService;
import project.util.AllowCORS;
import project.util.Secure;
import project.util.SecureIndescriminate;
import project.util.SecurityLevel;
import project.util.exception.InvalidRequestException;

@RestController
public class MediaController {
    private final PostMediaService storageService;
    private UserService userService;
    
    @Autowired
    public MediaController(PostMediaService storageService, UserService userService) {
        this.storageService = storageService;
        this.userService = userService;
    }
    
    /*
    @GetMapping("/media")
    public Object listUploadedFiles(Model model) {
        /*
        model.addAttribute("files", storageService.loadAll().map(
            path -> MvcUriComponentsBuilder.fromMethodName(MediaController.class,
                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));
        /
        return fileSystem.loadAll().map(
            path -> MvcUriComponentsBuilder.fromMethodName(MediaController.class,
                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList());
    }
    */
    
    /*
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
    */

    @PostMapping("/users/{id}/media")
    @AllowCORS
    @Secure
    public ResponseEntity<String> pfpUpload(
        @RequestHeader("Authorization") String authHeader, 
        @PathVariable("id") int user_id, 
        @RequestParam("file") MultipartFile file
    ) {
        try
        {
            var entity = userService.addMedia(file, user_id);
            return ResponseEntity.ok(entity.getUserProfile().getPfpEncoded());
        }
        catch (IOException e)
        {
            return ResponseEntity.badRequest().header("cause", e.getMessage()).build();
        }
    }

    //wrong
    @PostMapping("/users/{user_id}/listings/{post_id}/media")
    @AllowCORS
    @SecureIndescriminate(SecurityLevel.ADMIN)
    public ResponseEntity<String> postImageUpload(
        @RequestHeader("Authorization") String authHeader, 
        @PathVariable("user_id") int user_id, 
        @PathVariable("post_id") int post_id,
        @RequestParam("file") MultipartFile file
    ) {
        try
        {
            var entity = storageService.addMedia(file, post_id);
            System.out.println(entity.mediaEncoded);
            return ResponseEntity.ok(entity.mediaEncoded);
        }
        catch (IOException e)
        {
            return ResponseEntity.status(500).header("cause", e.getMessage()).build();
        }
        catch(InvalidRequestException e)
        {
            return ResponseEntity.status(400).header("cause", e.getMessage()).build();
        }
    }

    /*
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
        catch (InvalidRequestException | IOException e)
        {
            return ResponseEntity.status(400).header("Cause", e.getMessage()).build();
        }
    }
    */
    
    /*
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
    return ResponseEntity.notFound().build();
    }
    */
}
