package org.group14.utilities.gtfs;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DateHandlerTest {

    @Test
    public void testGetDate() {
        LocalDate date = LocalDate.of(2021, 1, 1);
        assert DateHandler.getDate(date).equals("20210101");

        date = LocalDate.of(2021, 12, 31);
        assert DateHandler.getDate(date).equals("20211231");

        date = LocalDate.of(2021, 10, 10);
        assert DateHandler.getDate(date).equals("20211010");

        date = LocalDate.of(2021, 1, 10);
        assert DateHandler.getDate(date).equals("20210110");

    }
    
}
