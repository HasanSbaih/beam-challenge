package app.beam.challenge.hotel.port.api;

import app.beam.challenge.hotel.port.api.response.Hotel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.util.List;

@Api(value = "AvailableHotels", description = "the Available Hotels API")
public interface AvailableHotelsApi {

    @GetMapping("/availableHotels")
    ResponseEntity<List<Hotel>> hotels(@ApiParam(value = "from what date you want to start the search , ISO_LOCAL_DATE", required = true) @FutureOrPresent LocalDate fromDate,
                                       @ApiParam(value = "till what date you want to start the search , ISO_LOCAL_DATE", required = true) @FutureOrPresent LocalDate toDate,
                                       @ApiParam(value = "name of the city , IATA code (AUH)", required = true) String city,
                                       @ApiParam(value = "number of adults ", required = true) Integer numberOfAdults);
}