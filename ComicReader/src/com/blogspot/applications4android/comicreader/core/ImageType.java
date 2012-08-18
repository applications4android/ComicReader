package com.blogspot.applications4android.comicreader.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Class to read an image file and determine the type of image
 */
public class ImageType {

    /**
     * List of all images currently supported. Each element's
     * 'toString' function will actually return the file extension!
     */
    public enum Types {
        BMP {
            public String toString() { return ".bmp"; }
        },
        JPG {
            public String toString() { return ".jpg"; }
        },
        GIF {
            public String toString() { return ".gif"; }
        },
        ICO {
            public String toString() { return ".ico"; }
        },
        PNG {
            public String toString() { return ".png"; }
        },
        UNKNOWN {
            public String toString() { return ".unknown"; }
        }
    }


    /**
     * Constructor. (private!)
     */
    private ImageType() {
    }

    /**
     * Read the file header to determine the image type.
     * @param imgFile image file
     * @return ImageType.Types enum
     */
    public static Types getImageType(String imgFile) {
        Types it = Types.UNKNOWN;
        try {
            FileInputStream fis = new FileInputStream(imgFile);
            it = getImageType(fis);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return it;
    }

    /**
     * Given a file pointer, returns the image type
     * @param fis file pointer
     * @return image type
     * @throws IOException
     * @throws Exception
     */
    public static Types getImageType(InputStream is) throws IOException, Exception {
        final int maxToBeRead = 8;
        Types it = Types.UNKNOWN;
        byte[] buff = new byte[maxToBeRead];
        try {
	        int len = is.read(buff);
	        if(len != maxToBeRead) {
	            Exception e = new Exception("Expected '"+maxToBeRead+"' bytes. But read only '"+len+"'!");
	            throw e;
	        }
	        if((buff[0] == (byte)0x42) && (buff[1] == (byte)0x4D)) {
	            it = Types.BMP;
	        }
	        else if((buff[0] == (byte)0xFF) && (buff[1] == (byte)0xD8)) {
	            it = Types.JPG;
	        }
	        else if((buff[0] == (byte)0x47) && (buff[1] == (byte)0x49) && (buff[2] == (byte)0x46) &&
	                (buff[3] == (byte)0x38) && (buff[4] == (byte)0x37) && (buff[5] == (byte)0x61)) {
	            it = Types.GIF;
	        }
	        else if((buff[0] == (byte)0x47) && (buff[1] == (byte)0x49) && (buff[2] == (byte)0x46) &&
	                (buff[3] == (byte)0x38) && (buff[4] == (byte)0x39) && (buff[5] == (byte)0x61)) {
	            it = Types.GIF;
	        }
	        else if((buff[0] == (byte)0x00) && (buff[1] == (byte)0x00) && (buff[2] == (byte)0x00) && (buff[3] == (byte)0x01)) {
	            it = Types.ICO;
	        }
	        else if((buff[0] == (byte)0x89) && (buff[1] == (byte)0x50) && (buff[2] == (byte)0x4E) && (buff[3] == (byte)0x47) &&
	                (buff[4] == (byte)0x0D) && (buff[5] == (byte)0x0A) && (buff[6] == (byte)0x1A) && (buff[7] == (byte)0x0A)) {
	            it = Types.PNG;
	        }
	        is.close();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        return it;
    }
}
