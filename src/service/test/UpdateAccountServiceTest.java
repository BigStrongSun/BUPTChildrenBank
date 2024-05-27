package service.test;

import static org.junit.Assert.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;
import service.UpdateAccountService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The UpdateAccountServiceTest class contains unit tests for the UpdateAccountService class.
 */
public class UpdateAccountServiceTest {
    private static final File jsonFile = new File("account.txt");
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Tests the updateLockTimes method.
     *
     * @throws IOException if an error occurs while reading the JSON file
     */
    @Test
    public void testUpdateLockTimes() throws IOException {
        ArrayNode accounts = (ArrayNode) objectMapper.readTree(jsonFile);
        ObjectNode savingAccount = (ObjectNode) accounts.get(0);

        UpdateAccountService.updateLockTimes(savingAccount);

        LocalDateTime depositTime = LocalDateTime.parse(savingAccount.get("depositTime").asText(), formatter);
        LocalDateTime lockEndTime = LocalDateTime.parse(savingAccount.get("lockEndTime").asText(), formatter);
        assertEquals(depositTime.plusDays(7), lockEndTime);
    }
}
