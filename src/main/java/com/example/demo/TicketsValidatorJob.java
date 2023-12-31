package com.example.demo;

import com.example.demo.service.TicketService;
import com.example.demo.service.TicketServiceImpl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TicketsValidatorJob {
    private boolean firstCall = true;
    private final TicketService ticketService;

    private WebDriver driver = null;
    private List<Integer> lastAvailableTickets = null;

    public TicketsValidatorJob(TicketServiceImpl ticketService) {
        this.ticketService = ticketService;
    }

    @Scheduled(fixedDelay = 5_000)
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
            if (isGeorgiaTicketAvailable) {
               playAlarm();
            }
            if (!firstCall){
                ticketService.addGames(newAvailableTickets);
            }else {
                firstCall=false;
            }

        }
        lastAvailableTickets = availableTickets;
    }

    private void playAlarm() {
        try {
            URL res = getClass().getClassLoader().getResource("audio/execute.aiff");
            File file = Paths.get(res.toURI()).toFile();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
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
