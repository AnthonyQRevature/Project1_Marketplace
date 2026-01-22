package project.Repository.Entities;

import java.io.Serializable;

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
	public static class BlockId implements Serializable
	{
		private int blocker_id;
		private int blocked_id;

		public BlockId(int blocked, int blocker) {
			this.blocked_id = blocked;
			this.blocker_id = blocker;
		}

		@Override
		public int hashCode() {
			int hash = 5;
			hash = 53 * hash + this.blocker_id;
			hash = 53 * hash + this.blocked_id;
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
			if (this.blocker_id != other.blocker_id) {
				return false;
			}
			return this.blocked_id == other.blocked_id;
		}
	}

	@Id
	@Column(name="blocker_id")
	private int blocker_id;
	@Id
	@Column(name="blocked_id")
	private int blocked_id;

	public int getBlocker() {return blocker_id;}
	public void setBlocker(int blocker) {this.blocker_id = blocker;}
	public int getBlocked() {return blocked_id;}
	public void setBlocked(int blocked) {this.blocked_id = blocked;}


	public BlockEntity() {}

	public BlockEntity(BlockEntity blockEntity) {
		this.blocker_id = blockEntity.blocker_id;
		this.blocked_id = blockEntity.blocked_id;
	}

	public BlockEntity(int blocker, int blocked) {
		this.blocker_id = blocker;
		this.blocked_id = blocked;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;

		BlockEntity that = (BlockEntity) o;
		return blocker_id == that.blocker_id && blocked_id == that.blocked_id;
	}

	@Override
	public int hashCode() {
		int result = blocker_id;
		result = 31 * result + blocked_id;
		return result;
	}

	@Override
	public String toString() {
		return "BlockEntity{" +
				"blocker_id=" + blocker_id +
				", blocked_id=" + blocked_id +
				'}';
	}
}
