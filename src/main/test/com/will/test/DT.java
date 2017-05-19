package com.will.test;

/**
 * Created by zoumy on 2017/3/29 10:44.
 */
public class DT {
    private double a;
    private double b;

    public DT(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public DT plus(DT other) {
        return new DT(this.a + other.a, this.b + other.b);
    }

    public DT subtraction(DT other) {
        return new DT(this.a - other.a, this.b - other.b);
    }

    public double dotProduct(DT other) {
        return this.a * other.a + this.b * other.b;
    }


    @Override
    public String toString() {
        return "(" + this.a + "," + this.b + ")";
    }

    public static void main(String[] args) {
        DT dt1 = new DT(1, 1);
        DT dt2 = new DT(2, 3);
        DT dt3 = new DT(4, 5);

        System.out.println(dt1.plus(dt2));
        System.out.println(dt1.subtraction(dt2));
        System.out.println(dt1.dotProduct(dt3));
        dt1.print(9);
    }

    public void print(int row){
        for(int i = 1;i<= 2 * row - 1;i++){
            for (int j = 0;j<(2 * row - 1 - i)/2;j++){
                System.out.print(" ");
            }
            for(int j = 0;j<i;j++){
                System.out.print("*");
            }
            i++;
            System.out.println();
        }
    }
}
