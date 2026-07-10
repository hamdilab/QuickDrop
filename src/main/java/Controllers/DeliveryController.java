package Controllers;

import Models.Delivery;
import Models.DeliveryStatus;
import Service.DeliveryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    // === READ ALL ===
    @GetMapping
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        return ResponseEntity.ok(deliveryService.findAll());
    }

    // === READ BY ID ===
    // GET : http://localhost:8081/Delivery/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable Long id) {
        return deliveryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // === CREATE ===
    // POST : http://localhost:8080/Delivery/request
    @PostMapping("/request")
    public ResponseEntity<Delivery> requestDelivery(@RequestBody Delivery delivery) {
        return ResponseEntity.ok(deliveryService.createDelivery(delivery));
    }

    // === UPDATE GLOBALE (Nouveau) ===
    // PUT : http://localhost:8081/Delivery/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Delivery> updateDelivery(@PathVariable Long id, @RequestBody Delivery deliveryDetails) {
        return deliveryService.findById(id)
                .map(existingDelivery -> {
                    // Mappe ici les champs que tu souhaites permettre de modifier globalement
                    // Exemple : existingDelivery.setAddress(deliveryDetails.getAddress());
                    //          existingDelivery.setCustomerName(deliveryDetails.getCustomerName());

                    Delivery updatedDelivery = deliveryService.createDelivery(existingDelivery); // Ou une méthode .update() dédiée si présente
                    return ResponseEntity.ok(updatedDelivery);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // === UPDATE PARTIELLE : ASSIGNATION ===
    // PUT : http://localhost:8080/Delivery/{id}/assign?driver=MedAziz
    @PutMapping("/{id}/assign")
    public ResponseEntity<Delivery> assignDriver(@PathVariable Long id, @RequestParam String driver) {
        return ResponseEntity.ok(deliveryService.assignDriver(id, driver));
    }

    // === UPDATE PARTIELLE : STATUT ===
    // PUT : http://localhost:8080/Delivery/{id}/status?status=DELIVERED
    @PutMapping("/{id}/status")
    public ResponseEntity<Delivery> updateStatus(@PathVariable Long id, @RequestParam DeliveryStatus status) {
        return ResponseEntity.ok(deliveryService.updateStatus(id, status));
    }

    // === DELETE (Nouveau) ===
    // DELETE : http://localhost:8081/Delivery/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        return deliveryService.findById(id)
                .map(delivery -> {
                    deliveryService.deleteById(id); // Assure-toi que cette méthode existe dans ton DeliveryService
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // === TEST OPENFEIGN ===
    @GetMapping("/test-feign/{id}")
    public Object testFeign(@PathVariable Long id) {
        Models.Delivery mockDelivery = new Models.Delivery();
        mockDelivery.setId(id);

        return deliveryService.createDelivery(mockDelivery);
    }
}