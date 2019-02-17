package app.beam.challenge.hotel.port.api;

import app.beam.challenge.hotel.port.api.response.Hotel;
import app.beam.challenge.hotel.usecase.GetHotelsUseCase;
import app.beam.challenge.hotel.usecase.GetHotelsUseCaseRequest;
import app.beam.challenge.hotel.usecase.GetHotelsUseCaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AvailableHotelsController implements AvailableHotelsApi{
    private final GetHotelsUseCase getHotelsUseCase;

    public AvailableHotelsController(GetHotelsUseCase getHotelsUseCase) {
        this.getHotelsUseCase = getHotelsUseCase;
    }

    @Override
    public ResponseEntity<List<Hotel>> hotels(LocalDate fromDate, LocalDate toDate, String city, Integer numberOfAdults) {
        GetHotelsUseCaseResponse response = getHotelsUseCase.execute(new GetHotelsUseCaseRequest() {
            @Override
            public LocalDate getFromDate() {
                return fromDate;
            }

            @Override
            public LocalDate getToDate() {
                return toDate;
            }

            @Override
            public String getCityAirport() {
                return city;
            }

            @Override
            public Integer getNumberOfAdults() {
                return numberOfAdults;
            }
        });

        return ResponseEntity.ok(response.getHotels().stream().map(Hotel::new).collect(Collectors.toList()));
    }
}
