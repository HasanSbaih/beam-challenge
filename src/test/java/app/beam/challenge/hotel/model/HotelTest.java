package app.beam.challenge.hotel.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HotelTest {

    @Test
    public void givenHotelConstructionNeededData_whenInitializingNewHotel_thenItsGoingToWorkAsDataStructure(){
        //Given
        String provider = "provider";
        String name = "name";
        Double farePerNight = 0.0;
        List<String> amenities = Arrays.asList();
        Integer rate = 0;

        //When
        Hotel hotel = new Hotel(provider, name, farePerNight, amenities, rate);

        //Then
        assertEquals(provider,hotel.getProvider());
        assertEquals(name,hotel.getName());
        assertEquals(farePerNight,hotel.getFarePerNight());
        assertEquals(amenities,hotel.getAmenities());
        assertEquals(rate,hotel.getRate());
    }
}