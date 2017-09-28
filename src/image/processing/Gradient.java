package image.processing;

import image.processing.Operations;

public class Gradient
{
    float[] laplacian = {0, -1, 0, -1, 4, -1, 0, -1, 0};

    float[] different = { -1/9, -1/9, -1/9, -1/9, 17/9, -1/9, -1/9, -1/9, -1/9};

    private float getDiv(float[] matrix, int size)
    {
        float sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sum += matrix[i * size + j];
            }
        }
        return sum == 0 ? 1 : sum;
    }

    public float count (float[] data, int offset, int width, float[] tab, int size)
    {
        float sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sum += tab[i * size + j] * data[offset + i * width + j];
            }
        }
        return sum / getDiv(tab, size);
    }

    private float[] proccess (float[] data, int width, int height, float[] tab, int size)
    {
        float[] out = new float[width * height];

        int center = (size / 2);
        int idx;
        for (int j = 0; (int) j < height - (size - 1); j++) {
            for (int i = 0; (int) i < width - (size - 1); i++) {
                idx = (j + center) * width + (i + center);
                out[idx] = count (data, j * width + i, width, tab, size);
                if (out[idx] < 0) {
                    out[idx] = 0;
                } else if (out[idx] > 1) {
                    out[idx] = 1;
                }
            }
        }
    
        return out;
    }

    public float[] countWithLaplacian (float[] data, int width, int height)
    {
        return proccess (data, width, height, this.laplacian, 3);
    }

    public float[] countWithDifferentation (float[] data, int width, int height)
    {
        return proccess (data, width, height, this.different, 3);
    }
}
