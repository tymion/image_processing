package image.processing;

public class MorphologicalOperations
{
    private float[] count (float[] data, int height, int width, int[] seTab)
    {
        int size = (int) Math.sqrt (seTab.length);
        int center = size / 2;
        int x, y = 0;
        int xOffset, yOffset = 0;
        float[] out = new float[height * width];

        for (int i = 0; i < seTab.length; i++) {
            if (seTab[i] == 0)
                continue;
            x = i / size;
            y = i % size;
            xOffset = x - center;
            yOffset = y - center;
            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    if (h + yOffset >= 0 && w + xOffset >= 0 && h + yOffset < height &&
                        w + xOffset < width) {
                        out[h * width + w] += data[(h + yOffset) * width + w + xOffset];
                    } else {
                        out[h * width + w] += data[h * width + w];
                    }
                } 
            }
        }
    
        return out;
    }

    public float[] dilatation (float[] data, int width, int height, int[] seTab)
    {
        float [] out = count (data, height, width, seTab);
        for (int j = 0; j < height * width; j++) {
            if (out[j] > 0) {
                out[j] = 1;
            }
        }
        return out;
    }

    public float[] erosion (float[] data, int width, int height, int[] seTab)
    {
        int sum = 0;
        for (int i = 0; i < seTab.length; i++) {
            sum += seTab[i];
        }
        float [] out = count (data, height, width, seTab);
        for (int j = 0; j < height * width; j++) {
            if (out[j] != sum) {
                out[j] = 0;
            } else {
                out[j] = 1;
            }
        }
        return out;
    }
}
