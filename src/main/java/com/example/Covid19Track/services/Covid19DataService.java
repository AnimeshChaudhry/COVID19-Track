package com.example.Covid19Track.services;

import com.example.Covid19Track.models.LocationStatistics;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;


@Service
public class Covid19DataService {

    private static String COVID_DATA= "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<LocationStatistics> allLocations = new ArrayList<>();

    public List<LocationStatistics> getAllLocations() {
        return allLocations;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchCoronaVirusData() throws IOException, InterruptedException {

        List<LocationStatistics> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(COVID_DATA))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader csvReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);

        for (CSVRecord record : records) {
            LocationStatistics locationStatistic = new LocationStatistics();
            locationStatistic.setState(record.get("Province/State"));
            locationStatistic.setCountry((record.get("Country/Region")));

            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevCases = Integer.parseInt(record.get(record.size() - 2));

            locationStatistic.setLatestCases(latestCases);
            locationStatistic.setCasesDiff(latestCases - prevCases);

            newStats.add(locationStatistic);
        }

        this.allLocations = newStats;


    }
}
