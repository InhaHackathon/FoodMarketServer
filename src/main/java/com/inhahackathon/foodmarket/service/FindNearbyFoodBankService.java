package com.inhahackathon.foodmarket.service;

import com.inhahackathon.foodmarket.type.entity.FoodBank;

import java.util.*;

public class FindNearbyFoodBankService {

    private void calAllDistance(double curLat, double curLon){

        Map<FoodBank, Double> nearbyFoodBanks = new HashMap<>();

        //모든 FoodBank 정보를 가져와 5km 이내의 FoodBank만 저장함
        List<FoodBank> all = FoodBankService.getAll();
        for ( FoodBank fb : all) {
            double distance = calDistance(curLat, curLon, fb.getLatitude(), fb.getLongitude());

            if ( distance <= 5000 ){
                nearbyFoodBanks.put(fb, distance);
            }
        }

        // Map을 List로 변환하여 정렬
        List<Map.Entry<FoodBank, Double>> nearbyFoodBankList = new ArrayList<>(nearbyFoodBanks.entrySet());

        // Double 값을 기준으로 오름차순 정렬
        Collections.sort(nearbyFoodBankList, Comparator.comparing(Map.Entry::getValue));

        // 최대 5개까지만 유지
        if (nearbyFoodBankList.size() > 5) {
            nearbyFoodBankList = nearbyFoodBankList.subList(0, 5);
        }


    }

    private double calDistance(double lat1, double lon1, double lat2, double lon2){
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))* Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))*Math.cos(deg2rad(lat2))*Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60*1.1515*1609.344;

        return dist; //단위 meter
    }
    //10진수를 radian(라디안)으로 변환
    private double deg2rad(double deg){
        return (deg * Math.PI/180.0);
    }
    //radian(라디안)을 10진수로 변환
    private double rad2deg(double rad){
        return (rad * 180 / Math.PI);
    }

}
