package project.controller.Bodies;

import java.util.Objects;

public class ReportsOfBody {
	private Integer id;

	public ReportsOfBody(Integer id) {
		this.id = id;
	}

	public ReportsOfBody() {}

	public Integer getId() {return id;}
	public void setId(Integer id) {this.id = id;}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;

		ReportsOfBody that = (ReportsOfBody) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "ReportsOfBody{" +
				"id=" + id +
				'}';
	}
}
