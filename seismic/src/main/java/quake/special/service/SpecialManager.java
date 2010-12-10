package quake.special.service;

import java.io.File;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import quake.special.model.Special;

import com.systop.core.service.BaseGenericsManager;
@Service
public class SpecialManager extends BaseGenericsManager<Special> {
  /**
   * 删除专题信息，如存在照片则删除照片
   */
  @Transactional
  public void remove(Special s, String realPath) {
    if (s != null && StringUtils.isNotBlank(s.getFront_pic())) {
      File file = new File(realPath);
      if (file.exists()) {
        file.delete();
      }
    }
    super.remove(s);
  }
}
