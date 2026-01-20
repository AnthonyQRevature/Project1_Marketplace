import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import project.properties.MediaProperties;
import project.service.PostMediaService;
import project.util.FileEncoder;
import project.util.FileSystemProvider;

public class AAAAAA {
    public static void main(String[] args) throws Throwable
    {
        /*
        FileSystemProvider fileSystem = new project.util.FileSystemProvider(new MediaProperties());
        PostMediaService service = new PostMediaService(null, null, fileSystem, new FileEncoder());

        Image input = ImageIO.read(fileSystem.resolve("test.png").toFile());
        BufferedImage output = service.cropAndResize(input);
        fileSystem.storeImage(output, fileSystem.resolve("output.jpg"));
        */
        
        FileEncoder encoder = new FileEncoder();
        FileSystemProvider fileSystem = new project.util.FileSystemProvider(new MediaProperties());
        PostMediaService service = new PostMediaService(null, new FileEncoder());

        File output = fileSystem.resolve("output.jpg").toFile();
        File input = fileSystem.resolve("test.jpg").toFile();
        BufferedImage img = service.cropAndResize(ImageIO.read(new FileInputStream(input)));

        String encoded = encoder.base64Encode(img);
        System.out.println(encoded);
        ImageIO.write(encoder.base64DecodeAsImage(encoded), "jpg", output);
    }
}
