package project.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileEncoder {

    /*
    public String base64Encode(MultipartFile file)
    {
        String encodedFile;
        try (InputStream stream = file.getInputStream())
        {
            //ImageInputStream stream = ImageIO.createImageInputStream(img);
            byte[] bytes = new byte[(int)file.getSize()];
            stream.read(bytes);
            encodedFile = Base64.getEncoder().encodeToString(bytes);
            return encodedFile;
        }
        catch (IOException e)
        {
            return null;
        }
    }

    public String base64Encode(File file)
    {
        String encodedFile;
        try (InputStream stream = new FileInputStream(file))
        {
            //ImageInputStream stream = ImageIO.createImageInputStream(img);
            byte[] bytes = new byte[(int)file.length()];
            stream.read(bytes);
            encodedFile = Base64.getEncoder().encodeToString(bytes);
            return encodedFile;
        }
        catch (IOException e)
        {
            return null;
        }
    }
    */

    public String base64Encode(BufferedImage file)
    {
        String encodedFile;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream())
        {
            ImageIO.write(file, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            encodedFile = Base64.getEncoder().encodeToString(bytes);
            return encodedFile;
        }
        catch (IOException e)
        {
            return null;
        }
    }

    public byte[] base64Decode(String str)
    {
        byte[] bytes = Base64.getDecoder().decode(str);
        return bytes;
    }

    public BufferedImage base64DecodeAsImage(String str)
    {
        try
        {
            byte[] bytes = base64Decode(str);
            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            BufferedImage result = ImageIO.read(stream);
            return result;
        }
        catch (IOException e)
        {
            return null;
        }
    }
}
