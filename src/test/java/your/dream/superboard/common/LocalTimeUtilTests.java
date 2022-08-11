package your.dream.superboard.common;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalTimeUtilTests {
    Instant time = Instant.ofEpochMilli(1660080600000L);
    @Test
    void ZonedTimeTest() {
        ZonedDateTime zonedDateTime = ZonedDateTime
                .of(2022,8,10,6,30,0,0,
                        ZoneOffset.systemDefault());

        assertEquals(zonedDateTime,LocalTimeUtil.toDateTime(time));
        assertEquals("2022-08-10T06:30:00",LocalTimeUtil.toDateTimeString(time));
        assertEquals("2022-08-10",LocalTimeUtil.toDateString(time));
    }

    @Test
    void LocalDateTimeStringTest() {
        assertEquals("2022-08-10T06:30:00",LocalTimeUtil.toDateTimeString(time));
    }

    @Test
    void LocalDateStringTest() {
        assertEquals("2022-08-10",LocalTimeUtil.toDateString(time));
    }
}
