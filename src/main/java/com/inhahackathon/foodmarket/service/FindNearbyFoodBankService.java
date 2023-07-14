package com.inhahackathon.foodmarket.service;

import com.inhahackathon.foodmarket.type.entity.FoodBank;
import com.inhahackathon.foodmarket.type.entity.FoodBankDto;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FindNearbyFoodBankService {

    private static final double EARTH_RADIUS = 6371.0; // 지구 반지름 (단위: km)

    public static List<FoodBankDto> calAllDistance(double curLat, double curLon){


        List<FoodBank> foodBanks;
        Map<FoodBank, Double> foodBanksAndDistance = new HashMap<>();
        List<FoodBankDto> nearbyFoodBanks = new ArrayList<>();

        //모든 FoodBank 정보를 가져와 5km 이내의 FoodBank만 저장함
        foodBanks = FoodBankService.getAll();
        for ( FoodBank fb : foodBanks) {
            double distance = calculateDistance(curLat, curLon, fb.getLatitude(), fb.getLongitude());
//            System.out.print("curLat = " + curLat);
//            System.out.print(" / curLon = " + curLon);
//            System.out.print(" / fb.getLatitude() = " + fb.getLatitude());
//            System.out.println(" / fb.getLongitude() = " + fb.getLongitude());
            if ( distance <= 5000 ){

                FoodBankDto foodBankDto = new FoodBankDto();
                foodBankDto.setFoodBankId( fb.getFoodBankId() );
                foodBankDto.setDistrict( fb.getDistrict() );
                foodBankDto.setCenterType( fb.getCenterType() );
                foodBankDto.setName( fb.getName() );
                foodBankDto.setTel( fb.getTel() );
                foodBankDto.setAddress( fb.getAddress() );
                foodBankDto.setDetailAddress( fb.getDetailAddress() );
                foodBankDto.setLatitude(fb.getLatitude() );
                foodBankDto.setLatitude( fb.getLongitude() );
                foodBankDto.setDirectDistance( distance );

                foodBanksAndDistance.put(fb, distance);

                nearbyFoodBanks.add( foodBankDto );
            }
        }

        Comparator<FoodBankDto> comparator = new Comparator<FoodBankDto>() {
            @Override
            public int compare(FoodBankDto o1, FoodBankDto o2) {
                return (int)(o1.getDirectDistance() - o2.getDirectDistance());
            }
        };

        // Double 값을 기준으로 오름차순 정렬
        Collections.sort(nearbyFoodBanks, comparator);

        // 최대 5개까지만 유지
        if (nearbyFoodBanks.size() > 10) {
            nearbyFoodBanks = nearbyFoodBanks.subList(0, 5);
        }

        return nearbyFoodBanks;
    }

    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // 위도 차이와 경도 차이 계산
        double latDiff = lat2 - lat1;
        double lonDiff = lon2 - lon1;

        // 직선 거리 계산
        double distance = Math.sqrt(Math.pow(latDiff, 2) + Math.pow(lonDiff, 2)) * 111000;

        return distance;
    }
}
