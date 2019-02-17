package app.beam.challenge.development;

public interface FunctionalUsecase <Rq extends  Request,Re extends Response>{

    Re execute(Rq request);

}
