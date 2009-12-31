package com.systop.fsmis.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * 执行sql文件的初始化操作
 * 
 * @author Lunch
 */
public class SqlInitializer {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	private JdbcTemplate jdbcTemplate;

	/**
	 * 待执行的Sql文件资源
	 */
	private Resource[] sqlResources;

	/**
	 * 本类被实例化后默认执行的方法
	 */
	@PostConstruct
	@Transactional
	public void init() {
		if (sqlResources == null) {
			logger.debug("数据初始化(SqlInitializer)未指定sql文件.");
			return;
		}
		String tableName = null;
		for (Resource resource : sqlResources) {
			tableName = getTableName(resource.getFilename());
			if (enableInit(tableName)) {
				executeSqlResource(resource);
			}
		}
	}

	/**
	 * 根据sql文件名解析初始化数据的表名称
	 * 
	 * @param sqlFileName
	 * @return
	 */
	private String getTableName(String sqlFileName) {
		if (sqlFileName.indexOf(".") > 0) {
			sqlFileName = sqlFileName.substring(0, sqlFileName.indexOf("."));
		}
		return sqlFileName;
	}

	/**
	 * 判断指定的表名是否可以初始化
	 * 
	 * @param tableName
	 * @return
	 */
	private boolean enableInit(String tableName) {
		boolean flg = true;
		try {
			int count = jdbcTemplate.queryForInt("select count(0) from "
					+ tableName);
			if (count > 0) {// 表中已经存在数据，禁止初始化操作
				logger.debug(tableName + "中已经存在数据，未执行初始化操作.");
				flg = false;
			}
		} catch (Exception e) {// 查询异常，可能是tableName对应的表不存在。
			logger.error(e.getMessage());
			logger.error("数据库中不存在此表:" + tableName);
			flg = false;
		}
		return flg;
	}

	/**
	 * 执行指定的sql文件
	 * 
	 * @param sqlFile
	 * @throws DataAccessException
	 * @throws IOException
	 */
	private void executeSqlResource(Resource resource) {
		for (String sql : buildSql(resource)) {
			jdbcTemplate.execute(sql);
		}
	}

	/**
	 * 读取sql语句
	 * 
	 * @param resource
	 * @return
	 */
	private List<String> buildSql(Resource resource) {
		File sqlFile = null;
		FileInputStream in = null;
		BufferedReader reader = null;
		List<String> sqls = new ArrayList<String>();
		try {
			sqlFile = resource.getFile();
			in = new FileInputStream(sqlFile);
			reader = new BufferedReader((new InputStreamReader(in, "UTF-8")));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sqls.add(line);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			logger.error("文件：" + resource.getFilename() + "不存在，请检查配置文件是否正确。");
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sqls;
	}

	/**
	 * @param sqlResources
	 *            the sqlResources to set
	 */
	public void setSqlResources(Resource[] sqlResources) {
		this.sqlResources = sqlResources;
	}
}
