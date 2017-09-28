package image.processing;

import java.util.Arrays;

import image.processing.Histogram;

public class Binarize
{
	float [] bestBalanceTab;
	float maxValue = Float.MAX_VALUE;
	int maxIdx = 0;
	float [] bestBalanceThresholdTab;
    float [] thresholdTab;

	private int getIndex (float data)
	{
		return (int) (data * 255);
	}
    
    public Binarize ()
	{
        this.thresholdTab = new float[256];
	}

	public void addThreshold (float threshold, float value)
	{
		int i = getIndex (threshold);
		float tmp = this.thresholdTab[i];

		while (i < 256 && tmp == this.thresholdTab[i]) {
			this.thresholdTab[i] = value;
			i++;
		}
	}

	public float getBinerizedValue (float value)
	{
		return this.thresholdTab[getIndex (value)];
	}

	public void binarizeData (float [] data)
	{
		for (int i = 0; i < data.length; i++) {
			data[i] = getBinerizedValue (data[i]);
		}
	}

	public void pushThreshold (int thresholdCnt, float balance, float threshold)
	{
		if (balance < this.maxValue) {
			this.bestBalanceTab[this.maxIdx] = balance;
			this.maxValue = balance;
			this.bestBalanceThresholdTab[this.maxIdx] = threshold;
			for (int i = 0; i < thresholdCnt; i++) {
				if (this.bestBalanceTab[i] > this.maxValue) {
					this.maxValue = this.bestBalanceTab[i];
					this.maxIdx = i;
				}
			}
		}
	}

	public void minimalizeThreshold (Histogram hist, int thresholdsCnt, float [] thresholdTab)
	{
		float q1, q2, mi1, mi2, ro1, ro2, balance, threshold;
		this.bestBalanceTab = new float[thresholdsCnt];
		this.bestBalanceThresholdTab = new float[thresholdsCnt];
        Arrays.fill(this.bestBalanceTab, Float.MAX_VALUE);

		for (int i = 0; i < 256; i++) {
			threshold = (float) i / 255;
			q1 = hist.probabilityLessAndEqualThanThreshold (threshold);
			q2 = hist.probabilityMoreThanThreshold (threshold);
			mi1 = hist.expectedValueOfValuesLessAndEqualThanThreshold (threshold, q1);
			mi2 = hist.expectedValueOfValuesMoreThanThreshold (threshold, q2);
			ro1 = hist.varianceOfValuesLessAndEqualThanThreshold (threshold, q1, mi1);
			ro2 = hist.varianceOfValuesMoreThanThreshold (threshold, q2, mi2);
			balance = hist.getTotalVarianceBalance (q1, q2, ro1, ro2);
			pushThreshold (thresholdsCnt, balance, threshold);
		}
		for (int i = 0; i < thresholdsCnt; i++) {
			addThreshold (this.bestBalanceThresholdTab[i], thresholdTab[i]);
		}
	}

    public void setThreshold (float threshold, float value)
    {
        Arrays.fill(this.thresholdTab, 0);
        addThreshold (threshold, value);
    }
}
