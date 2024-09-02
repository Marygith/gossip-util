package gossip_utils.service;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class StatisticsService {

    //second change
    private final String DIR_PATH = "D:\\dev\\gossip\\";
    private final String STATS_DIR_PATH = DIR_PATH + "stats\\lossRate\\";

    public void gatherStatistics(long messageHash, float lossRate, int neighboursCount, int neighboursAmount, int nodesAmount) {

        List<String> times = new ArrayList<>();
        String fileName = renderFileName(lossRate, neighboursCount, neighboursAmount, messageHash);
        for (int i = 0; i < nodesAmount; i++) {
            File file = new File(DIR_PATH + i + "\\" + fileName);

            try (
                    FileInputStream inputStream = new FileInputStream(file)) {
                String millis = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                times.add(millis);
            } catch (Exception e) {
                log.error("Didn't manage to collect info for node {} for params lossRate: {}, neighboursCount: {}, neighboursAmount: {}, messageHash: {}", i, lossRate, neighboursCount, neighboursAmount, messageHash);

            }
        }
        Collections.sort(times);
        var minTime = Long.parseLong(times.getFirst());
        List<String> x_list = times.stream().map(Long::parseLong).map(l -> l - minTime).map(String::valueOf).toList();
        List<String> y_list = new ArrayList<>();
        for (int i = 1; i <= x_list.size(); i++) {
            y_list.add(String.valueOf(i));
        }

        String x_str = String.join(", ", x_list);
        String y_str = String.join(", ", y_list);

        writeDataToFile(STATS_DIR_PATH + fileName + "_x", x_str);
        writeDataToFile(STATS_DIR_PATH + fileName + "_y", y_str);

    }

    public void gatherStatisticsByFileName(List<String> fileNames, int nodesAmount) {
        System.out.println("Gathering statistics for this files: " + fileNames);
        List<String> times = new ArrayList<>();
        for (String fileName : fileNames) {
            times.clear();

            for (int i = 0; i < nodesAmount; i++) {
                File file = new File(DIR_PATH + i + "\\" + fileName);

                try (
                        FileInputStream inputStream = new FileInputStream(file)) {
                    String millis = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    times.add(millis);
                } catch (Exception e) {
                    log.error("Didn't manage to collect info for file {}", fileName);

                }
            }
            if(times.isEmpty()) continue;
            Collections.sort(times);
            var minTime = Long.parseLong(times.getFirst());
            List<String> x_list = times.stream().map(Long::parseLong).map(l -> l - minTime).map(String::valueOf).toList();
            List<String> y_list = new ArrayList<>();
            for (int i = 1; i <= x_list.size(); i++) {
                y_list.add(String.valueOf(i));
            }
            System.out.println("File " + fileName);

            String x_str = String.join(", ", x_list);
            String y_str = String.join(", ", y_list);
            System.out.println("times: " + x_str);
            System.out.println("nodes: " + y_str);
            writeDataToFile(STATS_DIR_PATH + fileName + "_x", x_str);
            writeDataToFile(STATS_DIR_PATH + fileName + "_y", y_str);

        }
    }

    public static String renderFileName(float lossRate, int neighboursCount, int neighboursAmount, long messageHash) {
        String sep = "_";
        return messageHash + sep + neighboursAmount + sep + neighboursCount + sep + lossRate;
    }

    private void writeDataToFile(String filePath, String data) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // Create parent directories if they don't exist
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
