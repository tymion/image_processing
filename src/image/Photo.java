package image;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.FileNotFoundException;


public class Photo {

    public enum ChannelColor {
        Invalid,
        Red,
        Green,
        Blue,
        White,
        Max
    }

    File file;
    BufferedImage img = null;
    int width;
    int height;
    float[] rPixels;
    float[] gPixels;
    float[] bPixels;
    float[] wPixels;

    private float getMax (float a, float b)
    {
        return a > b ? a : b;
    }

    private float getMin (float a, float b)
    {
        return a > b ? b : a;
    }


    private int round (float a)
    {
        return (int)( a > 255 ? 255 : (a < 0 ? 0 : a));
    }

    public Photo(String filename) throws FileNotFoundException, IOException
    {
        this.file = new File(filename);
        img = ImageIO.read(this.file);
        this.rPixels = new float[img.getWidth() * img.getHeight()];
        this.gPixels = new float[img.getWidth() * img.getHeight()];
        this.bPixels = new float[img.getWidth() * img.getHeight()];
        this.wPixels = new float[img.getWidth() * img.getHeight()];
        this.height = img.getHeight();
        this.width = img.getWidth();
        int[] data = new int[img.getWidth() * img.getHeight()];
        img.getRGB(0, 0, img.getWidth(), img.getHeight(), data, 0, img.getWidth());
        for (int i = 0; i < data.length; i++) {
            rPixels[i] = (float) (data[i] >> 24 & 0xFF) / 255;
            gPixels[i] = (float) (data[i] >> 16 & 0xFF) / 255;
            bPixels[i] = (float) (data[i] >> 8 & 0xFF) / 255;
            wPixels[i] = (float) (data[i] & 0xFF) / 255;
        }
        data = null;
    }

    public float [] getData(ChannelColor color)
    {
        switch (color) {
            case Red:
                return this.rPixels;
            case Green:
                return this.gPixels;
            case Blue:
                return this.bPixels;
            case White:
                return this.wPixels;
            default:
                return null;
        }
    }

    public void setData(ChannelColor color, float[] data)
    {
        switch (color) {
            case Red:
                this.rPixels = data;
            case Green:
                this.gPixels = data;
            case Blue:
                this.bPixels = data;
            case White:
                this.wPixels = data;
            default:
                return;
        }
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public int getSize()
    {
        return this.height * this.width;
    }

    public void save() throws FileNotFoundException, IOException
    {
        int tmp;
        int[] data = new int[img.getWidth() * img.getHeight()];
        for (int i = 0; i < data.length; i++) {
            tmp = round(rPixels[i] * 255);
            data[i] = (tmp << 24) | (tmp << 16) | (tmp << 8) | tmp;
        }
        img.setRGB(0, 0, this.width, this.height, data, 0, this.width);
        ImageIO.write(img, "bmp", new File("red.bmp"));
        for (int i = 0; i < data.length; i++) {
            tmp = round(gPixels[i] * 255);
            data[i] = (tmp << 24) | (tmp << 16) | (tmp << 8) | tmp;
        }
        img.setRGB(0, 0, this.width, this.height, data, 0, this.width);
        ImageIO.write(img, "bmp", new File("green.bmp"));
        for (int i = 0; i < data.length; i++) {
            tmp = round(bPixels[i] * 255);
            data[i] = (tmp << 24) | (tmp << 16) | (tmp << 8) | tmp;
        }
        img.setRGB(0, 0, this.width, this.height, data, 0, this.width);
        ImageIO.write(img, "bmp", new File("blue.bmp"));
    }

    public void saveBin(String name, float [] bin) throws FileNotFoundException, IOException
    {
        int tmp;
        int[] data = new int[img.getWidth() * img.getHeight()];
        for (int i = 0; i < data.length; i++) {
            tmp = round(bin[i] * 255);
            data[i] = (tmp << 24) | (tmp << 16) | (tmp << 8) | tmp;
        }
        img.setRGB(0, 0, this.width, this.height, data, 0, this.width);
        ImageIO.write(img, "bmp", new File(name));
    }

    public void saveGray(String name, int [] data) throws FileNotFoundException, IOException
    {
        img.setRGB(0, 0, this.width, this.height, data, 0, this.width);
        ImageIO.write(img, "bmp", new File(name));
    }

}
