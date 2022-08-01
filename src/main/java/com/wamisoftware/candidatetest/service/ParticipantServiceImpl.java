package com.wamisoftware.candidatetest.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.wamisoftware.candidatetest.exception.NoSuchInputFileException;
import com.wamisoftware.candidatetest.repository.ParticipantRepositoryImpl;
import com.wamisoftware.candidatetest.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    @Value("${startFile.path:tag_read_start.log}")
    private String startFile;

    @Value("${finishFile.path:tag_reads_finish.log}")
    private String finishFile;

    @Value("${startFile.timezone:UTC}")
    private String startFileTimeZone;

    @Value("${finishFile.timezone:Europe/Kiev}")
    private String finishFileTimeZone;

    private ParticipantRepositoryImpl participantRepository;

    public ParticipantServiceImpl(ParticipantRepositoryImpl participantRepository) {
        this.participantRepository = participantRepository;
    }

    /**
     * Method merges result times from start and finish times.
     * When repeating tags, for start times method uses the first occurrence,
     * for finish times - last occurrence.
     * Time zone difference is also taken into calculating.
     * Result is calculated as the difference between finish time and start time.
     * If there aren't matching finish results, finish time sets as start time.
     * Thus, incorrect data is filtered out by the condition > 0
     * Names of data source files are taken from private variables
     *{@link #startFile startFile} and {@link #finishFile finishFile}
     * Time zones for result times are taken from private variables
     * {@link #startFileTimeZone startFileTimeZone} and
     * {@link #finishFileTimeZone finishFileTimeZone}
     * Values for these private variables can be defined in
     * {@code application.properties} file
     *
     * @return Map with participant ID and competition result
     * @throws NoSuchInputFileException when any data source file does not exist
     */
    @Override
    public Map<String, Long> getResults() throws NoSuchInputFileException {

        Map<String, LocalDateTime> startTimes = participantRepository.getTimesFromFile(startFile, true);
        Map<String, LocalDateTime> finishTimes = participantRepository.getTimesFromFile(finishFile, false);

        return startTimes.entrySet().stream()
            .collect(Collectors
                .toMap(Map.Entry::getKey,
                    e -> Duration.between(
                        e.getValue(),
                        finishTimes.getOrDefault(e.getKey(), e.getValue())).getSeconds()
                        + DateTimeUtils.diffBetweenTimeZonesInSeconds(startFileTimeZone, finishFileTimeZone)))
            .entrySet().stream()
            .filter(e -> e.getValue() > 0)
            .sorted(Map.Entry.comparingByValue())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }

    /**
     * Method takes result times using {@link #getResults()} method
     * and returns only Top 10 results
     *
     * @return Map with Top 10 results with participant ID and competition result
     * @throws NoSuchInputFileException when any data source file does not exist
     */
    @Override
    public Map<String, Long> getTop10Results() throws NoSuchInputFileException {
        return getResults().entrySet().stream()
            .limit(10)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }
}
