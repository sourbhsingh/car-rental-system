package com.carrentalsystem.app.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.PublicKey;

@AllArgsConstructor
public enum CarType {
    SEDAN("Sedan"),
    SUV("SUV"),
    HATCHBACK("Hatchback"),
    CONVERTIBLE("Convertible"),
    COUPE("Coupe"),
    MINIVAN("Minivan"),
    PICKUP_TRUCK("Pickup Truck"),
    LUXURY("Luxury"),
    SPORTS("Sports"),;

    private final String type;

    public static boolean isValidType(String type) {
        for (CarType carType : CarType.values()) {
            if (carType.name().equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }
}
