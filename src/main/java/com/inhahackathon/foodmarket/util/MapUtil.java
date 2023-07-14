package com.inhahackathon.foodmarket.util;

import com.inhahackathon.foodmarket.type.entity.FoodBank;
import org.springframework.stereotype.Service;

@Service
public class MapUtil {

    public double calculateDistance(FoodBank foodBank, Double latitude, Double longitude) {
        double distance = getDirectDistance(latitude, longitude, foodBank.getLatitude(), foodBank.getLongitude());
        return distance;
    }

    private double getDirectDistance(double lat1, double lon1, double lat2, double lon2) {
        // 위도 차이와 경도 차이 계산
        double latDiff = lat2 - lat1;
        double lonDiff = lon2 - lon1;

        // 직선 거리 계산
        double distance = Math.sqrt(Math.pow(latDiff, 2) + Math.pow(lonDiff, 2)) * 111000;

        return distance;
    }

}
