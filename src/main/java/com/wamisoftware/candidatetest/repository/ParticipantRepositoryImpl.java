package com.wamisoftware.candidatetest.repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.wamisoftware.candidatetest.exception.NoSuchInputFileException;
import org.springframework.stereotype.Repository;

@Repository
public class ParticipantRepositoryImpl implements ParticipantRepository {

    private static final String DATE_FORMAT = "yyMMddHHmmss";

    /**
     * Method reads string lines from file, then parses it to participant ID and
     * his time. If file does not exit it throws {@link NoSuchInputFileException}
     * with appropriate message.
     * In case of duplicating ID, conflicts are resolved depending on the
     * value of the firstOccurrence parameter
     *
     * @param inputFile file name from which data reads.
     * @param firstOccurrence set type of resolving duplicate IDs conflicts.
     *                        true - using first occurrence
     *                        false - using last occurrence
     * @return Map with IDs and times
     * @throws NoSuchInputFileException when data source file does not exist
     */
    @Override
    public Map<String, LocalDateTime> getTimesFromFile(String inputFile, boolean firstOccurrence)
        throws NoSuchInputFileException {

        Map<String, LocalDateTime> timesMap;
        try (Stream<String> startStream =
                 Files.lines(Path.of(ClassLoader.getSystemResource(inputFile).toURI()))) {
            timesMap = startStream
                .collect(Collectors.toMap(line -> line.substring(4, 16),
                    line -> LocalDateTime.parse(line.substring(20, 32), DateTimeFormatter.ofPattern(DATE_FORMAT)),
                    (prev, next) -> firstOccurrence ? prev : next,
                    HashMap::new));
        } catch (Exception e) {
            throw new NoSuchInputFileException("Input file " + inputFile + " not found");
        }
        return timesMap;
    }

}
