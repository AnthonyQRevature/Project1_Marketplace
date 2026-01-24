package project.Repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.Repository.Entities.BlockEntity;

import java.util.List;

public interface BlocksDAO extends JpaRepository<BlockEntity, Integer> {
	@Query("SELECT b FROM BlockEntity b WHERE b.blocker_id = ?1")
	List<BlockEntity> findBlocksByUser(Integer blocker_id);
}
