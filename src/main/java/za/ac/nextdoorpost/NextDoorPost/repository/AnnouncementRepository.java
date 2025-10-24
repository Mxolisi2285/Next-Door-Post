package za.ac.nextdoorpost.NextDoorPost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.nextdoorpost.NextDoorPost.model.Announcement;
import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByApprovedTrue();
}
