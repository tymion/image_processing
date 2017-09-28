package image.processing;

import image.processing.ILowPassAlgorithms;

public class GaussLowPassAlgorithm implements ILowPassAlgorithms
{
    int[] gauss;
    int gaussSize = 0;
    float suplyer;

    private int[] getGauss(int size)
    {
        int[] mat = new int[size * size];
        int center = size / 2;
        int lastIdx = size - 1;
        int value;
        for (int i = 0; i <= center; i++) {
            for (int j = 0; j <= center; j++) {
                value = (int) Math.pow(2, i + j);
                mat[i * size + j] = value;
                if (i == center && j == center) {
                    continue;
                }
                if (i == center) {
                    mat[i * size + (lastIdx - j)] = value;
                } else if (j == center) {
                    mat[(lastIdx - i) * size + j] = value;
                } else {
                    mat[i * size + (lastIdx - j)] = value;
                    mat[(lastIdx - i) * size + (lastIdx - j)] = value;
                    mat[(lastIdx - i) * size + j] = value;
                }
            }
        }
        return mat;
    }

    private float getDiv(int[] matrix, int size)
    {
        float sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sum += matrix[i * size + j];
            }
        }
        return sum;
    }

    public float count (float[] data, int offset, int width, int size)
    {
        if (this.gaussSize == 0 || this.gaussSize != size) {
    	    this.gauss = getGauss (size);
            this.gaussSize = size;
    		this.suplyer = getDiv (this.gauss, size);
    	}

        float sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sum += this.gauss[i * size + j] * data[offset + i * width + j];
            }
        }
        return sum / this.suplyer;
    }
}
