package app.beam.challenge.hotel.port.api.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("the hotel Response object")
public class Hotel extends app.beam.challenge.hotel.model.Hotel {

    public Hotel(app.beam.challenge.hotel.model.Hotel hotel) {
        super(hotel.getProvider(), hotel.getName(), hotel.getFarePerNight(), hotel.getAmenities(), hotel.getRate());
    }

    @Override
    @ApiModelProperty("the name of the Provider")
    public String getProvider() {
        return super.getProvider();
    }

    @Override
    @ApiModelProperty("the name of the Hotel")
    public String getName() {
        return super.getName();
    }

    @Override
    @ApiModelProperty("the fate for a night")
    public Double getFarePerNight() {
        return super.getFarePerNight();
    }

    @Override
    @ApiModelProperty("the list of amenities")
    public List<String> getAmenities() {
        return super.getAmenities();
    }

    @Override
    @ApiModelProperty("the rating of the hotel")
    public Integer getRate() {
        return super.getRate();
    }
}