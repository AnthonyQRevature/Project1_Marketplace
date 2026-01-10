package project.Repository.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;


@Entity
@Table(name="block")
public class BlockEntity {
	@Column(name="blocker_id")
	private int blocker;
	@Column(name="blocked_id")
	private int blocked;

	public int getBlocker() {return blocker;}
	public void setBlocker(int blocker) {this.blocker = blocker;}
	public int getBlocked() {return blocked;}
	public void setBlocked(int blocked) {this.blocked = blocked;}


	public BlockEntity() {}

	public BlockEntity(BlockEntity blockEntity) {
		this.blocker = blockEntity.blocker;
		this.blocked = blockEntity.blocked;
	}

	public BlockEntity(int blocker, int blocked) {
		this.blocker = blocker;
		this.blocked = blocked;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		BlockEntity that = (BlockEntity) o;
		return blocker == that.blocker && blocked == that.blocked;
	}

	@Override
	public int hashCode() {
		return Objects.hash(blocker, blocked);
	}

	@Override
	public String toString() {
		return "BlockEntity{" +
				"blocker=" + blocker +
				", blocked=" + blocked +
				'}';
	}
}
