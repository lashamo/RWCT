package com.example.demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TicketsValidatorJob {

    private WebDriver driver = null;
    private List<Integer> lastAvailableTickets = null;

    @Scheduled(fixedDelay = 3_000)
    public void job() throws Exception {
        String content = readContentFromURL("https://tickets.rugbyworldcup.com/en");
        List<Integer> unavailable = getWordsIndexes(content, "secondary unavailable");
        List<Integer> available = getWordsIndexes(content, "BUY TICKETS VIA RESALE");
        List<Integer> availableTickets = getIndexesOfAvailable(unavailable, available);

        List<Integer> newAvailableTickets = new ArrayList<>();
        if (lastAvailableTickets != null) {
            for (Integer ticket : availableTickets) {
                if (!lastAvailableTickets.contains(ticket)) {
                    newAvailableTickets.add(ticket);
                }
            }
            boolean isGeorgiaTicketAvailable = getIfMatchesAnyIndex(newAvailableTickets, Arrays.asList(3, 19, 28, 34));
            for (int i : newAvailableTickets) {
                if (i < 48) {
                    System.out.println(MatchesInfo.matches.get(i));
                }
            }
            System.out.println(newAvailableTickets);
            System.out.println(isGeorgiaTicketAvailable);

            if (isGeorgiaTicketAvailable) {
                // call mps music
            }
        }
        lastAvailableTickets = availableTickets;

    }

    protected boolean getIfMatchesAnyIndex(List<Integer> first, List<Integer> second) {
        for (Integer f : first) {
            for (Integer s : second) {
                if (f.equals(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected List<Integer> getWordsIndexes(String content, String word) {
        int k = 0;
        List<Integer> list = new ArrayList<>();
        while (content.indexOf(word, k) > -1) {
            k = content.indexOf(word, k);
            list.add(k);
            k = k + 1;
        }
        return list;
    }

    protected List<Integer> getIndexesOfAvailable(List<Integer> unavailable, List<Integer> available) {
        unavailable.add(0, Integer.MIN_VALUE);
        unavailable.add(Integer.MAX_VALUE);
        // -M, 3, 7, 15, 40, 56, 103, M
        // 2, 10, 58, 110
        List<Integer> result = new ArrayList<>();
        for (int j = 0; j < available.size(); j++) {
            for (int i = 0; i < unavailable.size() - 1; i++) {
                if (unavailable.get(i) < available.get(j) && unavailable.get(i + 1) > available.get(j)) {
                    result.add(i + j);
                    break;
                }
            }
        }

        return result;
    }

    private String readContentFromURL(String url) throws Exception {
        if (driver == null) {
            driver = new ChromeDriver();
            driver.get(url);
        } else {
            driver.navigate().refresh();
            Thread.sleep(1_000);
        }

        return driver.getPageSource();
    }
}
