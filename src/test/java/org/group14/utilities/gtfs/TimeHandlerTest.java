// package org.group14.utilities.gtfs;

// import java.time.LocalDate;
// import java.time.LocalDateTime;

// import org.junit.jupiter.api.Test;

// public class TimeHandlerTest {

//     @Test
//     public void testGetTime() {
//         LocalDateTime time = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
//         assert TimeHandler.getTime(time).equals("00:00:00");

//         time = LocalDateTime.of(2021, 1, 1, 1, 1, 1);
//         assert TimeHandler.getTime(time).equals("01:01:01");

//         time = LocalDateTime.of(2021, 1, 1, 10, 10, 10);
//         assert TimeHandler.getTime(time).equals("10:10:10");

//         time = LocalDateTime.of(2021, 1, 1, 10, 1, 10);
//         assert TimeHandler.getTime(time).equals("10:01:10");
//     }

//     @Test
//     public void testGetTimeCurrent() {
//         assert TimeHandler.getTime().equals(TimeHandler.getTime(LocalDateTime.now()));
//     }

//     @Test
//     public void testFormatTimeToWalk() {
//         LocalDateTime time = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
//         assert TimeHandler.formatTimeToWalk(time, "00:00:00").equals("00:00:00");

//         time = LocalDateTime.of(2021, 1, 1, 1, 1, 1);
//         assert TimeHandler.formatTimeToWalk(time, "01:01:01").equals("02:02:02");

//         time = LocalDateTime.of(2021, 1, 1, 10, 10, 10);
//         assert TimeHandler.formatTimeToWalk(time, "10:10:10").equals("20:20:20");

//         time = LocalDateTime.of(2021, 1, 1, 10, 1, 10);
//         assert TimeHandler.formatTimeToWalk(time, "10:01:10").equals("20:02:20");
//     }

//     @Test
//     public void testFormatTimeToWalkCurrent() {
//         assert TimeHandler.formatTimeToWalk("00:00:00").equals(TimeHandler.formatTimeToWalk(LocalDateTime.now(), "00:00:00"));
//     // }

//     @Test
//     public void testParseTime() {
//         LocalDateTime time = LocalDateTime.of(LocalDate.now(), TimeHandler.parseTime("00:00:00").toLocalTime());
//         assert TimeHandler.getTime(time).equals("00:00:00");

//         time = LocalDateTime.of(LocalDate.now(), TimeHandler.parseTime("01:01:01").toLocalTime());
//         assert TimeHandler.getTime(time).equals("01:01:01");

//         time = LocalDateTime.of(LocalDate.now(), TimeHandler.parseTime("10:10:10").toLocalTime());
//         assert TimeHandler.getTime(time).equals("10:10:10");

//         // 25:00:00 is the same as 01:00:00
//         time = LocalDateTime.of(LocalDate.now(), TimeHandler.parseTime("25:00:00").toLocalTime());
//         assert TimeHandler.getTime(time).equals("01:00:00");

//         // 24:00:00 is the same as 00:00:00
//         time = LocalDateTime.of(LocalDate.now(), TimeHandler.parseTime("24:00:00").toLocalTime());
       
//     }

    
// }
