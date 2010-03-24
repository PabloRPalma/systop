package datashare.rd;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.StringTokenizer;

public class Batch {
  public static void main(String[] args) throws Exception {
    BufferedReader inlist = new BufferedReader(new FileReader("dir.cnf"));
    String host;
    String database;
    String table;
    String user;
    String passwd;
    String bin;
    //读取数据库连接信息和bin目录路径
    try {
      for (int i = 0; i < 3; i++) { // 跳过前4行
        inlist.readLine();
      }
      bin = inlist.readLine(); // bin目录的位置
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
    //加载jdbc驱动
    Class.forName("com.mysql.jdbc.Driver");
    //创建 jdbc url
    StringBuffer url = new StringBuffer().append("jdbc:mysql://").append(host)
        .append(":3306/").append(database);
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    BufferedReader reader = null; //读取rec.list
    //rec.list文件，位于bin目录下
    if(bin == null || bin.length() == 0) {
      throw new Exception("dir.cnf有错误!");
    }
    
    if(!bin.endsWith(File.separator)) {
      bin += "/rec.list";
    } else {
      bin += "rec.list";
    }
    File file = new File(bin);
    if(!file.exists()) {
      System.out.println(file.getAbsolutePath() + "不存在！");
      return;
    }
    
    try {
      //创建数据库连接
      conn = DriverManager.getConnection(url.toString(), user, passwd);
      conn.setAutoCommit(false);
      System.out.println("Open database connection.");
      //准备statement
      pstmt = conn.prepareStatement("INSERT INTO " + table
          + "(SeedFile,Station,MapFile)VALUES(?,?,?)");
      //读取rec.list
      reader = new BufferedReader(new FileReader(file));
      String line = "";
      int index = 0;
      int count = 0;
      while ((line = reader.readLine()) != null) {
        if (line.length() == 0) {
          continue;
        }
        String[] info = line.split(","); //第一个是seedFile,第二个是netSta,第三个是gifFile
        if (info != null && info.length == 3) {
          pstmt.setString(1, info[0]);
          pstmt.setString(2, info[1]);
          pstmt.setString(3, info[2]);
          pstmt.addBatch(); //批量提交
          index++;
          if(index % 50 ==0) { //如果够50个了，则提交
            count += sum(pstmt.executeBatch());    
            conn.commit();
            pstmt.clearBatch();
          }
        }       
      }
      //提交剩余的
      count += sum(pstmt.executeBatch());
      conn.commit();
      pstmt.clearBatch();
      System.out.println(count + " records inserted.");
    } catch (Exception e) {
      conn.rollback();
    }
    finally { // 一定要关闭连接呀
      if (pstmt != null) {
        pstmt.close();
      }
      if (conn != null) {
        System.out.println("Close database connection.");
        conn.close();
      }
      if(reader != null) {
        reader.close();
      }
      file.delete(); //删除文件，以便于下一次执行rd.sh不会冲突
    }
  }
  
  static int sum(int[] counts) {
    if(counts == null) {
      return 0;
    }
    int count = 0;
    for(int i= 0; i < counts.length; i++) {
      count += counts[i];
    }
    return count;
  }
}
