package project.Repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project.Repository.Entities.BlockEntity;

public interface BlocksDAO extends JpaRepository<BlockEntity, Integer> {

}
