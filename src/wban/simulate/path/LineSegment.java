package wban.simulate.path;

public class LineSegment {

    int fromX, fromY, toX, toY;
    String label = null;

    public LineSegment(int x1, int y1, int x2, int y2, String l) {
        fromX = x1;
        fromY = y1;
        toX = x2;
        toY = y2;
        label = l;
    }
    public LineSegment(Pt pt1, Pt pt2, String l) {
        fromX = pt1.getX();
        fromY = pt1.getY();
        toX = pt2.getX();
        toY = pt2.getY();
        label = l;
    }

    public void setLabel(String l) {
        label = l;
    }

    public int[] getFromTo() {
        return new int[] {fromX, fromY, toX, toY};
    }

    public String getLabel() {
        return label;
    }

}
