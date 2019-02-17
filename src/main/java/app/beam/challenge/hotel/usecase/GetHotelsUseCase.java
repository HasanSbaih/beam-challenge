package app.beam.challenge.hotel.usecase;

import app.beam.challenge.development.FunctionalUsecase;
import app.beam.challenge.hotel.gateway.HotelProviderGateway;
import app.beam.challenge.hotel.model.Hotel;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetHotelsUseCase  implements FunctionalUsecase<GetHotelsUseCaseRequest,GetHotelsUseCaseResponse> {

    private final List<HotelProviderGateway> providers;

    public GetHotelsUseCase(List<HotelProviderGateway> providers) {
        this.providers = providers;
    }

    @Override
    public GetHotelsUseCaseResponse execute(GetHotelsUseCaseRequest request) {

        List<Hotel> sortedHotels = providers
                .stream()
                .map((Function<HotelProviderGateway, List<Hotel>>) p -> p.getHotels(request))
                .flatMap(List::stream)
                .sorted((f1, f2) -> Integer.compare(f2.getRate(),f1.getRate()))
                .collect(Collectors.toList());

        return () -> sortedHotels;
    }
}
