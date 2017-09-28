package image.processing;

import image.processing.Region;

public class Operations
{
    float[] laplacian = {0, -1, 0, -1, 4, -1, 0, -1, 0};
    float suplyer = 1;

    public float correlation (float[] matrix, int size, float[] data, int offset, int width)
    {
        float sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sum += this.laplacian[i * size + j] * data[offset + i * width + j];
            }
        }
        return sum / this.suplyer;
    }

    public float weave (float[] data, int offset, int width, int size)
    {
        float sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sum += this.laplacian[i * size + j] * data[offset + (size - 1 - i) * width + (size - 1 - j)];
            }
        }
        return sum / this.suplyer;
    }

    public float[] subtraction (float[] minuend, float[] subtrahend)
    {
        float[] out = new float[minuend.length];
        for (int i = 0; i < minuend.length; i++) {
            out[i] = minuend[i] - subtrahend[i];
        }
        return out;
    }

    public float[] removeBackground (float[] data, float[] background)
    {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == background[i]) {
                data[i] = 0;
            }
        }
        return data;
    }

    public float[] removeAfterRegion(float[] data, int width, int height, Region region)
    {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (!region.contains(i, j)) {
                    data[i * width + j] = 0;
                }
            }
        }
        return data;
    }

    public float[] removeRegion(float[] data, int width, int height, Region region)
    {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (region.contains(i, j)) {
                    data[i * width + j] = 0;
                }
            }
        }
        return data;
    }

    private float sum (float[] data, int x, int y, int size, int width)
    {
        float sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sum += data[(y + i) * width + j + x];
            }
        }
        return sum;
    }

    public float[] filter (float[] data, int width, int height, int size, int prog)
    {
        int center = size / 2;
        for (int y = center; y < height - center; y++) {
            for (int x = center; x < width - center; x++) {
                if (data[y * width + x] == 0) {
                    continue;
                }
                if (sum(data, x - center, y - center, size, width) > (float) prog) {
                    data[y * width + x] = 1;
                } else {
                    data[y * width + x] = 0;
                }
            }
        }
        return data;
    }
}
