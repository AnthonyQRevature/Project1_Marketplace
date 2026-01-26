package project.util;

import java.io.InputStream;
import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class DefaultPfp {

    final String fileName = "/default_pfp";

    String encodedPfp;

    public String get() {return encodedPfp;}

    public DefaultPfp()
    {
        try
        {
            InputStream file = DefaultPfp.class.getResourceAsStream(fileName);
            Scanner scanner = new Scanner(file).useDelimiter("\\A");
            encodedPfp = scanner.next();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("unable to read default_pfp");
        }
    }
}
