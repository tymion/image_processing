package image.processing;

import image.Photo;

public class ColorSpaceConverter
{
    private float getMax (float a, float b)
    {
        return a > b ? a : b;
    }

    private float getMin (float a, float b)
    {
        return a > b ? b : a;
    }

    private float round (float a)
    {
        return a > 1 ? 1 : (a < 0 ? 0 : a);
    }

    public boolean convertRGBToHSV (Photo photo)
    {
        int pixelCount = photo.getSize ();
        float [] rPixels = photo.getData(Photo.ChannelColor.Red);
        float [] gPixels = photo.getData(Photo.ChannelColor.Green);
        float [] bPixels = photo.getData(Photo.ChannelColor.Blue);
        float min = 0;
        float max = 0;
        float vPixel;

        for (int i = 0; i < pixelCount; i++) {
            min = getMin (getMin (rPixels[i], gPixels[i]), bPixels[i]);
            vPixel = getMax (getMax (rPixels[i], gPixels[i]), bPixels[i]);
            if (min == vPixel) {
                rPixels[i] = 0;
                gPixels[i] = 0;
                bPixels[i] = 0;
            } else {
                if (rPixels[i] == min) {
                    //rPixels[i] = fmod ((3 - (gPixels[i] - bPixels[i]) / (vPixel - min)) * 60, 360) / 360;
                    rPixels[i] = (3 - (gPixels[i] - bPixels[i]) / (vPixel - min)) * 60 / 400;
                } else if (gPixels[i] == min) {
                    rPixels[i] = (((5 - (bPixels[i] - rPixels[i]) / (vPixel - min)) * 60) % 360) / 360;
                    //rPixels[i] = fmod ((5 - (bPixels[i] - rPixels[i]) / (vPixel - min)) * 60, 360) / 360;
                } else {
                    rPixels[i] = (1 - (rPixels[i] - bPixels[i]) / (vPixel - min)) * 60 / 400;
                    rPixels[i] = (1 - (rPixels[i] - bPixels[i]) / (vPixel - min)) * 60 / 400;
                }
                rPixels[i] = round(rPixels[i]);
                gPixels[i] = round(((vPixel - min) / vPixel));
                bPixels[i] = round(vPixel);
            }
        }
        return true;
    }

    public boolean convertRGBToHSL (Photo photo)
    {
        int pixelCount = photo.getSize ();
        float [] rPixels = photo.getData(Photo.ChannelColor.Red);
        float [] gPixels = photo.getData(Photo.ChannelColor.Green);
        float [] bPixels = photo.getData(Photo.ChannelColor.Blue);
        float min = 0;
        float max = 0;
        float hPixel;

        for (int i = 0; i < pixelCount; i++) {
            if (rPixels[i] > gPixels[i]) {
                if (rPixels[i] >= bPixels[i]) {
                    max = rPixels[i];
                    if (bPixels[i] > gPixels[i]) {
                        min = gPixels[i];
                        hPixel = 60 * ((gPixels[i] - bPixels[i]) / (max - min)) + 360;
                    } else {
                        min = bPixels[i];
                        hPixel = 60 * ((gPixels[i] - bPixels[i]) / (max - min));
                    }
                } else {
                    max = bPixels[i];
                    min = gPixels[i];
                    hPixel = 60 * ((rPixels[i] - gPixels[i]) / (max - min)) + 240;
                }
            } else {
                if (gPixels[i] > bPixels[i]) {
                    max = gPixels[i];
                    if (bPixels[i] > rPixels[i]) {
                        min = rPixels[i];
                    } else {
                        min = bPixels[i];
                    }
                    hPixel = 60 * ((bPixels[i] - rPixels[i]) / (max - min)) + 120;
                } else {
                    max = bPixels[i];
                    min = rPixels[i];
                    if (min == max)
                        hPixel = 0;
                    else if (bPixels[i] == gPixels[i])
                        hPixel = 60 * ((bPixels[i] - rPixels[i]) / (max - min)) + 120;
                    else
                        hPixel = 60 * ((rPixels[i] - gPixels[i]) / (max - min)) + 240;
                }
            }
            bPixels[i] = round((max + min) / 2);
            gPixels[i] = round(max == 0 ? 0 : (bPixels[i] > 0.5 ? ((max - min) / 2 * bPixels[i]) : ((max - min) / (2 - 2 * bPixels[i]))));
            rPixels[i] = round(hPixel / 255);
        }

        return true;
    }

    public float [] convertRGBToGrayScale (Photo photo)
    {
        int pixelCount = photo.getSize ();
        float [] rPixels = photo.getData(Photo.ChannelColor.Red);
        float [] gPixels = photo.getData(Photo.ChannelColor.Green);
        float [] bPixels = photo.getData(Photo.ChannelColor.Blue);

        float [] out = new float[photo.getSize ()];

        for (int i = 0; i < pixelCount; i++) {
            out[i] = rPixels[i] * (float) 0.299 + gPixels[i] * (float) 0.587 + bPixels[i] * (float)0.114;
            //out[i] = (rPixels[i] + gPixels[i] + bPixels[i]) / 3;
        }

        return out;
    }

    public float [] convertRGBToYUV (Photo photo)
    {
        int pixelCount = photo.getSize ();
        float [] rPixels = photo.getData(Photo.ChannelColor.Red);
        float [] gPixels = photo.getData(Photo.ChannelColor.Green);
        float [] bPixels = photo.getData(Photo.ChannelColor.Blue);

        float [] out = new float[photo.getSize ()];

        for (int i = 0; i < pixelCount; i++) {
            out[i] = rPixels[i] * (float) 0.299 + gPixels[i] * (float) 0.587 + bPixels[i] * (float)0.114;
            //TODO
        }

        return out;
    }
}
