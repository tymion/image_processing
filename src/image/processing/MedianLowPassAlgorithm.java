package image.processing;

import image.processing.ILowPassAlgorithms;

public class MedianLowPassAlgorithm implements ILowPassAlgorithms
{
    private void qsort(float[] data, int lowerIndex, int higherIndex)
    {
        int i = lowerIndex;
        int j = higherIndex;
        float tmp;
        float pivot = data[lowerIndex + (higherIndex - lowerIndex) / 2];

        while (i <= j) {
            while (data[i] < pivot) {
                i++;
            }
            while (data[j] > pivot) {
                j--;
            }
            if (i <= j) {
                tmp = data[i];
                data[i] = data[j];
                data[j] = tmp;
                i++;
                j--;
            }
        }
        if (lowerIndex < j)
            qsort(data, lowerIndex, j);
        if (i < higherIndex)
            qsort(data, i, higherIndex);
    }

    public float count (float[] data, int offset, int width, int size)
    {
        float[] median = new float[size * size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                median[i * size + j] = data[offset + i * width + j];
            }
        }
        qsort (median, 0, size * size - 1);
 
        return median[size * size / 2];
    }
}
