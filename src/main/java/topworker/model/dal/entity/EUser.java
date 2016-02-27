package topworker.model.dal.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class EUser {

	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String lastName;

	@OneToMany(mappedBy = "user")
	private List<EWorkPeriod> workPeriods;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<EWorkPeriod> getWorkPeriods() {
		return workPeriods;
	}

	public void setWorkPeriods(List<EWorkPeriod> workPeriods) {
		this.workPeriods = workPeriods;
	}

}
