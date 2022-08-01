package com.wamisoftware.candidatetest.service;

import java.util.Map;

import com.wamisoftware.candidatetest.exception.NoSuchInputFileException;

public interface ParticipantService {

    Map<String, Long> getResults() throws NoSuchInputFileException;
    Map<String, Long> getTop10Results() throws NoSuchInputFileException;
}
