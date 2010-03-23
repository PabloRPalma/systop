package com.systop.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 使用poi，写Excel的帮助类
 * @author Sam
 * @version 1.0.1
 */
public final class HssfUtil {
  /**
   * Log of the class
   */
  private static Log log = LogFactory.getLog(HssfUtil.class);
  
  /**
   * prevent from initializing
   */
  private HssfUtil() {
  }

  /**
   * 创建一个新的Excel工作簿
   */
  public static HSSFWorkbook createWorkbook() {
    return new HSSFWorkbook();
  }

  /**
   * 创建一个Excel工作表
   * @param book 工作簿句柄
   * @param sheetName 工作表名称
   */
  public static HSSFSheet createSheet(HSSFWorkbook book, String sheetName,
      int sheetIndex) {
    HSSFSheet s = book.createSheet();
    if (sheetName != null) {
      book.setSheetName(sheetIndex, sheetName, HSSFWorkbook.ENCODING_UTF_16);
    }
    return s;
  }

  /**
   * 创建工作表中的一行
   * @param sheet 工作表句柄
   * @param cells 要创建的行的各个Cell的值
   * @param index 该行索引
   */
  public static HSSFRow createRow(HSSFSheet sheet, Object[] cells, int index) {
    HSSFRow row = sheet.createRow(index);
    if (cells != null) {
      for (int i = 0; i < cells.length; i++) {
        HSSFCell cell = row.createCell((short) i);
        setCellValue(cell, cells[i]);
      }
    }
    return row;
  }

  /**
   * 创建工作表中的一行
   * @param sheet 工作表句柄
   * @param cells 要创建的行的各个Cell的值
   * @param index 该行索引
   */
  public static HSSFRow createRow(HSSFSheet sheet, Iterator cells, int index) {
    HSSFRow row = sheet.createRow(index);
    if (cells != null) {
      for (int i = 0; cells.hasNext(); i++) {
        HSSFCell cell = row.createCell((short) i);
        setCellValue(cell, cells.next());
      }
    }
    return row;
  }
  /**
   * 为工作表中某个元素(Cell)赋值
   * @param cell 指定的元素
   * @param value 元素的value
   */
  public static HSSFCell setCellValue(HSSFCell cell, Object value) {
    if (cell == null) {
      return cell;
    }
    cell.setEncoding(HSSFCell.ENCODING_UTF_16);
    if (value == null) {
      cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
      return cell;
    }

    if (value instanceof Number) {
      Number n = (Number) value;
      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      cell.setCellValue(n.doubleValue());
    } else if (value instanceof Boolean) {
      Boolean bool = (Boolean) value;
      cell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
      cell.setCellValue(bool.booleanValue());
    } else if (value instanceof Date) {
      cell.setCellValue(DateUtil.convertDateToString((Date) value));
    } else {
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue(value.toString());
    }

    return cell;
  }

  /**
   * 根据给定File对象，创建一个Excel文档，并填充内容
   * @param file 给定的文档
   * @param data 给定的数据
   * @param sheetName 工作表名称
   * @return 文件位置
   */
  public synchronized static File createWorkbook(File file, Object[][] data,
      String sheetName) {
    HSSFWorkbook book = createWorkbook();
    HSSFSheet sheet = createSheet(book, sheetName, 0);

    for (int i = 0; i < data.length; i++) {
      HSSFRow row = sheet.createRow(i);
      for (int j = 0; j < data[i].length; j++) {
        HSSFCell cell = row.createCell((short) j);
        setCellValue(cell, data[i][j]);
      }
    }

    return saveWorkbook(book, file);
  }

  /**
   * 将ResultSet中的数据保存为Excel表格
   * @param file Excel的文件名
   * @param rs 包含数据的ResultSet对象
   * @param headerNames 标题行的名字，可以为null,如果为null,则各列名称为fieldName
   * @param fieldNames 各个列的名字，可以为null,如果为null,
   * 则各列名称从ResultSetMetaData中取得
   * @param sheetName 工作表名称
   * @return
   */
  public synchronized static File createWorkbook(File file, final ResultSet rs,
      String[] headerNames, String[] fieldNames, String sheetName) {
    HSSFWorkbook book = createWorkbook();
    HSSFSheet sheet = createSheet(book, sheetName, 0);
    // Header
    int beginIndex = 0;

    if (headerNames != null) {
      beginIndex = setHeader(sheet, headerNames);
    } else if (fieldNames != null) { // 将字段名作为列名
      beginIndex = setHeader(sheet, fieldNames);
    } else { // 如果没有字段名，则取ResultSetMetaData中的字段名
      try {
        ResultSetMetaData metaData = rs.getMetaData();
        fieldNames = new String[metaData.getColumnCount()];
        for (int i = 0; i < fieldNames.length; i++) {
          fieldNames[i] = metaData.getColumnName(i + 1);
        }
        beginIndex = setHeader(sheet, fieldNames);
      } catch (SQLException e) {
        log.error("Error get meta data:" + e.getMessage());
      }
    }
    //导入ResultSet
    try {
      while (rs.next()) {
        HSSFRow row = sheet.createRow(beginIndex);
        beginIndex++;
        for (int i = 0; i < fieldNames.length; i++) {
          HSSFCell cell = row.createCell((short) i);
          setCellValue(cell, rs.getObject(i + 1));
        }
      }
    } catch (SQLException e) {
      log.error("error on result set iterator:" + e.getMessage());
    }
    
    return saveWorkbook(book, file);
  }

  /**
   * 设置第一行
   * @param sheet 表格实例
   * @param headerNames 第一行所显示的各个列的文字
   * @return
   */
  private static int setHeader(HSSFSheet sheet, String[] headerNames) {
    HSSFRow header = sheet.createRow(0);
    for (int i = 0; i < headerNames.length; i++) {
      HSSFCell cell = header.createCell((short) i);
      setCellValue(cell, headerNames[i]);
    }

    return 1;
  }

  /**
   * 保存工作簿
   * @param book 工作簿句柄
   * @param file 文件位置和名称
   * @return 文件位置和名称
   */
  public synchronized static File saveWorkbook(HSSFWorkbook book, File file) {
    if (book == null || file == null) {
      return null;
    }
    FileOutputStream fileOut = null;
    try {
      fileOut = new FileOutputStream(file);
    } catch (FileNotFoundException e) {
    }
    try {
      book.write(fileOut);
      fileOut.flush();
      fileOut.close();
    } catch (IOException e) {
      log.error("Error save work book:" + e.getMessage());
      e.printStackTrace();
    }

    return file;
  }
}
