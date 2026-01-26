package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;
import project.Repository.Entities.ReportEntity;
import project.controller.Bodies.ReportPostBody;
import project.controller.Bodies.ReportStatusBody;
import project.controller.Bodies.ReportsOfBody;
import project.service.ReportService;
import project.util.AllowCORS;
import project.util.SecureIndescriminate;
import project.util.SecurityLevel;

import java.util.List;

import project.util.Secure;
import project.util.TokenUtil;
import project.util.exception.InvalidRequestException;

@RestController
@AllowCORS
public class ReportController {
	private ReportService reportService;
	private TokenUtil tokenUtil;

	@Autowired
	public ReportController(ReportService reportService, TokenUtil tokenUtil) {
		this.reportService = reportService;
		this.tokenUtil = tokenUtil;
	}

	//figure out user type (admin/normal)

	//get all reports
	@GetMapping("/reports")
	@SecureIndescriminate(SecurityLevel.ADMIN)
	public ResponseEntity<List<ReportEntity>> getAllReports(
			@RequestHeader("Authorization") String authHeader
	)
	{
		return ResponseEntity.ok(reportService.getAllReports());
	}

	//change report status
		//changes status of a report with given id, to new status
	@PatchMapping("/reports")
	@SecureIndescriminate(SecurityLevel.ADMIN)
	public ResponseEntity ChangeStatus
	(
			@RequestHeader("Authorization") String authHeader,
			@RequestBody ReportStatusBody body, Errors error)
	{
		//call dao to change status
		if(! reportService.changeReportStatus(body.getId(), body.getStatus()))
		{
			//TODO ERROR HANDLING: if reportService is false do thingss here.
		}

		System.out.println(error.getAllErrors());

		return ResponseEntity.ok().build();
	}

	//delete report by id
	@DeleteMapping("/reports/{id}")
	@SecureIndescriminate(SecurityLevel.ADMIN)
	public ResponseEntity<?> DeleteReport(
			@RequestHeader("Authorization") String authHeader,
			@PathVariable Integer id
	)
	{
		if(! reportService.Delete(id))
		{
			//TODO: ERROR HANDLING: if report status is false, do things here
		}

		return ResponseEntity.ok().build();
	}

	//get all reports of user id
	@GetMapping("/users/{id}/reports")
	@SecureIndescriminate(SecurityLevel.ADMIN)
	public ResponseEntity<List<ReportEntity>> ReportsOf
		(
				@RequestHeader("Authorization") String authHeader,
				@PathVariable Integer id
		)
	{
		return ResponseEntity.ok(reportService.getReportsOf(id));
	}

	//public void getAllReportsFromUser()
	//{}

	//create report from user ID
	//since theres so much redundant information we have to verify it
	@PostMapping("/users/{id}/reports")
	@SecureIndescriminate(SecurityLevel.USER)
	@Transactional
	public ResponseEntity<ReportEntity> createNewReport(
			@RequestHeader("Authorization") String authHeader,
			@PathVariable int id,
			@RequestBody ReportPostBody body
	) {
		try
		{
			var token = tokenUtil.asToken(authHeader);

			if (body.getReporter_id() != token.getId()) throw new InvalidRequestException();
			if (body.getReported_id() != id) throw new InvalidRequestException();
			if (body.getReported_id() == body.getReporter_id()) throw new InvalidRequestException();
			return ResponseEntity.ok(reportService.createReport(body));
		}
		catch (InvalidRequestException e)
		{
			return ResponseEntity.status(400).build();
		}
	}
}
