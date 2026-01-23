package project.Repository.dao;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import project.Repository.Entities.ReportEntity;

import java.util.List;

public interface ReportDAO extends JpaRepository<ReportEntity, Integer>{
	//@Query("SELECT r FROM ReportEntity r WHERE r.id = ?1")
	//ReportEntity findReportById(Integer id);

//	@Query("SELECT u FROM UserEntity u WHERE u.username = ?1")
//	ReportEntity findReportByUsername(String username);

	//change status of the update
	@Transactional
	@Modifying
	@Query("UPDATE ReportEntity r set r.status = ?2 WHERE r.id = ?1")
	public void ChangeStatus(Integer id, ReportEntity.ReportStatus newStatus);

	//get reports of user
	@Query("SELECT r FROM ReportEntity r WHERE r.reported_id = ?1")
	public List<ReportEntity> getReportsOf(Integer id);
}
