import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Cell {
    // 每种方块的样子
    public static BufferedImage Z;
    public static BufferedImage S;
    public static BufferedImage J;
    public static BufferedImage L;
    public static BufferedImage O;
    public static BufferedImage I;
    public static BufferedImage T;
    static {
        try {
            Z = ImageIO.read(Cell.class.getResource("img/Z.png"));
            S = ImageIO.read(Cell.class.getResource("img/S.png"));
            J = ImageIO.read(Cell.class.getResource("img/J.png"));
            T = ImageIO.read(Cell.class.getResource("img/T.png"));
            O = ImageIO.read(Cell.class.getResource("img/O.png"));
            I = ImageIO.read(Cell.class.getResource("img/I.png"));
            L = ImageIO.read(Cell.class.getResource("img/L.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 当前方块的相对位置
    private int row;
    private int col;
    // 当前方块的样子
    private BufferedImage image;

    public Cell() {}

    public Cell(int row, int col, BufferedImage image) {
        this.row = row;
        this.col = col;
        this.image = image;
    }

    public void moveDown() {
        ++row;
    }

    public void moveLeft() {
        --col;
    }

    public void moveRight() {
        ++col;
    }

    public String toString(){
        return "row: " + row + "col: " + col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
