import static org.junit.jupiter.api.Assert.*;
import org.junit.jupiter.api.Test;

public class LineTest {
    
    @Test
    public void getSlopeTest() {
        Line l1 = new Line(0, 0, 1, 1);
        Line l2 = new Line(1, 2, 5, 7);

        assertEquals(l1.getSlope(), 1.0);
        assertEquals(l2.getSlope(), 1.25);
    }

    @Test
    public void getDistanceTest() {
        Line l1 = new Line(1, 2, 5, 7);
        Line l2 = new Line(3, -2, 4, 8);

        assertEquals(l1.getDistance(), 6.4031242374328);
        assertEquals(l2.getDistance(), 10.049875621121);
    }

    @Test
    public void parallelToTest() {
        Line l1 = new Line(0, 1, 1, 2);
        Line l2 = new Line(1, 0, 2, 1);

        assertTrue(l1.parallelTo(l2));

        Line l3 = new Line(1, 1, 6, 1);
        assertFalse(l1.parallelTo(l3));
    }
}

class Line {
    // construct a line object
    public Line(double x0, double y0, double x1, double y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    // calcuulate the slope of the line
    public double getSlope() {
        // avoid dividing by zero
        if(x1==x0) {
            throw new ArithmeticException();
        }

        return (y1 - y0)/(x1 - x0);
    }

    // calculate the distance of the line
    public double getDistance() {
        return Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
    }

    // return whether a line is parallel to another
    public boolean parallelTo(Line l) {
        // if the difference between the slopes is very small, consider them parallel
        if(Math.abs(getSlope() - l.getSlope()) < .0001) {
            return true;
        } else {
            return false;
        }
    }

    // private member data
    private double x0, y0, x1, y1;
}