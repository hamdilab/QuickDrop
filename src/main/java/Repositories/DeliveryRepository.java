package Repositories;

import Models.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeliveryRepository extends JpaRepository {

    List<Delivery> findByUserId(Long userId);

//    @Query("SELECT d.user.id, COUNT(d) FROM Delivery d GROUP BY d.user.id")
//    List<Object[]> countDeliveriesPerUser();
}
