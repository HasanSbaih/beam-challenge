package app.beam.challenge.hotel.port.gateway;

import app.beam.challenge.hotel.gateway.HotelProviderGateway;
import app.beam.challenge.hotel.model.Hotel;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CrazyHotelProviderTest{


        public static final String AMENITIES = "Amenities";
        private static final List<String> AMENITIES_LIST = Arrays.asList(AMENITIES);
        private static final Double PRICE = 1.1;
        private static final Integer RATE = 1;
        private static final String RATE_AS_STARS = "*";
        private static final String HOTEL_NAME = "hotelName";
        private static final LocalDate FROM_DATE = LocalDate.now();
        private static final LocalDate TO_DATE = LocalDate.now().plusDays(1);
        private static final Instant FROM_DATE_INSTANT = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
        private static final Instant TO_DATE_INSTANT = LocalDate.now().plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        private static final String SOME_CITY_AIRPORT = "someCityAirport";
        private static final Integer NUMBER_OF_ADULTS = 1;
        private final String apiLink = "apiLink";

        @Test
        public void givenBestHotelProvider_whenCallingGetHotels_thenSimpleHotelList(){
            //Given


            CrazyHotelProvider crazyHotelProvider = new CrazyHotelProvider(new RestTemplate(){
            @Override
            public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
                if (url.equals(apiLink) && checkParams(uriVariables))
                    return (ResponseEntity<T>) ResponseEntity
                            .ok(
                                    new CrazyHotelProvider.CrazyHotelsList(
                                            Arrays.asList(
                                                    new CrazyHotelProvider.CrazyHotel(
                                                            HOTEL_NAME
                                                            , RATE_AS_STARS
                                                            , PRICE
                                                            ,null
                                                            , AMENITIES_LIST
                                                    ))));
                throw new IllegalArgumentException();
            }
        },apiLink );


            //WHEN
            List<Hotel> hotels = crazyHotelProvider.getHotels(new HotelProviderGateway.Request() {
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
            assertEquals("Crazy Hotel",hotel.getProvider());
            assertEquals(RATE,hotel.getRate());
            assertEquals(AMENITIES_LIST,hotel.getAmenities());
            assertEquals(PRICE,hotel.getFarePerNight());





        }

        private boolean checkParams(Map<String,?> uriVariables) {
            if(     SOME_CITY_AIRPORT.equals(uriVariables.get("city"))&&
                    FROM_DATE_INSTANT.equals(uriVariables.get("from")) &&
                    TO_DATE_INSTANT.equals(uriVariables.get("to")) &&
                    NUMBER_OF_ADULTS.equals(uriVariables.get("adultsCount")))
                return true;
            return false;
        }



}