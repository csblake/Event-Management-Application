package dmacc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dmacc.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
	List<Event> findEventByTypeOrderByDateAsc(String type);
	
	List<Event> findEventDistinctByTypeOrderByDateAsc(String type);
}
