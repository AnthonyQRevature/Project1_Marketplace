import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import project.properties.MediaProperties;
import project.service.PostMediaService;
import project.util.FileSystemProvider;
import test.AppContextTest;
import test.TestAppConfig;

public class AAAAAA {
    public static void main(String[] args) throws Throwable
    {
        FileSystemProvider fileSystem = new project.util.FileSystemProvider(new MediaProperties());
        PostMediaService service = new PostMediaService(null, null, fileSystem);

        Image input = ImageIO.read(fileSystem.resolve("test.png").toFile());
        BufferedImage output = service.cropAndResize(input);
        fileSystem.storeImage(output, fileSystem.resolve("output.jpg"));
    }
}
