import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tetris extends JPanel {

    private Block next;
    private Block now;

    // 是否在终端输出信息
    public static final boolean TerminalOutput = false;

    // 宽度和长度
    private static final int ROWS = 20;
    private static final int COLS = 10;

    // 得分
    private int score = 0;
    // 已消除的行数
    private int lines = 0;
    // 等级
    private int level = 0;
    // 方块框体
    private Cell[][] wall = new Cell[ROWS][COLS];
    // 游戏状态
    private boolean STATE = false;
    // 背景索引
    private int BGI = 0;
    // 像素宽度
    public static final int CELL_SIZE = 26;
    // 背景图片
    public static BufferedImage[] bgi = new BufferedImage[6];
    // 暂停
    public static BufferedImage pause;
    // 框架
    public static BufferedImage tetris;
    // 游戏结束
    public static BufferedImage gameover;

    static {
        try {
            pause = ImageIO.read(Tetris.class.getResource("img/pause.png"));
            tetris = ImageIO.read(Tetris.class.getResource("img/tetris.png"));
            gameover = ImageIO.read(Tetris.class.getResource("img/gameover.png"));
            for (int i = 0; i < bgi.length; i++) {
                String name = "img/bg" + i + ".jpg";
                bgi[i] = ImageIO.read(Tetris.class.getResource(name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听按键
     * 方块下落
     */
    public void action() {
        now = Block.randBlock();
        next = Block.randBlock();

        KeyListener keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyMoveAction(e.getKeyCode());
                repaint();
            }
        };
        this.addKeyListener(keyListener);
        this.setFocusable(true);
        this.requestFocus();

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int timer = 0;

            @Override
            public void run() {
                if (STATE) {
                    if (timer % (Math.max(1, 29 - level)) == 0) {
                        moveDownAction();
                        timer = 0;
                    }
                }
                timer++;
                repaint();
            }
        };
        timer.schedule(task, 10, 20);
    }

    /**
     * 按键功能映射
     * @param k 按键
     */
    public void keyMoveAction(int k) {
        switch (k) {
            case KeyEvent.VK_RIGHT:
                moveRightAction();
                break;
            case KeyEvent.VK_LEFT:
                moveLeftAction();
                break;
            case KeyEvent.VK_DOWN:
                moveDownAction();
                break;
            case KeyEvent.VK_UP:
                break;
            case KeyEvent.VK_Z:
                spinCellAnticlockwise();
                break;
            case KeyEvent.VK_X:
                spinCellClockwise();
                break;
            case KeyEvent.VK_SHIFT:
                moveInitAction();
                break;
            case KeyEvent.VK_ENTER:
                STATE = !STATE;
                break;
        }
    }

    /**
     * 初始化
     */
    public void moveInitAction() {
        BGI = (BGI == 5) ? 0 : BGI + 1;
        STATE = false;
        wall = new Cell[ROWS][COLS];
        now = Block.randBlock();
        next = Block.randBlock();
        score = 0;
        lines = 0;
        level = 0;
    }

    /**
     * 顺时针旋转方块
     */
    public void spinCellClockwise() {
        if(!STATE) return;
        now.clockwise();
        for (Cell cell : now.block) {
            if (now.col + cell.getCol() < 0 || now.col + cell.getCol() >= COLS) {
                now.anticlockwise();
                return;
            }
            if (now.row + cell.getRow() < 0) continue;
            if (now.row + cell.getRow() >= ROWS) {
                now.anticlockwise();
                return;
            }
            if (wall[now.row + cell.getRow()][now.col + cell.getCol()] != null) {
                now.anticlockwise();
                return;
            }
        }
    }

    /**
     * 逆时针旋转方块
     */
    public void spinCellAnticlockwise() {
        if(!STATE) return;
        now.anticlockwise();
        for (Cell cell : now.block) {
            if (now.col + cell.getCol() < 0 || now.col + cell.getCol() >= COLS) {
                now.clockwise();
                return;
            }
            if (now.row + cell.getRow() < 0) continue;
            if (now.row + cell.getRow() >= ROWS) {
                now.clockwise();
                return;
            }
            if (wall[now.row + cell.getRow()][now.col + cell.getCol()] != null) {
                now.clockwise();
                return;
            }
        }
    }

    /**
     * 向左移动方块
     */
    public void moveLeftAction() {
        if(!STATE) return;
        if (canLeftMove()) {
            now.moveLeft();
        }
    }

    /**
     * 判断是否能够向左移动
     * @return 结果
     */
    public boolean canLeftMove() {
        if (now == null) return false;
        int row;
        int col;
        StringBuilder outp = new StringBuilder("L: ");
        for (Cell c : now.block) {
            row = now.row + c.getRow();
            col = now.col + c.getCol();
            if (row < 0) continue;
            if (col <= 0) return false;
            if (wall[row][col - 1] != null) return false;
            outp.append("(").append(row).append(",").append(col).append(")");
        }
        if (TerminalOutput) System.out.println(outp);
        return true;
    }

    /**
     * 向右移动方块
     */
    public void moveRightAction() {
        if(!STATE) return;
        if (canRightMove()) {
            now.moveRight();
        }
    }

    /**
     * 判断是否能够向右移动方块
     * @return 结果
     */
    public boolean canRightMove() {
        if (now == null) return false;
        int row;
        int col;
        StringBuilder outp = new StringBuilder("R: ");
        for (Cell c : now.block) {
            row = now.row + c.getRow();
            col = now.col + c.getCol();
            if (row < 0) continue;
            outp.append("(").append(row).append(",").append(col).append(")");
            if (col + 1 == COLS || wall[row][col + 1] != null) return false;
        }
        if (TerminalOutput) System.out.println(outp);
        return true;
    }

    /**
     * 向下移动方块
     */
    public void moveDownAction() {
        if(!STATE) return;
        if (now == null) return;
        if (canDownMove()) {
            now.moveDown();
        } else {
            for (Cell c : now.block) {
                if (now.row + c.getRow() < 0) continue;
                wall[now.row + c.getRow()][now.col + c.getCol()] = c;
            }
            removeLine();
            now = next;
            next = Block.randBlock();
        }
    }

    /**
     * 判断能否向下移动
     * @return 结果
     */
    public boolean canDownMove() {
        if (now == null) return false;
        int row;
        int col;
        StringBuilder outp = new StringBuilder("D: ");
        for (Cell c : now.block) {
            row = now.row + c.getRow();
            col = now.col + c.getCol();
            outp.append("(").append(row).append(",").append(col).append(")");
            if (row < 0) continue;
            if (row + 1 == ROWS || wall[row + 1][col] != null) return false;
        }
        if (TerminalOutput) System.out.println(outp);
        return true;
    }

    /**
     * 删除行，并计算加分
     */
    public void removeLine() {
        boolean flag = true;
        int cnt = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (wall[row][col] == null) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                for (int col = 0; col < COLS; col++) {
                    wall[row][col] = null;
                }
                ++cnt;
                lines += 1;
                level = lines % 10 == 0 ? level + 1 : level;
                for (int row1 = row; row1 > 0; row1--) {
                    System.arraycopy(wall[row1 - 1], 0, wall[row1], 0, COLS);
                }
            } else {
                flag = true;
            }
        }
        switch (cnt) {
            case 1:
                score += 40 * (level + 1);
                break;
            case 2:
                score += 100 * (level + 1);
                break;
            case 3:
                score += 300 * (level + 1);
                break;
            case 4:
                score += 1200 * (level + 1);
                break;
        }
    }

    /**
     * 输出图形
     * @param g the <code>Graphics</code> context in which to paint
     */
    public void paint(Graphics g) {
        g.drawImage(bgi[BGI], -100, 0, null);
        g.drawImage(tetris, 0, 0, null);
        g.translate(15, 15);

        paintWall(g);
        paintBlock(g);
        paintNext(g);
        paintTabs(g);
        paintGamePause(g);
        paintGameOver(g);
    }

    /**
     * 游戏结束判断
     * @return 结果
     */
    public boolean isGameOver() {
        for (int col = 0; col < COLS; col++) {
            if (wall[0][col] != null) return true;
        }
        return false;
    }

    /**
     * 显示游戏结束
     * @param g the <code>Graphics</code> context in which to paint
     */
    public void paintGameOver(Graphics g) {
        if (isGameOver()) {
            now = null;
            g.drawImage(gameover, -15, -15, null);
            Color color = new Color(0, 71, 157);
            g.setColor(color);
            Font font = new Font(Font.SERIF, Font.BOLD, 30);
            g.setFont(font);
            g.drawString("" + score, 260, 207);
            g.drawString("" + lines, 260, 253);
            g.drawString("" + level, 260, 300);
            STATE = false;

        }
    }

    /**
     * 显示游戏暂停
     * @param g the <code>Graphics</code> context in which to paint
     */
    public void paintGamePause(Graphics g) {
        if (!STATE && !isGameOver()) {
            g.drawImage(pause, -15, -15, null);
        }
    }

    /**
     * 显示当前游戏信息
     * @param g the <code>Graphics</code> context in which to paint
     */
    public void paintTabs(Graphics g) {
        int x = 410;
        int y = 187;
        Color color = new Color(102, 119, 150);
        g.setColor(color);
        Font f = new Font(Font.SERIF, Font.BOLD, 30);
        g.setFont(f);
        g.drawString("" + score, x, y);
        y += 56;
        g.drawString("" + lines, x, y);
        y += 56;
        g.drawString("" + level, x, y);
    }

    /**
     * 显示下一个方块
     * @param g the <code>Graphics</code> context in which to paint
     */
    public void paintNext(Graphics g) {
        if (next == null) return;
        Cell[] cells = next.block;
        for (Cell c : cells) {
            int row = c.getRow() + 2;
            int col = c.getCol() + 10;
            int x = (next.col + col) * CELL_SIZE;
            int y = (next.row + row) * CELL_SIZE;
            g.drawImage(c.getImage(), x, y, null);
        }
    }

    String lastP = "";

    /**
     * 显示方块
     * @param g the <code>Graphics</code> context in which to paint
     */
    public void paintBlock(Graphics g) {
        if (now == null) return;

        StringBuilder nowP = new StringBuilder("Block: ");
        for (Cell c : now.block) {
            int col = c.getCol();
            int row = c.getRow();
            int x = (now.col + col) * CELL_SIZE;
            int y = (now.row + row) * CELL_SIZE;
            nowP.append("(").append(now.row + row).append(",").append(now.col + col).append(")");
            if (now.row + row < 0) continue;
            g.drawImage(c.getImage(), x, y, null);
        }
        if (TerminalOutput && !lastP.equals(nowP.toString())) {
            lastP = nowP.toString();
            System.out.println(lastP);
        }
    }

    /**
     * 显示方块区域
     * @param g the <code>Graphics</code> context in which to paint
     */
    public void paintWall(Graphics g) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Cell cell = wall[row][col];
                int rows = row * CELL_SIZE;
                int cols = col * CELL_SIZE;
                if (cell != null) {
                    g.drawImage(cell.getImage(), cols, rows, null);
                }
            }
        }
    }

    /**
     * 开始游戏
     */
    public static void run() {
        JFrame frame = new JFrame();
        Tetris tetris = new Tetris();

        frame.add(tetris);
        frame.setSize(620, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        tetris.action();
    }
}
