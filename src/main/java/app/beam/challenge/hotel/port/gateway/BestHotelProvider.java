package app.beam.challenge.hotel.port.gateway;

import app.beam.challenge.hotel.gateway.HotelProviderGateway;
import app.beam.challenge.hotel.model.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

public class BestHotelProvider implements HotelProviderGateway {

    private final RestTemplate restTemplate ;

    private final String apiUrl;

    public BestHotelProvider(RestTemplate restTemplate, String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }


    @Override
    public List<Hotel> getHotels(Request request) {
        ResponseEntity<BestHotelsList> bestHotels = restTemplate.getForEntity(apiUrl, BestHotelsList.class ,mapToApiRequestParams(request));

        return bestHotels.getBody().getBestHotels().stream().map(BestHotelProvider::mapToHotelModel).collect(Collectors.toList());
    }
    private static Hotel mapToHotelModel(BestHotel b) {

        return Objects.nonNull(b)?
                new Hotel("Best Hotel", b.getHotel(), b.getHotelFare(), Arrays.asList(StringUtils.split(b.getRoomAmenities(), ",")), b.getHotelRate())
                :null;
    }

    private static HashMap<String, Object> mapToApiRequestParams(Request request) {
        HashMap<String, Object> requestParams = new HashMap<>();
        requestParams.put("city",request.getCityAirport());
        requestParams.put("fromDate",request.getFromDate());
        requestParams.put("toDate",request.getToDate());
        requestParams.put("numberOfAdults",request.getNumberOfAdults());
        return requestParams;
    }

    @AllArgsConstructor
    @Data
    static class BestHotelsList {
        private List<BestHotel> bestHotels = new ArrayList<>();
    }

    @AllArgsConstructor
    @Data
    static class BestHotel{
        private final String hotel;
        private final Integer hotelRate;
        private final Double hotelFare;
        private final String roomAmenities;
    }

}