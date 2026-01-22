package project.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

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

    public BufferedImage cropAndResize(Image img, Rectangle targetDimensions)
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
