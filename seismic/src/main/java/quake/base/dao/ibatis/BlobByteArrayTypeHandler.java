package quake.base.dao.ibatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.lob.LobHandler;

/**
 * 扩展Spring提供的{@link org.springframework.orm.ibatis.support.BlobByteArrayTypeHandler}
 * 原著中，LobHandler从<code>SqlMapClientFactoryBean</code>提取，
 * 因为项目的特殊要求（数据源是MySQL还是Oracle不确定），现在从SqlMapClientFactory提取。
 * @author Sam
 *
 */
public class BlobByteArrayTypeHandler extends
    org.springframework.orm.ibatis.support.BlobByteArrayTypeHandler {
  private static Logger logger = LoggerFactory.getLogger(BlobByteArrayTypeHandler.class);
  /**
   * Constructor used by iBATIS: fetches config-time LobHandler from
   * SqlMapClientFactory.
   * @see eq.core.base.sqlmap.SqlMapClientFactory#getConfigTimeLobHandler
   */
  public BlobByteArrayTypeHandler() {
    this(SqlMapClientFactory.getConfigTimeLobHandler());
  }

  /**
   * Constructor used for testing: takes an explicit LobHandler.
   */
  protected BlobByteArrayTypeHandler(LobHandler lobHandler) {
    super(lobHandler);
    logger.debug("BlobByteArrayTypeHandler created，LobHander is [{}]", lobHandler);
  }
}
