package Service;

import Models.Delivery;
import Models.DeliveryStatus;
import Repositories.DeliveryRepository;
import com.example.quickdrop.Clients.CommandeFeignClient; // 👈 Ajout de l'import de ton client Feign
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final CommandeFeignClient commandeFeignClient; // 👈 1. Déclaration du client Feign

    // 2. Mise à jour du constructeur pour injecter les deux dépendances
    public DeliveryService(DeliveryRepository deliveryRepository, CommandeFeignClient commandeFeignClient) {
        this.deliveryRepository = deliveryRepository;
        this.commandeFeignClient = commandeFeignClient;
    }

    public List<Delivery> findAll() {
        return deliveryRepository.findAll();
    }

    public Delivery createDelivery(Delivery delivery) {
        // 🔄 3. Communication synchrone via OpenFeign avec le MS Commande
        if (delivery.getId() != null) {
            try {
                Object commandeAssociee = commandeFeignClient.getCommandeById(delivery.getId());
                System.out.println("👉 Succès OpenFeign ! Données reçues du MS Commande : " + commandeAssociee);
            } catch (Exception e) {
                System.err.println("❌ Échec Feign : Impossible de joindre le service commande. " + e.getMessage());
            }
        }

        delivery.setStatus(DeliveryStatus.PENDING); // Commencer en attente
        delivery.setDeliveryDate(LocalDateTime.now());
        return deliveryRepository.save(delivery);
    }

    public Delivery assignDriver(Long deliveryId, String deliveryPersonName) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Livraison introuvable avec l'ID : " + deliveryId));

        if (delivery.getStatus() != DeliveryStatus.PENDING) {
            throw new IllegalStateException("Cette livraison a déjà été acceptée ou annulée.");
        }

        delivery.setDeliveryPerson(deliveryPersonName);
        delivery.setStatus(DeliveryStatus.ASSIGNED);
        return deliveryRepository.save(delivery);
    }

    public Delivery updateStatus(Long deliveryId, DeliveryStatus newStatus) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Livraison introuvable"));

        delivery.setStatus(newStatus);
        if (newStatus == DeliveryStatus.DELIVERED) {
            System.out.println("Course terminée pour " + delivery.getDeliveryPerson() + ". Calcul des gains en cours...");
        }

        return deliveryRepository.save(delivery);
    }

    public Optional<Delivery> findById(Long id) {
        return deliveryRepository.findById(id);
    }
}