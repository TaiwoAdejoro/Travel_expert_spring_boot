package com.project.travelExperts.controller;

import com.project.travelExperts.data.dto.request.UpdateAgentRequest;
import com.project.travelExperts.service.AgentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/agent")
public class AgentController {
    @Autowired
    private AgentService agentService;

    @GetMapping("/")
    public ResponseEntity<?> fetchAgentDetails() {
        return ResponseEntity.ok(agentService.fetchAgentDetails());
    }

    @GetMapping("/all-customers")
    public ResponseEntity<?> fetchAllAgentsCustomers(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(agentService.fetchAgentsCustomers(page,size));
    }

    @PatchMapping("/update/{agentId}")
    public ResponseEntity<?> updateAgent(@PathVariable long agentId, @RequestBody @Valid UpdateAgentRequest updateAgentRequest) {
        return ResponseEntity.ok(agentService.updateAgent(agentId, updateAgentRequest));
    }
}
