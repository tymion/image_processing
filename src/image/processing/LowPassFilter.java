package image.processing;

import image.processing.ILowPassAlgorithms;
import image.processing.MedianLowPassAlgorithm;
import image.processing.GaussLowPassAlgorithm;

public class LowPassFilter
{
    private float[] proccess (float[] data, int width, int height, int size, ILowPassAlgorithms algorithm)
    {
        float[] out = new float[width * height];

        int center = (size / 2);
        int idx;
        for (int j = 0; j < height - (size - 1); j++) {
            for (int i = 0; i < width - (size - 1); i++) {
                idx = (j + center) * width + (i + center);
                out[idx] = algorithm.count (data, j * width + i, width, size);
                if (out[idx] < 0)
                    out[idx] = 0;
            }
        }
    
        return out;
    }

    public float[] countWithMedian (float[] data, int width, int height, int size)
    {
        return proccess (data, width, height, size, new MedianLowPassAlgorithm());
    }

    public float[] countWithGauss (float[] data, int width, int height, int size)
    {
        return proccess (data, width, height, size, new GaussLowPassAlgorithm());
    }
}
