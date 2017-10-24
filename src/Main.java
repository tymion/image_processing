import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import java.util.Arrays;

import image.Photo;
import image.processing.*; 

import java.awt.geom.Ellipse2D;

public class Main
{
    private static String getOutFileName(String name)
    {
        String[] parts = name.split("/");
        name = parts[parts.length - 1];
        if (name.indexOf(".") > 0) {
            name = name.substring(0, name.lastIndexOf("."));
        }
        return parts[parts.length - 2] + "/" + name + "_gray.bmp";
    }

    private static float[] count(Photo photo, Region region)
    {
        int width = photo.getWidth ();
        int height = photo.getHeight ();
	    LowPassFilter filter = new LowPassFilter ();
        ColorSpaceConverter color = new ColorSpaceConverter ();
        Gradient grad = new Gradient ();
        Operations op = new Operations ();
        Binarize bin = new Binarize ();
        bin.setThreshold ((float) 0.004, 1);

        float [] out = color.convertRGBToGrayScale (photo);
        out = filter.countWithGauss (out, width, height, 5);
        out = filter.countWithMedian (out, width, height, 5);
    	out = grad.countWithLaplacian (out, width, height);
        out = op.removeAfterRegion (out, width, height, region);
    	bin.binarizeData (out);
        return out;
    }

    private static float[] processFile(Photo photo, Region region) throws FileNotFoundException, IOException
    {
        int width = photo.getWidth ();
        int height = photo.getHeight ();
        MorphologicalOperations morph = new MorphologicalOperations ();
        Operations op = new Operations ();
        int[] seTab = {
                        1, 1, 1,
                        1, 1, 1,
                        1, 1, 1
                        };

        float[] gray = count(photo, region);

        //op.removeBackground (gray, grayBack);
        gray = op.filter (gray, width, height, 7, 5);
        gray = morph.dilatation (gray, width, height, seTab);
        gray = morph.erosion (gray, width, height, seTab);
        return gray;
    }

    private static float[] processFile_2(Photo photo) throws FileNotFoundException, IOException
    {
        int width = photo.getWidth ();
        int height = photo.getHeight ();
        MorphologicalOperations morph = new MorphologicalOperations ();
        Operations op = new Operations ();
        int[] seTab = {
                        1, 1, 1,
                        1, 1, 1,
                        1, 1, 1
                        };

        LowPassFilter filter = new LowPassFilter ();
        ColorSpaceConverter color = new ColorSpaceConverter ();
        Gradient grad = new Gradient ();
        Binarize bin = new Binarize ();
        bin.setThreshold ((float) 0.004, 1);

        float [] out = color.convertRGBToGrayScale (photo);
        out = filter.countWithGauss (out, width, height, 5);
        out = filter.countWithMedian (out, width, height, 5);
        out = grad.countWithLaplacian (out, width, height);
        bin.binarizeData (out);
        float[] gray = out;

        //op.removeBackground (gray, grayBack);
        gray = op.filter (gray, width, height, 7, 5);
        gray = morph.dilatation (gray, width, height, seTab);
        gray = morph.erosion (gray, width, height, seTab);
        return gray;
    }

    public static void main(String [ ] args)
    {
        try {
            /*
            if (args.length < 1) {
                System.out.println("Invalid argument!");
                return;
            }
            System.out.println("Processing file: " + args[0]);
            Photo photo = new Photo(args[0]);
            */
            String name = new String("resources/left.jpg");
            Photo photo = new Photo(name);
            //Region region = null;
            String outFilename = null;
            //region = new Region(photo.getWidth(), photo.getHeight());
            //region.addShape(new Ellipse2D.Double(90, 90, photo.getWidth() - 180, photo.getHeight() - 180));
            //region.removeShape(new Ellipse2D.Double(photo.getHeight() / 2 - 200, photo.getWidth() / 2 - 200, 400, 400));
            //float[] out = processFile(photo, region);
            float[] out = processFile_2(photo);
            outFilename = getOutFileName(name);
            System.out.println("Saving file: " + outFilename);
            photo.saveBin(outFilename, out);
        } catch (FileNotFoundException e) {
            System.out.println("Exception: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
