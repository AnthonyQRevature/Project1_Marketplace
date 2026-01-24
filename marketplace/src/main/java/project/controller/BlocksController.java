package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import project.controller.Bodies.BlockBody;
import project.controller.Bodies.BlockedBy;
import project.service.BlocksService;
import project.util.AllowCORS;
import project.util.SecureIndescriminate;
import project.util.SecurityLevel;

@RestController
@AllowCORS
public class BlocksController {
	private BlocksService blocksService;

	@Autowired
	public BlocksController(BlocksService blocksService) {
		this.blocksService = blocksService;
	}

	@GetMapping("/users/{id}/blocks")
	@SecureIndescriminate(SecurityLevel.ADMIN)
	public ResponseEntity getAllBlocksBy(
			@RequestHeader("Authorization") String authHeader,
			@RequestBody BlockedBy blockedBy)
	{
		return ResponseEntity.ok(blocksService.getAllBy(blockedBy.getId_blocker()));
	}

	@PostMapping("/users/{id}/blocks")
	@SecureIndescriminate(SecurityLevel.ADMIN)
	public ResponseEntity insertBlock(	@RequestHeader("Authorization") String authHeader,
								@RequestBody BlockBody body)
	{
		blocksService.insertBlock(body.getId_blocker(), body.getId_blocked());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/users/{id}/blocks")
	@SecureIndescriminate(SecurityLevel.USER)
	public ResponseEntity unBlock(@RequestHeader("Authorization") String authHeader,
								  @RequestBody BlockBody body, Errors error)
	{
		blocksService.delete(body.getId_blocker(), body.getId_blocked());
		return ResponseEntity.ok().build();
	}
}
