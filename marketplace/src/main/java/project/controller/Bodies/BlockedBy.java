package project.controller.Bodies;

import java.util.Objects;

public class BlockedBy {
	private Integer id_blocker;

	public BlockedBy() {}

	public BlockedBy(Integer id_blocker) {
		this.id_blocker = id_blocker;
	}

	public Integer getId_blocker() {return id_blocker;}
	public void setId_blocker(Integer id_blocker) {this.id_blocker = id_blocker;}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;

		BlockedBy blockedBy = (BlockedBy) o;
		return Objects.equals(id_blocker, blockedBy.id_blocker);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id_blocker);
	}

	@Override
	public String toString() {
		return "BlockedBy{" +
				"id_blocker=" + id_blocker +
				'}';
	}
}
