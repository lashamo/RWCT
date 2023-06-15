package com.example.demo;

import com.example.demo.service.TicketServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicketsValidatorJobTest {

    @Test
    public void testGetIfMatchesAnyIndex_True() {
        List<Integer> first = new ArrayList<>(Arrays.asList(3, 18, 9));
        List<Integer> second = new ArrayList<>(Arrays.asList(46, 18));

        TicketsValidatorJob job = new TicketsValidatorJob(null);
        Assertions.assertTrue(job.getIfMatchesAnyIndex(first, second));
    }

    @Test
    public void testGetIfMatchesAnyIndex_False() {
        List<Integer> first = new ArrayList<>(Arrays.asList(3, 18, 9));
        List<Integer> second = new ArrayList<>(Arrays.asList(46, 24));

        TicketsValidatorJob job = new TicketsValidatorJob(null);
        Assertions.assertFalse(job.getIfMatchesAnyIndex(first, second));
    }

    @Test
    public void testGetIndexesOfAvailableWithBorderValues() {
        List<Integer> unavailable = new ArrayList<>(Arrays.asList(3, 7, 15, 40, 56, 103));
        List<Integer> available = new ArrayList<>(Arrays.asList(2, 10, 58, 110));
        // 2 3 7 10 15 40 56 58 103 110

        List<Integer> expected = Arrays.asList(0, 3, 7, 9);
        TicketsValidatorJob job = new TicketsValidatorJob(null);

        List<Integer> result = job.getIndexesOfAvailable(unavailable, available);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testGetIndexesOfAvailableWithoutBorderValues() {
        List<Integer> unavailable = new ArrayList<>(Arrays.asList(3, 7, 15, 40, 56, 103));
        List<Integer> available = new ArrayList<>(Arrays.asList(10, 58));
        // 3 7 10 15 40 56 58 103

        List<Integer> expected = Arrays.asList(2, 6);
        TicketsValidatorJob job = new TicketsValidatorJob(null);

        List<Integer> result = job.getIndexesOfAvailable(unavailable, available);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testGetIndexesOfAvailableFromProd() {
        List<Integer> unavailable = new ArrayList<>(Arrays.asList(54373, 64688, 68005, 81839, 85421, 88978, 92321, 95628, 99208, 102542, 105815, 120290, 123617, 126942, 130480, 133788, 140948, 144522, 151475, 154812, 165424, 168976, 172598, 175922, 179232, 182796, 186119, 189483, 193091, 196436, 200040, 203383, 206969, 210559, 217746));
        List<Integer> available = new ArrayList<>(Arrays.asList(58004, 61332, 71599, 74956, 78262, 109397, 112988, 116608, 137404, 148137, 158519, 161861, 214188));
        // 3 7 10 15 40 56 58 103

        List<Integer> expected = Arrays.asList(1, 2, 5, 6, 7, 16, 17, 18, 24, 27, 30, 31, 46);
        TicketsValidatorJob job = new TicketsValidatorJob(null);

        List<Integer> result = job.getIndexesOfAvailable(unavailable, available);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testGetWordsIndexes() {
        String content = "rame rume rume rame heyy rame heyyy rame";
        String word = "rame";

        List<Integer> expected = Arrays.asList(0, 15, 25, 36);
        TicketsValidatorJob job = new TicketsValidatorJob(null);

        List<Integer> result = job.getWordsIndexes(content, word);
        Assertions.assertEquals(expected, result);
    }
}
