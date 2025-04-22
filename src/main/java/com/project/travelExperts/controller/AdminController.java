package com.project.travelExperts.controller;

import com.project.travelExperts.data.dto.request.CreateAgentManagerRequest;
import com.project.travelExperts.data.dto.request.CreateAgentRequest;
import com.project.travelExperts.data.dto.request.UpdateAgentManagerRequest;
import com.project.travelExperts.data.dto.response.CreateAgentManagerResponse;
import com.project.travelExperts.data.dto.response.CreateAgentResponse;
import com.project.travelExperts.service.AdminService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/")
    public ResponseEntity<?> fetchAdminDetails() {
        return ResponseEntity.ok(adminService.fetchAdminDetails());
    }

    @PostMapping(value = "/createAgentManager",produces = "application/json")
    public ResponseEntity<CreateAgentManagerResponse >createAgentManager(@RequestBody @Valid CreateAgentManagerRequest createAgentManagerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createAgentManager(createAgentManagerRequest));
    }

    @PostMapping(value = "/createAgent",produces = "application/json")
    public ResponseEntity<CreateAgentResponse> createAgent(@RequestBody @Valid CreateAgentRequest createAgentRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createAgent(createAgentRequest));
    }

    @GetMapping("/customers")
    public ResponseEntity<?> fetchCustomers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.fetchCustomers(page, size));
    }

    @GetMapping("/agents")
    public ResponseEntity<?> fetchAgents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.fetchAgents(page, size));
    }

    @GetMapping("/agentManagers")
    public ResponseEntity<?> fetchAgentManagers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.fetchAgentManagers(page, size));
    }

    @GetMapping("/agentManagers-agents")
    public ResponseEntity<?> fetchAgentManagersAgent(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.fetchAgentAssignedToAdminManager(page, size));
    }

    @PatchMapping("/agentManager/{agentManagerId}/deactivate")
    public ResponseEntity<?> deactivateAgentManager(@PathVariable long agentManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.deactivateAgentManager(agentManagerId));
    }

    @PatchMapping("/agentManager/{agentManagerId}/activate")
    public ResponseEntity<?> activateAgentManager(@PathVariable long agentManagerId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.activateAgentManager(agentManagerId));
    }

    @PatchMapping("/agentManager/{agentManagerId}/update")
    public ResponseEntity<?> updateAgentManager(@PathVariable long agentManagerId, @RequestBody @Valid UpdateAgentManagerRequest updateAgentManagerRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAgentManager(agentManagerId, updateAgentManagerRequest));
    }

    @PatchMapping("/agent/deactivate/{agentId}")
    public ResponseEntity<?> deactivateAgent(@PathVariable @Valid @NotNull(message = "agent id is required") long agentId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.deactivateAgent(agentId));
    }


    @PatchMapping("/agent/activate/{agentId}")
    public ResponseEntity<?> activateAgent(@PathVariable @Valid @NotNull(message = "agent id is required") long agentId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.activateAgent(agentId));
    }
}
