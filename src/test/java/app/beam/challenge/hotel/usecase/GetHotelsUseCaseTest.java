package app.beam.challenge.hotel.usecase;

import app.beam.challenge.hotel.gateway.HotelProviderGateway;
import app.beam.challenge.hotel.model.Hotel;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class GetHotelsUseCaseTest {

    private GetHotelsUseCaseRequest emptyRequest = new GetHotelsUseCaseRequest() {
        @Override
        public LocalDate getFromDate() {
            return null;
        }

        @Override
        public LocalDate getToDate() {
            return null;
        }

        @Override
        public String getCityAirport() {
            return null;
        }

        @Override
        public Integer getNumberOfAdults() {
            return null;
        }
    };


    @Test
    public void givenGetHotelsUseCaseWithEmptyProvidersList_whenCallingExecuteWithRequest_thenUseCaseResponseWithEmptyHotelList(){
        //GIVEN
        GetHotelsUseCase getHotelsUseCaseWithEmptyProvidersList = new GetHotelsUseCase(Arrays.asList());

        //WHEN

        GetHotelsUseCaseResponse execute = getHotelsUseCaseWithEmptyProvidersList.execute(emptyRequest);

        //THEN
        assertEquals(Arrays.asList(),execute.getHotels());

    }



    @Test
    public void givenGetHotelsUseCaseWithListOfSimpleHotelListProvider_whenCallingExecuteWithRequest_thenUseCaseResponseSimpleHotelList(){
        //GIVEN
        List<Hotel> simpleHotelList = Arrays.asList(new Hotel("provider","name",1.0,Arrays.asList(),0));

        HotelProviderGateway hotelProviderGateway = request -> {
            if(request.equals(emptyRequest))
                return simpleHotelList;
            return Arrays.asList();
        };
        GetHotelsUseCase getHotelsUseCaseWithEmptyProvidersList = new GetHotelsUseCase(Arrays.asList(hotelProviderGateway));

        //WHEN

        GetHotelsUseCaseResponse execute = getHotelsUseCaseWithEmptyProvidersList.execute(emptyRequest);

        //THEN
        assertEquals(simpleHotelList,execute.getHotels());

    }


    @Test
    public void givenGetHotelsUseCaseWithListOfSimpleHotelListWithRateProviders_whenCallingExecuteWithRequest_thenUseCaseResponseSimpleHotelListOrderedByRate(){
        //GIVEN
        List<Hotel> simpleRatedHotelList = Arrays.asList(new Hotel("provider","name",1.0,Arrays.asList(),2));
        List<Hotel> simpleRatedHotelList2 = Arrays.asList(new Hotel("provider","name",1.0,Arrays.asList(),1));

        HotelProviderGateway simpleRatedHotelListProviderGateway = request -> {
            if(request.equals(emptyRequest))
                return simpleRatedHotelList;
            return Arrays.asList();
        };

        HotelProviderGateway simpleRatedHotelList2ProviderGateway = request -> {
            if(request.equals(emptyRequest))
                return simpleRatedHotelList2;
            return Arrays.asList();
        };


        GetHotelsUseCase getHotelsUseCaseWithEmptyProvidersList = new GetHotelsUseCase(Arrays.asList(simpleRatedHotelListProviderGateway,simpleRatedHotelList2ProviderGateway));

        //WHEN

        GetHotelsUseCaseResponse execute = getHotelsUseCaseWithEmptyProvidersList.execute(emptyRequest);

        //THEN
        assertEquals(Stream.concat(simpleRatedHotelList.stream(),simpleRatedHotelList2.stream()).collect(Collectors.toList()), execute.getHotels());

    }

}