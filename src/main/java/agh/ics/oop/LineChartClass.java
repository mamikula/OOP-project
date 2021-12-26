package agh.ics.oop;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;


public class LineChartClass {
//    https://docs.oracle.com/javafx/2/charts/line-chart.htm
    final NumberAxis xAxis;
    final NumberAxis yAxis;
    final LineChart<Number,Number> lineChart;
    private final XYChart.Series series1;
    private final XYChart.Series series2;

    public LineChartClass() {
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        series1 = new XYChart.Series();
        series2 = new XYChart.Series();
        lineChart.setMaxSize(300, 300);
        lineChart.getData().addAll(series1, series2);
        series1.setName("Animal");
        series2.setName("Grass");
    }

    public void printChart(int day, int animals, int grasses ) {

        series1.getData().add(new XYChart.Data(day, animals));
        series2.getData().add(new XYChart.Data(day, grasses));
    }

    public LineChart<Number, Number> getLineChart() {
        return lineChart;
    }
}
