package app.beam.challenge.hotel.gateway;

import app.beam.challenge.hotel.model.Hotel;

import java.time.LocalDate;
import java.util.List;

public interface HotelProviderGateway <Re extends HotelProviderGateway.Request> {

    List<Hotel> getHotels(Re request);

    interface Request {
        LocalDate getFromDate();
        LocalDate getToDate();
        String getCityAirport();
        Integer getNumberOfAdults();
    }
}


