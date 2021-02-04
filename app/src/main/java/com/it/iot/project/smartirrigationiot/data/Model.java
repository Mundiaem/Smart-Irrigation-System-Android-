package com.it.iot.project.smartirrigationiot.data;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Model {
    private String temperature;
    private String humidity;
    private String moisture;
    private String Fahrenheit;
    private String solenoid_valve;
    private String time;

    public Model() {
        super();
    }

    @Override
    public String toString() {
        return "Model{" +
                "temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", moisture='" + moisture + '\'' +
                ", Fahrenheit='" + Fahrenheit + '\'' +
                ", solenoid_valve='" + solenoid_valve + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getMoisture() {
        return moisture;
    }

    public void setMoisture(String moisture) {
        this.moisture = moisture;
    }

    public String getFahrenheit() {
        return Fahrenheit;
    }

    public void setFahrenheit(String fahrenheit) {
        Fahrenheit = fahrenheit;
    }

    public String getSolenoid_valve() {
        return solenoid_valve;
    }

    public void setSolenoid_valve(String solenoid_valve) {
        this.solenoid_valve = solenoid_valve;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("temperature", temperature);
        result.put("humidity", humidity);
        result.put("moisture", moisture);
        result.put("Fahrenheit", Fahrenheit);
        result.put("solenoid_valve", solenoid_valve);
        result.put("time", time);

        return result;
    }
}
