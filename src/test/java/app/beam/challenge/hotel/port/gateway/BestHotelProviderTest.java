package app.beam.challenge.hotel.port.gateway;


import app.beam.challenge.hotel.gateway.HotelProviderGateway;
import app.beam.challenge.hotel.model.Hotel;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class BestHotelProviderTest {

    public static final String AMENITIES = "Amenities";
    private static final List<String> AMENITIES_LIST = Arrays.asList(AMENITIES);
    private static final Double PRICE = 1.1;
    private static final Integer RATE = 1;
    private static final String HOTEL_NAME = "hotelName";
    private static final LocalDate FROM_DATE = LocalDate.now();
    private static final LocalDate TO_DATE = LocalDate.now().plusDays(1);
    private static final String SOME_CITY_AIRPORT = "someCityAirport";
    private static final Integer NUMBER_OF_ADULTS = 1;
    private final String apiLink = "apiLink";

    @Test
    public void givenBestHotelProvider_whenCallingGetHotels_thenSimpleHotelList(){
        //Given
        BestHotelProvider bestHotelProvider = new BestHotelProvider(new RestTemplate(){
            @Override
            public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
                if (url.equals(apiLink) && checkParams(uriVariables))
                    return (ResponseEntity<T>) ResponseEntity
                            .ok(
                                    new BestHotelProvider.BestHotelsList(
                                            Arrays.asList(
                                                    new BestHotelProvider.BestHotel(
                                                            HOTEL_NAME
                                                            , RATE
                                                            , PRICE
                                                            , AMENITIES
                                                    ))));
                throw new IllegalArgumentException();
            }
        },apiLink );

        //WHEN
        List<Hotel> hotels = bestHotelProvider.getHotels(new HotelProviderGateway.Request() {
            @Override
            public LocalDate getFromDate() {
                return FROM_DATE;
            }

            @Override
            public LocalDate getToDate() {
                return TO_DATE;
            }

            @Override
            public String getCityAirport() {
                return SOME_CITY_AIRPORT;
            }

            @Override
            public Integer getNumberOfAdults() {
                return NUMBER_OF_ADULTS;
            }
        });

        //THEN
        Hotel hotel = hotels.get(0);

        assertEquals(HOTEL_NAME,hotel.getName());
        assertEquals("Best Hotel",hotel.getProvider());
        assertEquals(RATE,hotel.getRate());
        assertEquals(AMENITIES_LIST,hotel.getAmenities());
        assertEquals(PRICE,hotel.getFarePerNight());





    }

    private boolean checkParams(Map<String,?> uriVariables) {
        if( SOME_CITY_AIRPORT.equals(uriVariables.get("city"))&&
            FROM_DATE.equals(uriVariables.get("fromDate")) &&
            TO_DATE.equals(uriVariables.get("toDate")) &&
            NUMBER_OF_ADULTS.equals(uriVariables.get("numberOfAdults")))
            return true;
        return false;
    }


}