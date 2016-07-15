package lv.ctco;

import lv.ctco.entities.Offer;
import lv.ctco.entities.Request;
import lv.ctco.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestOfferMatcher {
    @Autowired
    OfferRepository offerRepository;

    public List<Offer> findOffersByRequest (Request request){
        List<Offer> offers = offerRepository.findAll();
        List<Offer> offersNew = new ArrayList<>();
        List<Offer> offerReturn = new ArrayList<>();
        for(Offer offer: offers){    //filter offers which have at least one place
            if(offer.getPassengersAmount()>offer.getRequests().size()){
                offersNew.add(offer);
            }
        }
        for(Offer offer: offersNew){  //filter offers which is less than radius
            if(request.getRadius()>= Math.sqrt(
                    Math.pow((request.getCoordinateFrom().getLatitude()-offer.getCoordinateFrom().getLatitude()),2) +
                            Math.pow((request.getCoordinateFrom().getLongitude()-offer.getCoordinateFrom().getLongitude()),2))
                    && request.getRadius()>= Math.sqrt(
                    Math.pow((request.getCoordinateTo().getLatitude()-offer.getCoordinateTo().getLatitude()),2) +
                            Math.pow((request.getCoordinateTo().getLongitude()-offer.getCoordinateTo().getLongitude()),2))){
                offerReturn.add(offer);
            }
        }
        return offerReturn;
    }
}
