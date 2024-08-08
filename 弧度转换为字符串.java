package com.ycnote.bazi.jw;
// 弧度转换为字符串
public class 弧度转换为字符串 {

    // 弧度转换为字符串的方法
    public static String rad2str2(double radians) {
        // 定义字符串常量
        String s = "+";
        String degreeSymbol = "°";
        String minuteSymbol = "'";

        // 如果弧度为负数，取绝对值，并设置符号为负
        if (radians < 0) {
            radians = Math.abs(radians);
            s = "-";
        }

        // 将弧度转换为度数
        double degrees = radians * 180 / Math.PI;
        int wholeDegrees = (int) Math.floor(degrees); // 整数部分的度数
        int minutes = (int) Math.floor((degrees - wholeDegrees) * 60 + 0.5); // 转换为分并四舍五入

        // 处理分钟溢出
        if (minutes >= 60) {
            minutes -= 60;
            wholeDegrees++;
        }

        // 格式化度数和分数，确保宽度一致
        String degreeStr = String.format("%3d", wholeDegrees);
        String minuteStr = String.format("%02d", minutes);

        // 拼接结果字符串
        s += degreeStr + degreeSymbol;
        s += minuteStr + minuteSymbol;

        return s;
    }

    // 测试转换方法
    public static void main(String[] args) {
        double radians = -0.418879; // 示例输入（弧度）
        String result = rad2str2(radians);
        System.out.println("Converted angle: " + result);
    }
}
