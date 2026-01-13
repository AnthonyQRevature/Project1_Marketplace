package project.Repository.Entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name="block")
@IdClass(BlockEntity.BlockId.class) //composite key
public class BlockEntity {

	/*
	 * The specified primary key type must:
	 * be a non-abstract regular Java class, or a Java record type,
	 * have a public or protected constructor with no parameters, unless it is a record type, and
	 * implement equals and hashCode, defining value equality consistently with equality of the mapped primary key of the database table.
	 */
	public static class BlockId
	{
		public int blocker;
		public int blocked;

		public BlockId(int blocked, int blocker) {
			this.blocked = blocked;
			this.blocker = blocker;
		}

		@Override
		public int hashCode() {
			int hash = 5;
			hash = 53 * hash + this.blocker;
			hash = 53 * hash + this.blocked;
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final BlockId other = (BlockId) obj;
			if (this.blocker != other.blocker) {
				return false;
			}
			return this.blocked == other.blocked;
		}
	}

	@Id
	@Column(name="blocker_id")
	private int blocker;
	@Id
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
