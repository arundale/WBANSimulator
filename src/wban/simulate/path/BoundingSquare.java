package wban.simulate.path;

public class BoundingSquare {

    Pt v1, v2, v3, v4;
    int color_rgb;

    public BoundingSquare(int x1, int y1, int x2, int y2) {
        v1 = new Pt(x1, y1);
        v2 = new Pt(x1, y2);
        v3 = new Pt(x2, y2);
        v4 = new Pt(x2, y1);
        color_rgb = 0;
    }

    public void setColor(int c) {
        color_rgb = c;
    }

    public Pt[] getBounds() {
        return new Pt[] {v1, v2, v3, v4};
    }

    public int getColor() {
        return color_rgb;
    }

    public int getSide() {
        return v3.getX()-v2.getX();
    }

}
