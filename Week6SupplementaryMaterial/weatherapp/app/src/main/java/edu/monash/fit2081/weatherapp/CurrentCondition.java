package edu.monash.fit2081.weatherapp;

public class CurrentCondition {
    private String  temp;
    private String precip;
    private String humidity;
    private String  wind;


    public CurrentCondition(String  temp, String precip, String humidity, String  wind) {
        this.temp = temp;
        this.precip = precip;
        this.humidity = humidity;
        this.wind = wind;

    }

    public String  getTemp() {
        return temp;
    }

    public String getPrecip() {
        return precip;
    }

    public String getHumidity() {
        return humidity;
    }

    public String  getWind() {
        return wind;
    }

}
