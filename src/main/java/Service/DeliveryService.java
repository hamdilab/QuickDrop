package Service;

import Models.Delivery;
import Repositories.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public List<Delivery> findAll()
    {
        return deliveryRepository.findAll();
    }

}
