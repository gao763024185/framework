package com.cloudm.framework.common.util;

import java.text.DecimalFormat;

/**
 * @description: 机器工作数就算据帮助类
 * @author: wangbo
 * @date: 2018/3/12
 * @version: V1.0
 */
public class MachineDataUtil {


    /**
     * 获取油位
     *
     * @param up        电阻上限值
     * @param down      电阻下限值
     * @param r         当前电阻值
     * @param levelType 液位类型
     * @return
     */
    public static int getOilLevel(double up, double down, double r, int levelType) {
        if (r > up) {
            r = up;
        }
        if (r < down) {
            r = down;
        }

        double k1 = 2.5;
        double k3 = 0.4;
        double k = k1;
        if (levelType == 3) {
            k = k3;
            // 获取中值
            double middle = (up + k * down) / (k + 1);
            if (r > middle) {
                return 50 + (int) (((r - middle) / (up - middle)) * 50);
            } else {
                return (int) (((r - down) / (middle - down)) * 50);
            }
        } else if (levelType == 1) {
            // 获取中值
            double middle = (up + k * down) / (k + 1);
            if (r > middle) {
                return 50 - (int) (((r - middle) / (up - middle)) * 50);
            } else {
                return 100 - (int) (((r - down) / (middle - down)) * 50);
            }
        } else if (levelType == 4) {
            double len = up - down;
            return 100 - (int) (((r - down) / len) * 100);
        } else {
            double len = up - down;
            return (int) (((r - down) / len) * 100);
        }
    }

    /**
     * 获取油位
     *
     * @param up          电阻上限值
     * @param down        电阻下限值
     * @param middle      电阻中值
     * @param oneForth    1/4电阻
     * @param threeForths 3/4电阻
     * @param r           当前电阻值
     * @param levelType   液位类型
     * @return
     */
//    public static int getOilLevel(double up, double down, int middle, int oneForth, int threeForths, double r, int levelType) {
//        if (r > up) {
//            r = up;
//        }
//        if (r < down) {
//            r = down;
//        }
//        if (levelType == 3 || levelType == 5) {
//            if (r > middle) {
//                return 50 + getHalfLevel(up, middle, threeForths, r, levelType);
//            } else {
//                return getHalfLevel(middle, down, oneForth, r, levelType);
//            }
//        } else if (levelType == 1 || levelType == 4) {
//            if (r > middle) {
//                return 50 - getHalfLevel(up, middle, threeForths, r, levelType);
//            } else {
//                return 100 - getHalfLevel(middle, down, oneForth, r, levelType);
//            }
//        } else {
//            double len = up - down;
//            return (int) (((r - down) / len) * 100);
//        }
//    }


    /**
     * 获取油位
     *
     * @param up          电阻上限值
     * @param down        电阻下限值
     * @param middle      电阻中值
     * @param oneForth    1/4电阻
     * @param threeForths 3/4电阻
     * @param r           当前电阻值
     * @return
     */
    public static int getOilLevel(double up, double down, int middle, int oneForth, int threeForths, double r,int levelType) {
        if (down < up) {
            if (r < down) {
                return 0;
            } else if (r < oneForth && r >= down && oneForth != down) {
                return getData(oneForth, down, r, 0);
            } else if (r < middle && r >= oneForth && oneForth != middle) {
                return getData(middle, oneForth, r, 25);
            } else if (r < threeForths && r >= middle && middle != threeForths) {
                return getData(threeForths, middle, r, 50);
            } else if (r < up && r >= threeForths && threeForths != up) {
                return getData(up, threeForths, r, 75);
            } else {
                return 100;
            }
        } else {
            if (r > down) {
                return 0;
            } else if (r <= down && r > oneForth && oneForth != down) {
                return getData(oneForth, down, r, 0);
            } else if (r <= oneForth && r > middle && oneForth != middle) {
                return getData(middle, oneForth, r, 25);
            } else if (r <= middle && r > threeForths && middle != threeForths) {
                return getData(threeForths, middle, r, 50);
            } else if (r <= threeForths && r > up && threeForths != up) {
                return getData(up, threeForths, r, 75);
            } else {
                return 100;
            }
        }
    }

    /**
     * 通用公式
     *
     * @param up
     * @param down
     * @param r
     * @param param
     * @return
     */
    private static int getData(double up, double down, double r, int param) {
        if (up == down) {
            return param + 25;
        }
        //四色五入转换成整数
        DecimalFormat df = new DecimalFormat("######0");
        return Integer.parseInt(df.format(25 / (up - down) * (r - down) + param));
    }

    /**
     * 获取电阻所占百分比
     *
     * @param up        电阻上限值
     * @param down      电阻下限值
     * @param middle    电阻中值
     * @param r         当前电阻值
     * @param levelType 液位类型
     * @return
     */
    private static int getHalfLevel(double up, double down, int middle, double r, int levelType) {

        int level = 0;
        if (r > up) {
            r = up;
        }
        if (r < down) {
            r = down;
        }
        // 没有中值 计算中值
        if (middle == 0) {
            middle = (int) (((up - down) / 2) + down);
        }

        if (r > middle) {
            level = 50 + (int) (((r - middle) / (up - middle)) * 50);
        } else {
            level = (int) (((r - down) / (middle - down)) * 50);
        }

        return (int) (level * 0.5);
    }


    public static void main(String[] args) {

//        System.out.println(getOilLevel(100, 0, 40, 25, 75, 78, 3));
//        System.out.println(getOilLevel(10, 80, 40, 61, 15, 33, 1));
//        System.out.println(getOilLevel(10, 80, 40, 61, 15, 33));
//        System.out.println(getOilLevel(93, 5, 27, 16, 63, 6));
//        System.out.println(getOilLevel(93, 5, 27, 16, 63, 50));
//        System.out.println(getOilLevel(26, 96, 66, 78, 43, 70));
        System.out.println(getOilLevel(93, 5, 16, 16, 63, 16,0));
    }

}
