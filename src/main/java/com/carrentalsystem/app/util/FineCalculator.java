package com.carrentalsystem.app.util;

public class FineCalculator {
    public static double calculateFine(int daysOverdue, double dailyFineRate) {
        if (daysOverdue <= 0 || dailyFineRate < 0) {
            return 0.0;
        }
        return daysOverdue * dailyFineRate;
    }
    //calculate fine on damage ->
    public static double calculateDamageFine(double damageCost, double damageFineRate) {
        if (damageCost <= 0 || damageFineRate < 0) {
            return 0.0;
        }
        return damageCost * damageFineRate;
    }
}
