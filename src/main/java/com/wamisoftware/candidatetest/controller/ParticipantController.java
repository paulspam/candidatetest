package com.wamisoftware.candidatetest.controller;

import com.wamisoftware.candidatetest.exception.NoSuchInputFileException;
import com.wamisoftware.candidatetest.service.ParticipantServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping()
public class ParticipantController {

    private final ParticipantServiceImpl participantService;

    ParticipantController(ParticipantServiceImpl participantService) {
        this.participantService = participantService;
    }

    @GetMapping("/results")
    public ResponseEntity<Map<String, Long>> getResults() throws NoSuchInputFileException {
        return ResponseEntity.ok(participantService.getResults());
    }

    @GetMapping("/top10results")
    public ResponseEntity<Map<String, Long>> getTop10Results() throws NoSuchInputFileException {
        return ResponseEntity.ok(participantService.getTop10Results());
    }

}
