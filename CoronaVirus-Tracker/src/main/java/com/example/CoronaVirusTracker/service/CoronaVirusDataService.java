package com.example.CoronaVirusTracker.service;

import com.example.CoronaVirusTracker.models.*;
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
import java.util.*;

import org.apache.commons.csv.CSVRecord;
import org.thymeleaf.util.StringUtils;

@Service
public class CoronaVirusDataService {
    private static  String VIRUS_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private String global_death="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
    private String global_Recover="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";


    private List<DeathStat> Deathstatarr =new ArrayList<>();
    public List<DeathStat> getDeathstatarr() {return Deathstatarr;}

    private List<CountryWise> countryWisesList =new ArrayList<>();
    public List<CountryWise> getCountryWisesList(){return  countryWisesList;}

    private List<DataState>  data=new ArrayList<>();
    public List<DataState> newData(String s){
        GetAccordingtoCountry(s);
        return data;}

        private  List<MergeStat> mergeStatsList =new ArrayList<>();
    public  List<MergeStat> getMergeStatsList(){return mergeStatsList;}


    private List<LocationStats> allstats =new ArrayList<>();
    public List<LocationStats> getAllstats() {
        return allstats;
    }

    private List<RecoveryStat> Recoverystatlist=new ArrayList<>();
    public List<RecoveryStat> getRecoverystat(){return Recoverystatlist;}

    @PostConstruct //hey spring start this when the spring starts
    @Scheduled(cron="* * 1 * * *")  //run daily * means always sec min hrs day month year
    public void fetchDataGlobal() throws IOException, InterruptedException {

        List<LocationStats> newstats =new ArrayList<>();

        HttpClient client=HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();

        HttpResponse<String> httpResponse=client.send(request, HttpResponse.BodyHandlers.ofString());

        //System.out.println(httpResponse.body());

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
        List<DeathStat> deathStatList=new ArrayList<>();

        HttpClient client2=HttpClient.newHttpClient();
        HttpRequest request2=HttpRequest.newBuilder().uri(URI.create(global_death)).build();
        HttpResponse<String> httpResponse2 =client2.send(request2,HttpResponse.BodyHandlers.ofString());

       //System.out.println(httpResponse.body());
        StringReader  cvsReader2= new StringReader(httpResponse2.body());
        Iterable<CSVRecord> records2 = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(cvsReader2);
        for (CSVRecord record : records2) {
            DeathStat  deathStat=new DeathStat();
            deathStat.setState(record.get("Province/State"));
            deathStat.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int PreviousDay = Integer.parseInt(record.get(record.size() - 2));
            deathStat.setLatestDeath(latestCases);
            deathStat.setPreviousDayDeath(latestCases-PreviousDay);
            deathStatList.add(deathStat);
        }
        this.Deathstatarr=deathStatList;
        UniqueCountry();
    }
    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void FetchRecoveryData() throws IOException, InterruptedException {
        List<RecoveryStat> recoveryStats=new ArrayList<>();
        HttpClient client=HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder().uri(URI.create(global_Recover)).build();
        HttpResponse<String> httpResponse =client.send(request,HttpResponse.BodyHandlers.ofString());

        //System.out.println(httpResponse.body());
        StringReader  cvsReader= new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(cvsReader);
        for (CSVRecord record : records) {
            RecoveryStat recoveryStat=new RecoveryStat();
            recoveryStat.setState(record.get("Province/State"));
            recoveryStat.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int PreviousDay = Integer.parseInt(record.get(record.size() - 2));
            recoveryStat.setLatestRecoveryCases(latestCases);
            recoveryStat.setDiffFromPrevDayRecovery(latestCases-PreviousDay);
            recoveryStats.add(recoveryStat);
        }
        this.Recoverystatlist=recoveryStats;
    }


    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void UniqueCountry(){
      TreeMap<String,CountryWise>  hscount =new TreeMap<>();
        int sum1=0,sum2=0,sum3=0;
        Iterator itr3=getRecoverystat().iterator();
        while(itr3.hasNext()) {
            Iterator itr = getAllstats().iterator();
            Iterator itr2 = getDeathstatarr().iterator();
            RecoveryStat recoveryStat = (RecoveryStat) itr3.next();
            String s=recoveryStat.getCountry();
            String state=recoveryStat.getState();
            while (itr.hasNext() && itr2.hasNext()) {
                LocationStats locationStat = (LocationStats) itr.next();
                DeathStat deathStat = (DeathStat) itr2.next();
                String s1=locationStat.getCountry();
                String state2=locationStat.getState();
                if (hscount.containsKey(locationStat.getCountry()) && s.equals(s1) && state.equals(state2)) {
                    sum1 += locationStat.getLatestTotalCases();
                    sum2 += deathStat.getLatestDeath();
                    sum3 += recoveryStat.getLatestRecoveryCases();
                    hscount.put(locationStat.getCountry(), (new CountryWise(null, locationStat.getCountry(), sum1, sum2, sum3)));
                    break;
                } else if(s.equals(s1) && state.equals(state2) ) {
                    hscount.put(locationStat.getCountry(), new CountryWise(null, locationStat.getCountry(), locationStat.getLatestTotalCases(), deathStat.getLatestDeath(), recoveryStat.getLatestRecoveryCases()));
                break;}
                //System.out.println(hscount.get(locationStat.getCountry()));
            }
        }
         List<CountryWise> cc =new ArrayList<>(hscount.values());
        this.countryWisesList=cc;
    }

    public void GetAccordingtoCountry(String s) {
        List<DataState> datastate = new ArrayList<>();

        Iterator itr3=getRecoverystat().iterator();
        while(itr3.hasNext()) {
            Iterator itr = getAllstats().iterator();
            Iterator itr2 = getDeathstatarr().iterator();
            RecoveryStat recoveryStat = (RecoveryStat) itr3.next();
            String ss=recoveryStat.getCountry();
            String state=recoveryStat.getState();
            while (itr.hasNext() && itr2.hasNext()) {
                LocationStats locationStats = (LocationStats) itr.next();
                DeathStat deathStat = (DeathStat) itr2.next();
                String s1=locationStats.getCountry();
                String state2=locationStats.getState();
                if (s.equals(locationStats.getCountry()) && ss.equals(s1) && state.equals(state2)) {
                    DataState ds = new DataState();
                    ds.setState(StringUtils.isEmpty(locationStats.getState()) ? "Unknown" : locationStats.getState());
                    ds.setCases(locationStats.getLatestTotalCases());
                    ds.setDeath(deathStat.getLatestDeath());
                    ds.setPrevcase(locationStats.getDiffFromPrevDay());
                    ds.setPrevdeath(deathStat.getPreviousDayDeath());
                    ds.setRecovery(recoveryStat.getLatestRecoveryCases());
                    ds.setPrevrecover(recoveryStat.getDiffFromPrevDayRecovery());
                    datastate.add(ds);
                    //System.out.println(ds);
                    break;
                }
                if (s.charAt(0) < (locationStats.getCountry().charAt(0)))
                    break;
            }
            this.data = datastate;
        }
    }
}

