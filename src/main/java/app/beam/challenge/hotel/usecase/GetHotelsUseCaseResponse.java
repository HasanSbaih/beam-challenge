package app.beam.challenge.hotel.usecase;

import app.beam.challenge.development.Response;
import app.beam.challenge.hotel.model.Hotel;

import java.util.List;

public interface GetHotelsUseCaseResponse extends Response {

    List<Hotel> getHotels();

}
