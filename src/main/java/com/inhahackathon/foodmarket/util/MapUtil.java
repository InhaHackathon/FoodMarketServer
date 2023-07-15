package com.inhahackathon.foodmarket.util;

import com.google.maps.*;
import com.google.maps.model.*;
import com.inhahackathon.foodmarket.type.entity.FoodBank;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URLEncoder;

@Service
public class MapUtil {
    public static void main(String[] args) {
        MapUtil mapUtil = new MapUtil();
        System.out.println(mapUtil.getAddress(37.45085008, 126.6543226));
    }

    public double calculateDistance(FoodBank foodBank, Double latitude, Double longitude) {
        double distance = getDirectDistance(latitude, longitude, foodBank.getLatitude(), foodBank.getLongitude());
        return distance;
    }

    public String getAddress(double latitude, double longitude) {
        // 구글 지도 API 클라이언트 생성
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyD6zRLTl6jWc8eLO2vchgPc61mYSv-WpfY")
                .build();

        // 역지오코딩 요청
        GeocodingResult[] results;
        try {
            results = GeocodingApi.newRequest(context)
                    .language("ko")  // 한글 주소 요청
                    .latlng(new LatLng(latitude, longitude))
                    .await();

            // 주소 반환
            if (results.length > 0) {
                String formattedAddress = results[0].formattedAddress;
                String koreanAddress = URLEncoder.encode(formattedAddress, "UTF-8");
                return URLDecoder.decode(koreanAddress, "UTF-8");
            } else {
                return "주소를 찾을 수 없습니다.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "주소 변환 오류";
        }
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
