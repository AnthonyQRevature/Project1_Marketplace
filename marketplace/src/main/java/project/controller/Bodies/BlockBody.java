package project.controller.Bodies;

import java.util.Objects;

public class BlockBody {
	private Integer id_blocked;
	private Integer id_blocker;

	public BlockBody() {}
	public BlockBody(Integer id_blocked, Integer id_blocker) {
		this.id_blocked = id_blocked;
		this.id_blocker = id_blocker;
	}

	public Integer getId_blocked() {return id_blocked;}
	public void setId_blocked(Integer id_blocked) {this.id_blocked = id_blocked;}
	public Integer getId_blocker() {return id_blocker;}
	public void setId_blocker(Integer id_blocker) {this.id_blocker = id_blocker;}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;

		BlockBody blockBody = (BlockBody) o;
		return Objects.equals(id_blocked, blockBody.id_blocked) && Objects.equals(id_blocker, blockBody.id_blocker);
	}

	@Override
	public int hashCode() {
		int result = Objects.hashCode(id_blocked);
		result = 31 * result + Objects.hashCode(id_blocker);
		return result;
	}

	@Override
	public String toString() {
		return "BlockBody{" +
				"id_blocked=" + id_blocked +
				", id_blocker=" + id_blocker +
				'}';
	}
}