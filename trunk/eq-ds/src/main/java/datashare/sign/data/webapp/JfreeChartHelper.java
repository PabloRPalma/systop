package datashare.sign.data.webapp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import com.systop.core.util.DateUtil;

public class JfreeChartHelper {
  /**
   * 将曲线图CSV数据转换为图片
   */
  public void getImage(String title, StringBuffer csv, String dateFormat, OutputStream out) {
   
    XYDataset dataset = new TimeSeriesCollection(createEURTimeSeries(csv, dateFormat));
    XYItemRenderer xyRender = new XYLineAndShapeRenderer(true, false);
    xyRender.setBaseToolTipGenerator(new StandardXYToolTipGenerator("[{1}, {2}]",
        new SimpleDateFormat(dateFormat), new DecimalFormat("0.00##")));

    xyRender.setSeriesStroke(0, new BasicStroke(1F, 1, 2));
    xyRender.setSeriesPaint(0, new Color(0, 0, 0x99));

    
    //domainAxis = new DateAxis("观测时间");
    DateAxis domainAxis = new DateAxis();
    //根据采样率时间格式设置X轴时间显示格式
    domainAxis.setDateFormatOverride(new SimpleDateFormat(dateFormat));
    
    //valueAxis = new NumberAxis("观测值");
    NumberAxis valueAxis = new NumberAxis();
    valueAxis.setAutoRangeIncludesZero(false);

    XYPlot plot = new XYPlot(dataset, domainAxis, valueAxis, xyRender);
    plot.setBackgroundPaint(Color.white);
    plot.setDomainGridlinePaint(Color.gray);
    plot.setRangeGridlinePaint(Color.gray);
    plot.setOrientation(PlotOrientation.VERTICAL);
    Font titleFont = new Font("Tahoma", Font.BOLD, 20);
    JFreeChart chart = new JFreeChart(title, titleFont, plot, false);
    chart.setBackgroundPaint(Color.white);
    LegendTitle legend = new LegendTitle(plot);
    chart.addSubtitle(legend);
    
    try {
      ChartUtilities.writeChartAsJPEG(out, chart, 650, 350);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private TimeSeries createEURTimeSeries(StringBuffer csv, String dateFormat) {
    TimeSeries t = new TimeSeries("", Second.class);
    BufferedReader reader = null;
    try {     
      reader = new BufferedReader(new StringReader(csv.toString()));
      String line = null;
      // boolean first = true;
      while ((line = reader.readLine()) != null) {
        if (line != null && line.indexOf(";") > 0) {
          String[] xy = line.split(";");
          Date date = DateUtil.convertStringToDate(dateFormat, xy[0]);
          if (xy.length > 1) {// 当出现[2009-04-21 00:37;]情况的时候
            t.addOrUpdate(new Second(date), Double.valueOf(xy[1]));
          } else {
            t.addOrUpdate(new Second(date), null);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
        }
      }     
    }
    return t;
  }
}
