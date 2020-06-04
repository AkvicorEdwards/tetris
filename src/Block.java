import java.util.Arrays;
import java.util.Random;

// 方块的方向
enum DIRECT {
    L, R, U, D
}

public abstract class Block {

    // 方块的方向
    public DIRECT direct = DIRECT.L;
    // 方块中心所在的坐标
    public int row;
    public int col;
    // 方块实体
    protected Cell[] block = new Cell[4];

    /**
     * 向下移动
     */
    public void moveDown() {
        ++row;
    }

    /**
     * 向左移动
     */
    public void moveLeft() {
        --col;
    }

    /**
     * 向右移动
     */
    public void moveRight() {
        ++col;
    }

    /**
     * 顺时针旋转
     */
    protected abstract void clockwise();

    /**
     * 逆时针旋转
     */
    protected abstract void anticlockwise();

    protected abstract void print();

    private static final Random random = new Random();
    private static int lastBlock = -1;

    /**
     * 随机生成一个与上个方块不同的方块
     * @return 方块
     */
    public static Block randBlock() {
        int c = random.nextInt(7);
        while (c == lastBlock) {
            c = random.nextInt(7);
        }
        lastBlock = c;

        switch (c) {
            case 0:
                return new Z();
            case 1:
                return new J();
            case 2:
                return new O();
            case 3:
                return new L();
            case 4:
                return new S();
            case 5:
                return new T();
            case 6:
            default:
                return new I();
        }
    }
}

/**
 * I 方块的结构
 */
class I extends Block {
    public I() {
        direct = DIRECT.D;
        row = 0;
        col = 5;
        block[0] = new Cell(0, -2, Cell.I);
        block[1] = new Cell(0, -1, Cell.I);
        block[2] = new Cell(0, 0, Cell.I);
        block[3] = new Cell(0, 1, Cell.I);
    }

    public void clockwise() {
        switch (direct) {
            case D:
                direct = DIRECT.L;

                block[0].setRow(-2);
                block[0].setCol(0);

                block[1].setRow(-1);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(1);
                block[3].setCol(0);

                break;
            case L:
                direct = DIRECT.U;

                block[0].setRow(0);
                block[0].setCol(-2);

                block[1].setRow(0);
                block[1].setCol(-1);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(0);
                block[3].setCol(1);

                break;
            case U:
                direct = DIRECT.R;

                block[0].setRow(-2);
                block[0].setCol(0);

                block[1].setRow(-1);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(1);
                block[3].setCol(0);

                break;
            case R:
                direct = DIRECT.D;

                block[0].setRow(0);
                block[0].setCol(-2);

                block[1].setRow(0);
                block[1].setCol(-1);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(0);
                block[3].setCol(1);

                break;
            default:
                System.out.println("Default");
        }
    }

    public void anticlockwise() {
        switch (direct) {
            case D:
                direct = DIRECT.R;

                block[0].setRow(-2);
                block[0].setCol(0);

                block[1].setRow(-1);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(1);
                block[3].setCol(0);

                break;
            case L:
                direct = DIRECT.D;

                block[0].setRow(0);
                block[0].setCol(-2);

                block[1].setRow(0);
                block[1].setCol(-1);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(0);
                block[3].setCol(1);

                break;
            case U:
                direct = DIRECT.L;

                block[0].setRow(-2);
                block[0].setCol(0);

                block[1].setRow(-1);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(1);
                block[3].setCol(0);
                break;
            case R:
                direct = DIRECT.U;

                block[0].setRow(0);
                block[0].setCol(-2);

                block[1].setRow(0);
                block[1].setCol(-1);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(0);
                block[3].setCol(1);

                break;
            default:
                System.out.println("Default");
        }
    }

    public void print(){
        char[][] map = new char[4][4];
        for (char[] s : map) {
            Arrays.fill(s, 'o');
        }
        for(Cell cell : block) {
            map[2+cell.getRow()][2+cell.getCol()] = 'x';
        }
        for(char[] s : map) {
            for(char c : s) {
                System.out.print(c);
                System.out.print(' ');
            }
            System.out.println("");
        }
    }
}

/**
 * O 方块的结构
 */
class O extends Block {
    public O() {
        direct = DIRECT.D;
        row = 0;
        col = 5;
        block[0] = new Cell(1, 1, Cell.O);
        block[1] = new Cell(0, 1, Cell.O);
        block[2] = new Cell(0, 0, Cell.O);
        block[3] = new Cell(1, 0, Cell.O);
    }

    public void clockwise() {}

    public void anticlockwise() {}

    public void print(){
        char[][] map = new char[2][2];
        for (char[] s : map) {
            Arrays.fill(s, 'o');
        }
        for(Cell cell : block) {
            map[2+cell.getRow()][2+cell.getCol()] = 'x';
        }
        for(char[] s : map) {
            for(char c : s) {
                System.out.print(c);
                System.out.print(' ');
            }
            System.out.println("");
        }
    }
}

/**
 * T 方块的结构
 */
class T extends Block {
    public T() {
        direct = DIRECT.U;
        row = 0;
        col = 5;
        block[0] = new Cell(0, -1, Cell.T);
        block[1] = new Cell(-1, 0, Cell.T);
        block[2] = new Cell(0, 0, Cell.T);
        block[3] = new Cell(0, 1, Cell.T);
    }

    public void clockwise() {
        switch (direct) {
            case D:
                direct = DIRECT.L;

                block[0].setRow(1);
                block[0].setCol(0);

                block[1].setRow(0);
                block[1].setCol(-1);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(-1);
                block[3].setCol(0);

                break;
            case L:
                direct = DIRECT.U;

                block[0].setRow(0);
                block[0].setCol(-1);

                block[1].setRow(-1);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(0);
                block[3].setCol(1);

                break;
            case U:
                direct = DIRECT.R;

                block[0].setRow(-1);
                block[0].setCol(0);

                block[1].setRow(0);
                block[1].setCol(1);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(1);
                block[3].setCol(0);

                break;
            case R:
                direct = DIRECT.D;

                block[0].setRow(0);
                block[0].setCol(1);

                block[1].setRow(1);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(0);
                block[3].setCol(-1);

                break;
            default:
                System.out.println("Default");
        }
    }

    public void anticlockwise() {
        switch (direct) {
            case D:
                direct = DIRECT.R;

                block[0].setRow(-1);
                block[0].setCol(0);

                block[1].setRow(0);
                block[1].setCol(1);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(1);
                block[3].setCol(0);

                break;
            case L:
                direct = DIRECT.D;

                block[0].setRow(0);
                block[0].setCol(1);

                block[1].setRow(1);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(0);
                block[3].setCol(-1);

                break;
            case U:
                direct = DIRECT.L;

                block[0].setRow(1);
                block[0].setCol(0);

                block[1].setRow(0);
                block[1].setCol(-1);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(-1);
                block[3].setCol(0);

                break;
            case R:
                direct = DIRECT.U;

                block[0].setRow(0);
                block[0].setCol(-1);

                block[1].setRow(-1);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(0);
                block[3].setCol(1);

                break;
            default:
                System.out.println("Default");
        }
    }

    public void print(){
        char[][] map = new char[4][4];
        for (char[] s : map) {
            Arrays.fill(s, 'o');
        }
        for(Cell cell : block) {
            map[2+cell.getRow()][2+cell.getCol()] = 'x';
        }
        for(char[] s : map) {
            for(char c : s) {
                System.out.print(c);
                System.out.print(' ');
            }
            System.out.println("");
        }
    }
}

/**
 * L 方块的结构
 */
class L extends Block {
    public L() {
        direct = DIRECT.U;
        row = 0;
        col = 5;
        block[0] = new Cell(0, -1, Cell.L);
        block[1] = new Cell(0, 0, Cell.L);
        block[2] = new Cell(0, 1, Cell.L);
        block[3] = new Cell(-1, 1, Cell.L);
    }

    public void clockwise() {
        switch (direct) {
            case D:
                direct = DIRECT.L;

                block[0].setRow(-1);
                block[0].setCol(-1);

                block[1].setRow(-1);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(1);
                block[3].setCol(0);

                break;
            case L:
                direct = DIRECT.U;

                block[0].setRow(0);
                block[0].setCol(-1);

                block[1].setRow(0);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(1);

                block[3].setRow(-1);
                block[3].setCol(1);

                break;
            case U:
                direct = DIRECT.R;

                block[0].setRow(-1);
                block[0].setCol(0);

                block[1].setRow(0);
                block[1].setCol(0);

                block[2].setRow(1);
                block[2].setCol(0);

                block[3].setRow(1);
                block[3].setCol(1);

                break;
            case R:
                direct = DIRECT.D;

                block[0].setRow(1);
                block[0].setCol(-1);

                block[1].setRow(0);
                block[1].setCol(-1);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(0);
                block[3].setCol(1);

                break;
            default:
                System.out.println("Default");
        }
    }

    public void anticlockwise() {
        switch (direct) {
            case D:
                direct = DIRECT.R;

                block[0].setRow(-1);
                block[0].setCol(0);

                block[1].setRow(0);
                block[1].setCol(0);

                block[2].setRow(1);
                block[2].setCol(0);

                block[3].setRow(1);
                block[3].setCol(1);

                break;
            case L:
                direct = DIRECT.D;

                block[0].setRow(1);
                block[0].setCol(-1);

                block[1].setRow(0);
                block[1].setCol(-1);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(0);
                block[3].setCol(1);

                break;
            case U:
                direct = DIRECT.L;

                block[0].setRow(-1);
                block[0].setCol(-1);

                block[1].setRow(-1);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(1);
                block[3].setCol(0);

                break;
            case R:
                direct = DIRECT.U;

                block[0].setRow(0);
                block[0].setCol(-1);

                block[1].setRow(0);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(1);

                block[3].setRow(-1);
                block[3].setCol(1);

                break;
            default:
                System.out.println("Default");
        }
    }

    public void print(){
        char[][] map = new char[4][4];
        for (char[] s : map) {
            Arrays.fill(s, 'o');
        }
        for(Cell cell : block) {
            map[2+cell.getRow()][2+cell.getCol()] = 'x';
        }
        for(char[] s : map) {
            for(char c : s) {
                System.out.print(c);
                System.out.print(' ');
            }
            System.out.println("");
        }
    }
}

/**
 * J 方块的结构
 */
class J extends Block {
    public J() {
        direct = DIRECT.U;
        row = 0;
        col = 5;
        block[0] = new Cell(-1, -1, Cell.J);
        block[1] = new Cell(0, -1, Cell.J);
        block[2] = new Cell(0, 0, Cell.J);
        block[3] = new Cell(0, 1, Cell.J);
    }

    public void clockwise() {
        switch (direct) {
            case D:
                direct = DIRECT.L;

                block[0].setRow(1);
                block[0].setCol(-1);

                block[1].setRow(1);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(-1);
                block[3].setCol(0);

                break;
            case L:
                direct = DIRECT.U;

                block[0].setRow(-1);
                block[0].setCol(-1);

                block[1].setRow(0);
                block[1].setCol(-1);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(0);
                block[3].setCol(1);

                break;
            case U:
                direct = DIRECT.R;

                block[0].setRow(-1);
                block[0].setCol(1);

                block[1].setRow(-1);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(1);
                block[3].setCol(0);

                break;
            case R:
                direct = DIRECT.D;

                block[0].setRow(0);
                block[0].setCol(-1);

                block[1].setRow(0);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(1);

                block[3].setRow(1);
                block[3].setCol(1);

                break;
            default:
                System.out.println("Default");
        }
    }

    public void anticlockwise() {
        switch (direct) {
            case D:
                direct = DIRECT.R;

                block[0].setRow(-1);
                block[0].setCol(1);

                block[1].setRow(-1);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(1);
                block[3].setCol(0);

                break;
            case L:
                direct = DIRECT.D;

                block[0].setRow(0);
                block[0].setCol(-1);

                block[1].setRow(0);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(1);

                block[3].setRow(1);
                block[3].setCol(1);

                break;
            case U:
                direct = DIRECT.L;

                block[0].setRow(1);
                block[0].setCol(-1);

                block[1].setRow(1);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(-1);
                block[3].setCol(0);

                break;
            case R:
                direct = DIRECT.U;

                block[0].setRow(-1);
                block[0].setCol(-1);

                block[1].setRow(0);
                block[1].setCol(-1);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(0);
                block[3].setCol(1);

                break;
            default:
                System.out.println("Default");
        }
    }

    public void print(){
        char[][] map = new char[4][4];
        for (char[] s : map) {
            Arrays.fill(s, 'o');
        }
        for(Cell cell : block) {
            map[2+cell.getRow()][2+cell.getCol()] = 'x';
        }
        for(char[] s : map) {
            for(char c : s) {
                System.out.print(c);
                System.out.print(' ');
            }
            System.out.println("");
        }
    }
}

/**
 * S 方块的结构
 */
class S extends Block {
    public S() {
        direct = DIRECT.U;
        row = 0;
        col = 5;
        block[0] = new Cell(0, -1, Cell.S);
        block[1] = new Cell(0, 0, Cell.S);
        block[2] = new Cell(-1, 0, Cell.S);
        block[3] = new Cell(-1, 1, Cell.S);
    }

    public void clockwise() {
        switch (direct) {
            case D:
                direct = DIRECT.L;

                block[0].setRow(-1);
                block[0].setCol(-1);

                block[1].setRow(0);
                block[1].setCol(-1);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(1);
                block[3].setCol(0);

                break;
            case L:
                direct = DIRECT.U;

                block[0].setRow(0);
                block[0].setCol(-1);

                block[1].setRow(0);
                block[1].setCol(0);

                block[2].setRow(-1);
                block[2].setCol(0);

                block[3].setRow(-1);
                block[3].setCol(1);

                break;
            case U:
                direct = DIRECT.R;

                block[0].setRow(-1);
                block[0].setCol(0);

                block[1].setRow(0);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(1);

                block[3].setRow(1);
                block[3].setCol(1);

                break;
            case R:
                direct = DIRECT.D;

                block[0].setRow(1);
                block[0].setCol(-1);

                block[1].setRow(1);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(0);
                block[3].setCol(1);

                break;
            default:
                System.out.println("Default");
        }
    }

    public void anticlockwise() {
        switch (direct) {
            case D:
                direct = DIRECT.R;

                block[0].setRow(-1);
                block[0].setCol(0);

                block[1].setRow(0);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(1);

                block[3].setRow(1);
                block[3].setCol(1);

                break;
            case L:
                direct = DIRECT.D;

                block[0].setRow(1);
                block[0].setCol(-1);

                block[1].setRow(1);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(0);
                block[3].setCol(1);

                break;
            case U:
                direct = DIRECT.L;

                block[0].setRow(-1);
                block[0].setCol(-1);

                block[1].setRow(0);
                block[1].setCol(-1);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(1);
                block[3].setCol(0);

                break;
            case R:
                direct = DIRECT.U;

                block[0].setRow(0);
                block[0].setCol(-1);

                block[1].setRow(0);
                block[1].setCol(0);

                block[2].setRow(-1);
                block[2].setCol(0);

                block[3].setRow(-1);
                block[3].setCol(1);

                break;
            default:
                System.out.println("Default");
        }
    }

    public void print(){
        char[][] map = new char[4][4];
        for (char[] s : map) {
            Arrays.fill(s, 'o');
        }
        for(Cell cell : block) {
            map[2+cell.getRow()][2+cell.getCol()] = 'x';
        }
        for(char[] s : map) {
            for(char c : s) {
                System.out.print(c);
                System.out.print(' ');
            }
            System.out.println("");
        }
    }
}

/**
 * Z 方块的结构
 */
class Z extends Block {
    public Z() {
        direct = DIRECT.U;
        row = 0;
        col = 5;
        block[0] = new Cell(-1, -1, Cell.Z);
        block[1] = new Cell(-1, 0, Cell.Z);
        block[2] = new Cell(0, 0, Cell.Z);
        block[3] = new Cell(0, 1, Cell.Z);
    }

    public void clockwise() {
        switch (direct) {
            case D:
                direct = DIRECT.L;

                block[0].setRow(-1);
                block[0].setCol(0);

                block[1].setRow(0);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(-1);

                block[3].setRow(1);
                block[3].setCol(-1);

                break;
            case L:
                direct = DIRECT.U;

                block[0].setRow(-1);
                block[0].setCol(-1);

                block[1].setRow(-1);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(0);
                block[3].setCol(1);

                break;
            case U:
                direct = DIRECT.R;

                block[0].setRow(-1);
                block[0].setCol(1);

                block[1].setRow(0);
                block[1].setCol(1);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(1);
                block[3].setCol(0);

                break;
            case R:
                direct = DIRECT.D;

                block[0].setRow(0);
                block[0].setCol(-1);

                block[1].setRow(0);
                block[1].setCol(0);

                block[2].setRow(1);
                block[2].setCol(0);

                block[3].setRow(1);
                block[3].setCol(1);

                break;
            default:
                System.out.println("Default");
        }
    }

    public void anticlockwise() {
        switch (direct) {
            case D:
                direct = DIRECT.R;

                block[0].setRow(-1);
                block[0].setCol(1);

                block[1].setRow(0);
                block[1].setCol(1);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(1);
                block[3].setCol(0);

                break;
            case L:
                direct = DIRECT.D;

                block[0].setRow(0);
                block[0].setCol(-1);

                block[1].setRow(0);
                block[1].setCol(0);

                block[2].setRow(1);
                block[2].setCol(0);

                block[3].setRow(1);
                block[3].setCol(1);

                break;
            case U:
                direct = DIRECT.L;

                block[0].setRow(-1);
                block[0].setCol(0);

                block[1].setRow(0);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(-1);

                block[3].setRow(1);
                block[3].setCol(-1);

                break;
            case R:
                direct = DIRECT.U;

                block[0].setRow(-1);
                block[0].setCol(-1);

                block[1].setRow(-1);
                block[1].setCol(0);

                block[2].setRow(0);
                block[2].setCol(0);

                block[3].setRow(0);
                block[3].setCol(1);
                break;
            default:
                System.out.println("Default");
        }
    }

    public void print(){
        char[][] map = new char[4][4];
        for (char[] s : map) {
            Arrays.fill(s, 'o');
        }
        for(Cell cell : block) {
            map[2+cell.getRow()][2+cell.getCol()] = 'x';
        }
        for(char[] s : map) {
            for(char c : s) {
                System.out.print(c);
                System.out.print(' ');
            }
            System.out.println("");
        }
    }
}