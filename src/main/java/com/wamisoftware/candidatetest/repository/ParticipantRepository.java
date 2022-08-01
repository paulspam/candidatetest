package com.wamisoftware.candidatetest.repository;

import java.time.LocalDateTime;
import java.util.Map;

import com.wamisoftware.candidatetest.exception.NoSuchInputFileException;

public interface ParticipantRepository {
    Map<String, LocalDateTime> getTimesFromFile(String inputFile, boolean firstOccurrence) throws NoSuchInputFileException;
}
