package app.beam.challenge.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Hotel {
    private final String provider;
    private final String name;
    private final Double farePerNight;
    private final List<String> amenities;
    private final Integer rate;
}
