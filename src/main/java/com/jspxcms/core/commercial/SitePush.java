package com.jspxcms.core.commercial;

import com.jspxcms.core.service.*;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

public class SitePush {
    public static String sitePushForm(Integer[] ids, HttpServletRequest request, org.springframework.ui.Model modelMap, SiteService siteService, InfoQueryService query) {
        // 本功能在商业版中提供
        return "redirect:/support_genuine.jsp";
    }

    public static String sitePush(Integer[] ids, Integer[] siteId, Integer[] nodeId, HttpServletRequest request, RedirectAttributes ra, InfoService service, OperationLogService logService, Logger logger) {
        // 本功能在商业版中提供
        return "redirect:/support_genuine.jsp";
    }

    public static String sitePushList(Pageable pageable, HttpServletRequest request, org.springframework.ui.Model modelMap, InfoPushService infoPushService) {
        // 本功能在商业版中提供
        return "redirect:/support_genuine.jsp";
    }

    public static String sitePushDelete(Integer[] ids, HttpServletRequest request, RedirectAttributes ra, InfoService service, InfoPushService infoPushService, OperationLogService logService, Logger logger) {
        // 本功能在商业版中提供
        return "redirect:/support_genuine.jsp";
    }
}
