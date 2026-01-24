package project.controller.Bodies;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.Objects;

//{id: int, newStatus:int} -- newStatus may need to change, for now 0 for open, nonZero for resolved
public class ReportStatusBody {
	private Integer id;
	//zero for open, nonZero for resolved
	private Integer status;

	public ReportStatusBody(Integer id, Integer status) {
		this.id = id;
		this.status = status;
	}

	public ReportStatusBody() {}

	public Integer getId() {return id;}
	public void setId(Integer id) {this.id = id;}
	public Integer getStatus() {return status;}
	public void setStatus(Integer status) {this.status = status;}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;

		ReportStatusBody that = (ReportStatusBody) o;
		return Objects.equals(id, that.id) && Objects.equals(status, that.status);
	}

	@Override
	public int hashCode() {
		int result = Objects.hashCode(id);
		result = 31 * result + Objects.hashCode(status);
		return result;
	}

	@Override
	public String toString() {
		return "ReportStatusBody{" +
				"id=" + id +
				", status=" + status +
				'}';
	}
}
