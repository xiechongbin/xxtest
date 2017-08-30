package com.xiaoxie.weightrecord.bean;

/**
 * desc:图案锁九宫格的点
 * Created by xiaoxie on 2017/8/29.
 */
public class LockViewPoint {
    public static int BITMAP_NORMAL = 0; // 正常
    public static int BITMAP_ERROR = 1;  // 错误
    public static int BITMAP_PRESS = 2;  // 按下


    private String index; //九宫格中的点的下标（即每个点代表一个值）

    private int state;//点的状态

    private float x; //点的坐标
    private float y;

    public LockViewPoint() {
        super();
    }

    public LockViewPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public String getIndex() {
        return index;
    }

    public int getState() {
        return state;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    /**
     * 判断屏幕上的九宫格中的点能否可以进行连线
     *
     * @param a
     * @param moveX
     * @param moveY
     * @param radius 点bitmap的半径
     * @return 布尔型
     */
    public boolean isWith(LockViewPoint a, float moveX, float moveY, float radius) {
        float result = (float) Math.sqrt((a.getX() - moveX)
                * (a.getX() - moveX) + (a.getY() - moveY)
                * (a.getY() - moveY));
        if (result < 5 * radius / 4) {
            return true;
        }
        return false;
    }
}
