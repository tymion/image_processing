package image.processing;

import java.awt.Shape;

public class Region
{
    int[] region;
    int width;
    int height;

    public Region(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.region = new int[width * height];
    }

    public void addShape(Shape shape)
    {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (shape.contains(i, j)) {
                    this.region[i* this.width + j] = 1;
                }
            }
        }
    }

    public void removeShape(Shape shape)
    {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (shape.contains(i, j)) {
                    this.region[i* this.width + j] = 0;
                }
            }
        }
    }

    public boolean contains(int x, int y)
    {
        return this.region[x * this.width + y] == 1 ? true : false;
    }
}
