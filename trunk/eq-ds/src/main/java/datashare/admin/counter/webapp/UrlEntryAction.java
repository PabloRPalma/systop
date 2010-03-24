package datashare.admin.counter.webapp;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.systop.core.webapp.struts2.action.DefaultCrudAction;

import datashare.admin.counter.model.UrlEntry;
import datashare.admin.counter.service.UrlEntryManager;

/**
 * 网站统计Action
 * @author wbb
 */
@SuppressWarnings({"serial","unchecked"})
@Deprecated
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UrlEntryAction extends DefaultCrudAction<UrlEntry, UrlEntryManager> {
}
