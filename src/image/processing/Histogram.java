package image.processing;


public class Histogram
{
	int [] hist;
	float [] data;
	float averageValue;

	private int getIndex (float data)
	{
		return (int) (data * 255);
	}

	private void findAverage ()
	{
		int minVal = Integer.MAX_VALUE;
		int maxVal = 0;
		for (int i = 0; i < 256; i++) {
			if (hist[i] == 0) {
				continue;
			}

			if (hist[i] < minVal) {
				minVal = hist[i];
			}
			if (hist[i] > maxVal) {
				maxVal = hist[i];
			}
		}
		int average = (maxVal - minVal) / 2;
		float bestApprox = Float.MAX_VALUE;
		int bestIdx = 0;
		for (int i = 0; i < 256; i++) {
			if (hist[i] == 0) {
				continue;
			}
			if (Math.abs (hist[i] - average) < bestApprox) {
				bestIdx = i;
				bestApprox = Math.abs (hist[i] - average);
			}
		}
		averageValue = (float) bestIdx / 255;
	}

	public Histogram (float []data)
	{
		averageValue = -1;
        hist = new int[256];

		this.data = data;
		for (int i = 0; i < this.data.length; i++) {
			hist[getIndex(data[i])]++;
		}
	}

	public int getValueCount (float data)
	{
		return hist[getIndex (data)];
	}

	public int getCountOfAverageCount ()
	{
		if (averageValue == -1)
			findAverage ();
		return getValueCount (averageValue);
	}

	public float getValueOfAverageCount ()
	{
		if (averageValue == -1)
			findAverage ();
		return averageValue;
	}

	public float probabilityLessAndEqualThanThreshold (float threshold)
	{
		int idx = getIndex (threshold);
		float probability = 0;
		for (int i = 0; i <= idx; i++) {
			probability += hist[i];
		}
		probability /= this.data.length;
		return probability;
	}

	public float probabilityMoreThanThreshold (float threshold)
	{
		int idx = getIndex (threshold);
		float probability = 0;
		for (int i = idx + 1; i < 256; i++) {
			probability += hist[i];
		}
		probability /= this.data.length;
		return probability;
	}

	public float expectedValueOfValuesLessAndEqualThanThreshold (float threshold, float probability)
	{
		if (probability == 0)
			return 0;
		int idx = getIndex (threshold);
		float expected = 0;
		for (int i = 0; i <= idx; i++) {
			expected += i * hist[i];
		}
		expected /= probability;
		//expected /= (this.data.length * probability);
		return expected;
	}

	public float expectedValueOfValuesMoreThanThreshold (float threshold, float probability)
	{
		if (probability == 0)
			return 0;
		int idx = getIndex (threshold);
		float expected = 0;
		for (int i = idx + 1; i < 256; i++) {
			expected += i * hist[i];
		}
		expected /= probability;
		//expected /= (this.data.length * probability);
		return expected;
	}

	public float varianceOfValuesLessAndEqualThanThreshold (float threshold, float probability,
                                                            float expected)
	{
		if (probability == 0)
			return 0;
		int idx = getIndex (threshold);
		float variance = 0;
		float tmp = 0;
		for (int i = 0; i <= idx; i++) {
			tmp = (float) i - expected;
			variance += Math.pow (tmp, 2) * hist[i];
		}
		variance /= probability;
		//variance /= (this.data.length * probability);
		return variance;
	}

	public float varianceOfValuesMoreThanThreshold (float threshold, float probability,
                                                    float expected)
	{
		if (probability == 0)
			return 0;
		int idx = getIndex (threshold);
		float variance = 0;
		float tmp = 0;
		for (int i = idx + 1; i < 256; i++) {
			tmp = (float) i - expected;
			variance += Math.pow (tmp, 2) * hist[i];
		}
		variance /= probability;
		//variance /= (this.data.length * probability);
		return variance;
	}

	public float getTotalVarianceBalance (float probabilityLess, float probabilityMore,
                                          float varianceLess, float varianceMore)
	{
		return (float)(probabilityLess * Math.pow (varianceLess, 2) + probabilityMore * Math.pow (varianceMore, 2));
	}

	public void equalize ()
	{
		float [] lut = new float[256];
		lut[0] = (float) ((double) hist[0] / this.data.length);
		float min = 0;
		for (int i = 1; i < 256; i++) {
			lut[i] = (float) (lut[i - 1] + ((double) hist[i] / this.data.length));
			if (min == 0 && lut[i] != 0)
				min = lut[i];
		}
		for (int i = 0; i < 256; i++) {
			lut[i] = (lut[i] - min) / (1 - min);
			hist[i] = 0;
		}

		for (int i = 0; i < this.data.length; i++) {
			data[i] = lut[getIndex (data[i])];
		}

		for (int i = 0; i < this.data.length; i++) {
			hist[getIndex (data[i])]++;
		}
	}

    public float [] stretch(float [] data)
    {
        float max = 0;
        float min = 1;
        float [] out = new float[data.length];
        for (int i = 0; i < data.length; i++) {
            if (data[i] > max) {
                max = data[i];
            } else if (data[i] <  min) {
                min = data[i];
            }
        }
        float div = max - min;
        for (int i = 0; i < data.length; i++) {
            out[i] = (data[i] - min) / div;
        }
        return out;
    }
}
