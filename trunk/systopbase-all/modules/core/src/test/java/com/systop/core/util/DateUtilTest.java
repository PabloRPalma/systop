package com.systop.core.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class DateUtilTest extends TestCase {

  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * Test method for {@link DateUtil#getDatePattern()}.
   */
  public void testGetDatePattern() {
    assertNotNull(DateUtil.getDatePattern());
  }

  /**
   * Test method for {@link DateUtil#getDate()}.
   */
  public void testGetDate() {
    assertNotNull(DateUtil.getDate(new Date()));
    assertTrue(DateUtil.getDate(new Date()).length() > 0);
    assertEquals("", DateUtil.getDate(null));
  }

  /**
   * Test method for {@link DateUtil#convertStringToDate(String, String)}.
   */
  public void testConvertStringToDate() {
    try {
      assertNotNull(DateUtil.convertStringToDate("yyyy/MM/dd", "2008/10/06"));
      assertNotNull(DateUtil.convertStringToDate("yyyy-MM-dd", "2008-10-06"));
      assertNotNull(DateUtil.convertStringToDate(null, DateUtil
          .getDate(new Date())));
      assertNotNull(DateUtil.convertStringToDate("", DateUtil
          .getDate(new Date())));
      assertEquals(DateUtil.getDateTime("yyyy-MM-dd", new Date()), DateUtil
          .getDateTime("yyyy-MM-dd", DateUtil.convertStringToDate(null, null)));

      String sDate = DateUtil
          .getDateTime(DateUtil.getDatePattern(), new Date());
      assertNotNull(DateUtil.convertStringToDate(sDate));
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * Test method for {@link DateUtil#getTimeNow()}.
   */
  public void testGetTimeNow() {
    assertNotNull(DateUtil.getTimeNow(new Date()));
  }

  /**
   * Test method for {@link DateUtil#getToday()}.
   */
  public void testGetToday() {
    try {
      Calendar cal = DateUtil.getToday();
      assertNotNull(cal);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * Test method for {@link DateUtil#getDateTime(String, Date)}.
   */
  public void testGetDateTime() {
    String sDate = "2008-10-06";
    Date date = null;
    try {
      date = DateUtil.convertStringToDate("yyyy-MM-dd", sDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    assertNotNull(DateUtil.getDateTime("yyyy-MM-dd", date));
    assertEquals(sDate, DateUtil.getDateTime("yyyy-MM-dd", date));
    assertNull(DateUtil.getDateTime("yyyy-MM-dd", null));
  }

  /**
   * Test method for {@link DateUtil#convertDateToString(Date)}.
   */
  public void testConvertDateToString() {
    assertNotNull(DateUtil.convertDateToString(new Date()));
    assertTrue(DateUtil.convertDateToString(new Date()).length() > 0);
    assertNull(DateUtil.convertDateToString(null));

    assertNotNull(DateUtil.convertDateToString(new Date(), "2008-10-06"));
    assertEquals("2008-10-06", DateUtil.convertDateToString(null, "2008-10-06"));
  }

  /**
   * Test method for {@link DateUtil#lastSecondOfDate(Date)}.
   */
  public void testLastSecondOfDate() {
    String sDate = "2008-10-06";
    Date date = null;
    try {
      date = DateUtil.convertStringToDate("yyyy-MM-dd", sDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    date = DateUtil.lastSecondOfDate(date);
    assertEquals("2008-10-06 23:59:59", DateUtil.getDateTime(
        "yyyy-MM-dd HH:mm:ss", date));
  }
  
  /**
   * Test method for {@link DateUtil#firstSecondOfDate(Date)}.
   */
  public void testFirstSecondOfDate() {
    String sDate = "2008-10-06";
    Date date = null;
    try {
      date = DateUtil.convertStringToDate("yyyy-MM-dd", sDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    
    date = DateUtil.firstSecondOfDate(date);
    assertEquals("2008-10-06 00:00:00", DateUtil.getDateTime(
        "yyyy-MM-dd HH:mm:ss", date));
  }
  
  /**
   * Test method for {@link DateUtil#add(Date, int, int)}.
   */
  public void testAdd() {
    Date date = new Date();
    assertEquals(date, DateUtil.add(date, Calendar.YEAR, 0));
    assertNotNull(DateUtil.add(date, Calendar.YEAR, 2));
  }
  
  /**
   * Test method for {@link DateUtil#getDate(Date, int)}.
   */
  public void testAddDay() {
    String sDate = "2008-10-06";
    Date date = null;
    try {
      date = DateUtil.convertStringToDate("yyyy-MM-dd", sDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    
    date = DateUtil.getDate(date, 10);
    assertEquals("2008-10-16", DateUtil.getDateTime("yyyy-MM-dd", date));
    
    assertNull(DateUtil.getDate(null, 100));
  }
  
  /**
   * Test method for {@link DateUtil#getMonth(Date, int)}.
   */
  public void testAddMonth() {
    String sDate = "2008-10-06";
    Date date = null;
    try {
      date = DateUtil.convertStringToDate("yyyy-MM-dd", sDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    
    date = DateUtil.getMonth(date, 2);
    assertEquals("2008-12-06", DateUtil.getDateTime("yyyy-MM-dd", date));
    
    assertNull(DateUtil.getMonth(null, 100));
  }
}
