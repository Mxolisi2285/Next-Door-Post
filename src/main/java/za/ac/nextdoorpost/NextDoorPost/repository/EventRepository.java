package za.ac.nextdoorpost.NextDoorPost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.nextdoorpost.NextDoorPost.model.Event;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByApprovedTrue();  // ✅ Finds approved events
}
