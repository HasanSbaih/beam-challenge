package app.beam.challenge.hotel.port.gateway;

import app.beam.challenge.hotel.gateway.HotelProviderGateway;
import app.beam.challenge.hotel.model.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CrazyHotelProvider implements HotelProviderGateway {

    private final RestTemplate restTemplate;

    private final String apiUrl;

    public CrazyHotelProvider(RestTemplate restTemplate, String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }


    @Override
    public List<Hotel> getHotels(Request request) {
        ResponseEntity<CrazyHotelsList> crazyHotels = restTemplate.getForEntity(apiUrl, CrazyHotelsList.class ,mapToApiRequestParams(request));

        return crazyHotels.getBody().getCrazyHotels().stream().map(CrazyHotelProvider::mapToHotelModel).collect(Collectors.toList());
    }
    private static HashMap<String, Object> mapToApiRequestParams(Request request) {
        HashMap<String, Object> requestParams = new HashMap<>();
        requestParams.put("city",request.getCityAirport());
        requestParams.put("from",request.getFromDate().atStartOfDay().toInstant(ZoneOffset.UTC));
        requestParams.put("to",request.getToDate().atStartOfDay().toInstant(ZoneOffset.UTC));
        requestParams.put("adultsCount",request.getNumberOfAdults());
        return requestParams;
    }

    private static Hotel mapToHotelModel(CrazyHotel b) {

        return Objects.nonNull(b)?
                new Hotel("Crazy Hotel", b.getHotelName(), getPrice(b.getPrice(),b.getDiscount()), b.getRoomAmenities(), StringUtils.countOccurrencesOf(b.getRate(),"*"))
                :null;
    }

    private static Double getPrice(Double priceForNight , Double discount){

        return Objects.nonNull(discount)? priceForNight+(priceForNight*discount) : priceForNight;
    }

    @AllArgsConstructor
    @Data
    static class CrazyHotelsList {
        private List<CrazyHotelProvider.CrazyHotel> crazyHotels = new ArrayList<>();
    }

    @AllArgsConstructor
    @Data
    static class CrazyHotel{
        private final String hotelName;
        private final String rate;
        private final Double price;
        private final Double discount;
        private final List<String> roomAmenities;
    }

}
