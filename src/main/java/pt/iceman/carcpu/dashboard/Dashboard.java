package pt.iceman.carcpu.dashboard;

import eu.hansolo.enzo.gauge.Gauge;
import eu.hansolo.enzo.lcd.Lcd;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * Created by iceman on 18/07/16.
 */
public abstract class Dashboard {
    protected Gauge speedGauge;
    protected AbsolutePositioning speedGaugeAbsPos;
    protected double speed;

    protected Gauge tempGauge;
    protected AbsolutePositioning tempGaugeAbsPos;
    protected double temp;

    protected File fileTemperature = new File("dashBoardSymbols/temperature.jpg");
    protected Image tempImage;
    protected ImageView tempImageView;
    protected AbsolutePositioning tempImageAbsPos;

    protected Gauge rpmGauge;
    protected AbsolutePositioning rpmGaugeAbsPos;
    protected double rpm;

    protected Gauge dieselGauge;
    protected AbsolutePositioning dieselGaugeAbsPos;
    protected double diesel;

    protected File fileFuel = new File("dashBoardSymbols/fuel.jpg");
    protected Image dieselImage;
    protected ImageView dieselImageView;
    protected AbsolutePositioning dieselImageAbsPos;

    protected Lcd distanceLcd;
    protected AbsolutePositioning distanceLcdAbsPos;
    protected double distance;

    protected Lcd totalDistanceLcd;
    protected AbsolutePositioning totalDistanceLcdAbsPos;
    protected double totalDistance;

    protected AnimationTimer timerSpeed;
    protected AnimationTimer timerRpm;
    protected AnimationTimer timerDistance;
    protected AnimationTimer timerTotalDistance;
    protected AnimationTimer timerDiesel;
    protected AnimationTimer timerTemp;

    protected File fileBrakesOil = new File("dashBoardSymbols/brakesWarning.jpg");
    protected boolean brakesOil;
    protected Image brakesOilImage;
    protected ImageView brakesOilImageView;
    protected AbsolutePositioning brakesOilImageAbsPos;

    protected File fileBattery = new File("dashBoardSymbols/battery.jpg");
    protected boolean battery;
    protected Image batteryImage;
    protected ImageView batteryImageView;
    protected AbsolutePositioning batteryImageAbsPos;

    protected File fileAbs = new File("dashBoardSymbols/abs.jpg");
    protected boolean abs;
    protected Image absImage;
    protected ImageView absImageView;
    protected AbsolutePositioning absImageAbsPos;

    protected File fileParking = new File("dashBoardSymbols/parking.jpg");
    protected boolean parking;
    protected Image parkingImage;
    protected ImageView parkingImageView;
    protected AbsolutePositioning parkingImageAbsPos;

    protected File fileHighBeams = new File("dashBoardSymbols/highBeams.jpg");
    protected boolean highBeams;
    protected Image highBeamsImage;
    protected ImageView highBeamsImageView;
    protected AbsolutePositioning highBeamsImageAbsPos;

    protected File fileOilPressure = new File("dashBoardSymbols/oilPressure.jpg");
    protected boolean oilPressure;
    protected Image oilPressureImage;
    protected ImageView oilPressureImageView;
    protected AbsolutePositioning oilPressureImageAbsPos;

    protected File fileSparkPlug = new File("dashBoardSymbols/sparkPlug.jpg");
    protected boolean sparkPlug;
    protected Image sparkPlugImage;
    protected ImageView sparkPlugImageView;
    protected AbsolutePositioning sparkPlugImageAbsPos;

    protected File fileTurningSigns = new File("dashBoardSymbols/turnSigns.jpg");
    protected boolean turnSigns;
    protected Image turningSignsImage;
    protected ImageView turningSignsImageView;
    protected AbsolutePositioning turningSignsImageAbsPos;

    protected Image backgroundImage;
    protected ImageView backgroundImageView;
    protected AbsolutePositioning backgroundImageAbsPos;

    protected File fileIce = new File("dashBoardSymbols/ice formation.jpg");
    protected boolean ice;
    protected Image iceImage;
    protected ImageView iceImageView;
    protected AbsolutePositioning iceImageAbsPos;

    public Dashboard() {
        super();
        configureInstruments();
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Double getRpm() {
        return rpm;
    }

    public void setRpm(double rpm) {
        this.rpm = rpm;
    }

    public double getDistance() {
        return distance;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getDiesel() {
        return diesel;
    }

    public void setDiesel(double diesel) {
        this.diesel = diesel;
    }

    public boolean isBrakesOil() {
        return brakesOil;
    }

    public void setBrakesOil(boolean brakesOil) {
        this.brakesOil = brakesOil;
        Platform.runLater(() -> brakesOilImageView.setVisible(brakesOil));
    }

    public boolean isBattery() {
        return battery;
    }

    public void setBattery(boolean battery) {
        this.battery = battery;
        Platform.runLater(() -> batteryImageView.setVisible(battery));
    }

    public boolean isAbs() {
        return abs;
    }

    public void setAbs(boolean abs) {
        this.abs = abs;
        Platform.runLater(() -> absImageView.setVisible(abs));
    }

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
        Platform.runLater(() -> parkingImageView.setVisible(parking));
    }

    public boolean isHighBeams() {
        return highBeams;
    }

    public void setHighBeams(boolean highBeams) {
        this.highBeams = highBeams;
        Platform.runLater(() -> highBeamsImageView.setVisible(highBeams));
    }

    public boolean isOilPressure() {
        return oilPressure;
    }

    public void setOilPressure(boolean oilPressure) {
        this.oilPressure = oilPressure;
        Platform.runLater(() -> oilPressureImageView.setVisible(oilPressure));
    }

    public boolean isSparkPlug() {
        return sparkPlug;
    }

    public void setSparkPlug(boolean sparkPlug) {
        this.sparkPlug = sparkPlug;
        Platform.runLater(() -> sparkPlugImageView.setVisible(sparkPlug));
    }

    public boolean isTurnSigns() {
        return turnSigns;
    }

    public void setTurnSigns(boolean turnSigns) {
        this.turnSigns = turnSigns;
        Platform.runLater(() -> turningSignsImageView.setVisible(turnSigns));
    }

    public void setIce(boolean ice) {
        this.ice = ice;
        Platform.runLater(() -> iceImageView.setVisible(ice));
    }

    public void configureInstruments() {
    }
}
