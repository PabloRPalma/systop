package datashare.sign.data.webapp;

import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
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

public class SignApplet extends Applet implements ChangeListener, ChartChangeListener {
  private String dateFormat = "yyyy-MM-dd HH:mm:ss";
  //主panle用于布局
  private JPanel mainPanel;
  private ChartPanel chartPanel;
  private JFreeChart chart;
  private XYPlot plot;
  private XYDataset dataset;
  private DateAxis domainAxis;
  private NumberAxis valueAxis;
  private JScrollBar hScrollBar;
  private JScrollBar vScrollBar;
  private int xScrollValue;
  private int yScrollValue;

  public void init() {
    dateFormat(); // 获取时间格式
    mainPanel = new JPanel();
    createChartPanel();
    chartPanel.setDomainZoomable(true);
    // Y轴不可调整大小
    chartPanel.setRangeZoomable(false);

    chartPanel.setPreferredSize(new Dimension(600, 400));
    chartPanel.setLayout(new BorderLayout());
    initHorScrollBar();
    initVerScrollBar();
    initPanel();
    add(mainPanel);
  }

  private void createChartPanel() {
    String url = getDataUrl();
    System.out.println("Visit URL for data : " + url);
    dataset = new TimeSeriesCollection(createEURTimeSeries(url));
    XYItemRenderer xyRender = new XYLineAndShapeRenderer(true, false);
    xyRender.setBaseToolTipGenerator(new StandardXYToolTipGenerator("[{1}, {2}]",
        new SimpleDateFormat(dateFormat), new DecimalFormat("0.00##")));

    xyRender.setSeriesStroke(0, new BasicStroke(1F, 1, 2));
    xyRender.setSeriesPaint(0, new Color(0, 0, 0x99));

    
    //domainAxis = new DateAxis("观测时间");
    domainAxis = new DateAxis();
    //根据采样率时间格式设置X轴时间显示格式
    domainAxis.setDateFormatOverride(new SimpleDateFormat(dateFormat));
    
    //valueAxis = new NumberAxis("观测值");
    valueAxis = new NumberAxis();
    valueAxis.setAutoRangeIncludesZero(false);

    plot = new XYPlot(dataset, domainAxis, valueAxis, xyRender);
    plot.setBackgroundPaint(Color.white);
    plot.setDomainGridlinePaint(Color.gray);
    plot.setRangeGridlinePaint(Color.gray);
    plot.setOrientation(PlotOrientation.VERTICAL);

    chart = new JFreeChart(getParameter("title"), JFreeChart.DEFAULT_TITLE_FONT, plot, false);
    chart.setBackgroundPaint(Color.white);
    LegendTitle legend = new LegendTitle(plot);
    chart.addSubtitle(legend);

    chartPanel = new ChartPanel(chart);
  }
  
  private void initHorScrollBar() {
    hScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 10, 0, 100);

    hScrollBar.addAdjustmentListener(new AdjustmentListener() {
      public void adjustmentValueChanged(AdjustmentEvent e) {
        double lower = domainAxis.getLowerBound();
        double upper = domainAxis.getUpperBound();
        // 计算两次滚动的差值，作为range的增量
        double x = (e.getValue() - xScrollValue) * 60 * 60 * 1000;
        xScrollValue = e.getValue(); // 记录本次滚动的位置
        domainAxis.setRange(lower + x, upper + x);
      }
    });
  }

  private void initVerScrollBar() {
    vScrollBar = new JScrollBar(JScrollBar.VERTICAL, 50, 10, 0, 100);

    vScrollBar.addAdjustmentListener(new AdjustmentListener() {
      public void adjustmentValueChanged(AdjustmentEvent e) {
        double y = e.getValue() - yScrollValue;
        yScrollValue = e.getValue();

        chartPanel.setZoomInFactor(1 / 1.2);
        chartPanel.setZoomOutFactor(1.2);
        if (y > 0) {
          chartPanel.zoomOutRange(0, 0);
        }
        if (y < 0) {
          chartPanel.zoomInRange(0, 0);
        }
      }
    });
  }

  /**
   * 根据参数，构建获取数据的URL
   */
  private String getDataUrl() {
    String urlPattern = "http://{0}:{1}{2}/datashare/sign/data/csv.do?"
        + "model.methodId={3}&model.itemId={4}&model.pointId={5}"
        + "&model.sampleRate={6}&model.tableCategory={7}&model.stationId={8}"
        + "&model.startDate={9}&model.endDate={10}";
    List<String> list = new ArrayList<String>();

    list.add(getParameter("host"));
    list.add(getParameter("port"));
    list.add(getParameter("contextPath"));
    list.add(getParameter("methodId"));
    list.add(getParameter("itemId"));
    list.add(getParameter("pointId"));
    list.add(getParameter("sampleRate"));
    list.add(getParameter("tableCategory"));
    list.add(getParameter("stationId"));
    list.add(getParameter("startDate"));
    list.add(getParameter("endDate"));

    return MessageFormat.format(urlPattern, list.toArray());
  }

  /**
   * 根据参数构建获取合适的时间格式的URL
   */
  private String getDateFmtUrl() {
    String urlPattern = "http://{0}:{1}{2}/datashare/admin/samplerate/dateFormat.do?"
        + "model.id={3}";
    List<String> list = new ArrayList<String>();
    list.add(getParameter("host"));
    list.add(getParameter("port"));
    list.add(getParameter("contextPath"));
    list.add(getParameter("sampleRate"));
    return MessageFormat.format(urlPattern, list.toArray());
  }

  /**
   * 访问采样率Action，根据采样率代码，得到合适的时间格式，将这个时间格式保存到 {@link #dateFormat}变量
   */
  private void dateFormat() {
    String url = getDateFmtUrl();
    System.out.println("Visit URL:" + url);
    HttpURLConnection conn = null;
    InputStream in = null;
    BufferedReader reader = null;
    try {
      URL u = new URL(url);
      conn = (HttpURLConnection) u.openConnection();
      conn.connect();
      in = conn.getInputStream();
      reader = new BufferedReader(new InputStreamReader(in));
      String temp = reader.readLine();
      if (temp != null && temp.length() > 0) {
        dateFormat = temp;
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
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
        }
      }
      if (conn != null) {
        conn.disconnect();
      }
    }
  }

  /**
   * 访问URL，获取CSV格式的数据，将这些数据转换为用于JFreeChart的TimeSeries对象
   */
  private TimeSeries createEURTimeSeries(String url) {
    TimeSeries t = new TimeSeries("", Second.class);
    HttpURLConnection conn = null;
    InputStream in = null;
    BufferedReader reader = null;
    try {
      URL u = new URL(url);
      conn = (HttpURLConnection) u.openConnection();
      conn.connect();
      in = conn.getInputStream();
      reader = new BufferedReader(new InputStreamReader(in));
      String line = null;
      // boolean first = true;
      while ((line = reader.readLine()) != null) {
        if (line != null && line.indexOf(";") > 0) {
          String[] xy = line.split(";");
          Date date = toDate(xy[0]);
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
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
        }
      }
      if (conn != null) {
        conn.disconnect();
      }
    }
    return t;
  }

  private Date toDate(String strDate) throws ParseException {
    if (strDate == null || strDate.length() == 0) {
      return new Date();
    }
    SimpleDateFormat df = null;
    Date date = null;
    df = new SimpleDateFormat(dateFormat);

    try {
      date = df.parse(strDate);
    } catch (ParseException pe) {
      // log.error("ParseException: " + pe);
      throw new ParseException(pe.getMessage(), pe.getErrorOffset());
    }

    return (date);
  }

  private void initPanel() {
    mainPanel.setBackground(Color.white);
    javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
    mainPanel.setLayout(mainPanelLayout);
    mainPanelLayout.setHorizontalGroup(
        mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(mainPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(hScrollBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chartPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(vScrollBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(54, Short.MAX_VALUE))
    );
    mainPanelLayout.setVerticalGroup(
        mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(mainPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(vScrollBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chartPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(hScrollBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
  }

  @Override
  public void stateChanged(ChangeEvent e) {
     //do onthing...
    //System.out.println("State Changed");
  }

  @Override
  public void chartChanged(ChartChangeEvent e) {
    //do onthing...
    //System.out.println("Chart Changed");
  }
}
