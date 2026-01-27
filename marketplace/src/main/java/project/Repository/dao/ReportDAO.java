package project.Repository.dao;

<<<<<<< HEAD
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import project.Repository.Entities.ReportEntity;

=======
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import project.Repository.Entities.ReportEntity;

import java.util.List;

>>>>>>> fb293b6a16fdaebce9a67603b34f6b957c358c4e
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
<<<<<<< HEAD

	//get reports from user
	@Query("SELECT r FROM ReportEntity r WHERE r.reporter_id = ?1")
	public List<ReportEntity> getReportsFrom(Integer id);
=======
>>>>>>> fb293b6a16fdaebce9a67603b34f6b957c358c4e
}
