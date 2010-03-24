package datashare.rd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageDb {
  public static void main(String[] args) throws Exception {
    String seedFile = null;
    String netSta = null;
    String gifFile = null;

    if ((args.length) > 2) {
      seedFile = args[0];
      netSta = args[1];
      gifFile = args[2];
    } else {
      throw new Exception("参数不够！");
    }

    BufferedReader inlist = new BufferedReader(new FileReader("dir.cnf"));
    String host;
    String database;
    String table;
    String user;
    String passwd;
    try {
      for (int i = 0; i < 4; i++) { // 跳过前4行
        inlist.readLine();
      }
      String line = inlist.readLine(); // 数据库HOST
      StringTokenizer t = new StringTokenizer(line, ":");
      host = t.nextToken();
      host = t.nextToken().trim();

      line = inlist.readLine(); // Database or schema
      t = new StringTokenizer(line, ":");
      database = t.nextToken();
      database = t.nextToken().trim();

      line = inlist.readLine(); // table name
      t = new StringTokenizer(line, ":");
      table = t.nextToken();
      table = t.nextToken().trim();

      line = inlist.readLine(); // database user
      t = new StringTokenizer(line, ":");
      user = t.nextToken();
      user = t.nextToken().trim();

      line = inlist.readLine(); // database password
      t = new StringTokenizer(line, ":");
      passwd = t.nextToken();
      passwd = t.nextToken().trim();
    } finally {
      inlist.close();
    }

    Class.forName("org.gjt.mm.mysql.Driver");
    StringBuffer url = new StringBuffer().append("jdbc:mysql://").append(host).append(":3306/")
        .append(database);
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = DriverManager.getConnection(url.toString(),user, passwd);
      conn.setAutoCommit(true);
      /*数据库DDL:
       * create table seed_plots (id int primary key auto_increment,
       * MapFile varchar(255),SeedFile varchar(255),Station varchar(255));
       */
      pstmt = conn.prepareStatement("INSERT INTO " + table
          + "(MapFile,SeedFile,Station)VALUES(?,?,?)");
      pstmt.setString(1, gifFile);
      pstmt.setString(2, seedFile);
      pstmt.setString(3, netSta);
      pstmt.executeUpdate();
    } finally { //一定要关闭连接呀
      if (pstmt != null) {
        pstmt.close();
      }
      if (conn != null) {
        conn.close();
      }
    }
  }

  public static boolean isNumeric(String str) {
    Pattern pattern = Pattern.compile("[0-9]*");
    Matcher isNum = pattern.matcher(str);
    if (!isNum.matches()) {
      return false;
    }
    return true;
  }
}
