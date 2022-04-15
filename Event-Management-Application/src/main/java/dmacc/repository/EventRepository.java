package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dmacc.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{

}
