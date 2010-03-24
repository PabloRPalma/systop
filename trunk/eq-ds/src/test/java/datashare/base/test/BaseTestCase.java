package datashare.base.test;

import org.springframework.test.context.ContextConfiguration;

import com.systop.core.test.BaseTransactionalTestCase;
@ContextConfiguration(locations = { "classpath*:spring/applicationContext-*.xml" })
public class BaseTestCase extends BaseTransactionalTestCase {

}
