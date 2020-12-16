public class assignment5 {
    public static void main(String[] args) {
        Rectangle rect = new Rectangle(10, 5);
        rect.display();
        System.out.println("Area = " + rect.calculateArea());
        System.out.println();

        Circle circle = new Circle(5);
        circle.display();
        System.out.println("Area = " + circle.calculateArea());
        System.out.println();

        Triangle triangle = new Triangle(10, 4);
        triangle.display();
        System.out.println("Area = " + triangle.calculateArea());
    }
}


interface Shape {
    public float calculateArea();

    public void display();
}

class Rectangle implements Shape {
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public float calculateArea() {
        return width * height;
    }

    public void display() {
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                if (i == 0 || i == height-1) System.out.print("_");
                else if (j == 0 || j == width-1) System.out.print("|");
                else System.out.print(" ");
            }
            System.out.println();
        }
    }
}

class Circle implements Shape {
    private int radius;

    public Circle(int radius) {
        this.radius = radius;
    }

    public float calculateArea() {
        return (float) Math.PI * radius * radius;
    }

    // This needs work. Only works with radius 5.
    public void display() {
        int before = radius-3;
        int after = radius+before+1;
        for (int i=0; i<radius+2; i++) {
            for (int j=0; j<radius*2+3; j++) {
                if (i==0 || i==radius+1) {
                    if (j == radius+1) System.out.print(".");
                    else System.out.print(" ");
                }
                else if (i == (radius+2)/2) {
                    if (j==0 || j==radius*2+2) System.out.print(".");
                    else System.out.print(" ");
                }
                else {
                    if (j==before || j==after+before) System.out.print(".");
                    else System.out.print(" ");
                }
            }
            System.out.println();
            if (i > 0 && i < (radius+2)/2) {
                before--;
                after += 2;
            }
            else if (i >= (radius+2)/2) {
                before++;
                after -= 2;
            }
        }
    }
}

class Triangle implements Shape {
    private int baselength;
    private int height;

    public Triangle(int baselength, int height) {
        this.baselength = baselength;
        this.height = height;
    }

    public float calculateArea() {
        return 0.5f * baselength * height;
    }

    //Does not look good for every value.
    public void display() {
        int spaces = 0;
        for (int i=0; i<height; i++) {
            for (int j=0; j<baselength; j++) {
                if (i == height-1) {
                    System.out.print(".");
                }

                else if (j == 0) System.out.print(".");
                else if (spaces > 0) {
                    for (int k=0; k<spaces; k++) {
                        System.out.print(" ");
                    }
                    System.out.print(".");
                    break;
                }
            }
            spaces += 3;
            System.out.println();
        }
    }
}