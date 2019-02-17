package app.beam.challenge.hotel.port;

import app.beam.challenge.hotel.gateway.HotelProviderGateway;
import app.beam.challenge.hotel.port.gateway.BestHotelProvider;
import app.beam.challenge.hotel.port.gateway.CrazyHotelProvider;
import app.beam.challenge.hotel.usecase.GetHotelsUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Configuration
public class Context {

    @Value("${hotel.provider.crazy}")
    private String crazyHotelApiUrl;


    @Value("${hotel.provider.best}")
    private String bestHotelApiUrl;


    @Bean
    public GetHotelsUseCase getHotelsUseCase(List<HotelProviderGateway> providers){
        return new GetHotelsUseCase(providers);
    }

    @Bean
    public List<HotelProviderGateway> providers(RestTemplate restTemplate){
        return Arrays.asList(new CrazyHotelProvider(restTemplate, crazyHotelApiUrl),new BestHotelProvider(restTemplate, bestHotelApiUrl));
    }


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
