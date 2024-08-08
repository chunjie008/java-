package com.ycnote.bazi.jw;
// 弧度转经纬度
public class 弧度转经纬度 {

    public static double[] 转经纬度(double radianLongitude,double radianLatitude) {

        // 示例弧度值
//        double radianLongitude = 2.0344439357957027; // 示例弧度值
//        double radianLatitude = 0.7285004297824339;  // 示例弧度值
        // 弧度转换为度数
        double degreeLongitude = radianToDegree(radianLongitude);
        double degreeLatitude = radianToDegree(radianLatitude);

        // 输出结果
//        System.out.println("经度 (度): " + degreeLongitude);
//        System.out.println("纬度 (度): " + degreeLatitude);
        return new double[] {degreeLongitude,degreeLatitude};
    }

    /**
     * 将弧度转换为度数
     *
     * @param radian 弧度
     * @return 度数
     */
    public static double radianToDegree(double radian) {
        return radian * (180 / Math.PI);
    }

    /**
     * 将度数转换为弧度
     *
     * @param degree 度数
     * @return 弧度
     */
    public static double degreeToRadian(double degree) {
        return degree * (Math.PI / 180);
    }
}