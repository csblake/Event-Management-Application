/**
 * @author Benjamin Whisler - bwhisler1
 * CIS175 - Spring 2022
 * Apr 12, 2022
 */
package dmacc.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Event {
	@Id
	@GeneratedValue
	private long id;
}
