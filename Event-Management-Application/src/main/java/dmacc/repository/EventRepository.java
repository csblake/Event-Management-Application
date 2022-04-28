package dmacc.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dmacc.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
	List<Event> findEventByTypeOrderByDateAsc(String type);
	
	List<Event> findEventDistinctByTypeOrderByDateAsc(String type);
	
	List<Event> findEventByDateAfterOrderByDateAsc(Date current);
	
	// Gets unique event types from the database
	@Query("SELECT DISTINCT e.type FROM Event e")
	List<String> findTypes();
}