package com.example.CoronaVirusTracker.service;

import com.example.CoronaVirusTracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
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

@Service
public class CoronaVirusDataService {


    private static  String VIRUS_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private String global_death="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";



    private List<LocationStats> allstats =new ArrayList<>();

    public List<LocationStats> getAllstats() {
        return allstats;
    }

    private List<LocationStats> countrywise =new ArrayList<>();

    public List<LocationStats> getCountrywise() {
       return countrywise;
    }

    @PostConstruct //hey spring start this when the spring starts
    @Scheduled(cron="* * 1 * * *")  //run daily * means always sec min hrs day month year
    public void fetchData() throws IOException, InterruptedException {

        List<LocationStats> newstats =new ArrayList<>();

        HttpClient client=HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();

        HttpResponse<String> httpResponse=client.send(request, HttpResponse.BodyHandlers.ofString());

         System.out.println(httpResponse.body());

        StringReader  cvsReader= new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(cvsReader);


        for (CSVRecord record : records) {
            LocationStats locationStats= new LocationStats();
            if(record.get("Province/State")!=null && record.get("Country/Region") !=null) {
                locationStats.setState(record.get("Province/State"));
                locationStats.setCountry(record.get("Country/Region"));
                int latestCases = Integer.parseInt(record.get(record.size() - 1));
                int PreviousDay = Integer.parseInt(record.get(record.size() - 2));
                locationStats.setLatestTotalCases(latestCases);
                locationStats.setDiffFromPrevDay(latestCases - PreviousDay);
                newstats.add(locationStats);
            }
        }
        this.allstats=newstats;
    }




}
