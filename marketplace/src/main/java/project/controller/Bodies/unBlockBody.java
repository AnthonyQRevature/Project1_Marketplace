package project.controller.Bodies;

import java.util.Objects;

public class unBlockBody {
	private Integer id_blocked;

	//constructors
	public unBlockBody() {}
	public unBlockBody(Integer id_blocked) {
		this.id_blocked = id_blocked;
	}

	//get and set
	public Integer getId_blocked() {return id_blocked;}
	public void setId_blocked(Integer id_blocked) {this.id_blocked = id_blocked;}


	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;

		unBlockBody that = (unBlockBody) o;
		return Objects.equals(id_blocked, that.id_blocked);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id_blocked);
	}

	@Override
	public String toString() {
		return "unBlockBody{" +
				"id_blocked=" + id_blocked +
				'}';
	}
}
