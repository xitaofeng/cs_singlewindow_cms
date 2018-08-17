package com.jspxcms.ext.quartz;

import com.jspxcms.core.constant.Constants;
import com.jspxcms.ext.service.VisitLogService;
import org.joda.time.DateTime;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * 访问日志删除任务
 *
 * @author liufang
 */
public class VisitLogDeleteJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(VisitLogDeleteJob.class);

    public static final String SITE_ID = "siteId";
    public static final String DAY = "day";

    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            ApplicationContext appContext = (ApplicationContext) context.getScheduler().getContext().get(Constants.APP_CONTEXT);
            VisitLogService service = appContext.getBean(VisitLogService.class);
            JobDataMap map = context.getJobDetail().getJobDataMap();
            Integer siteId = map.getIntegerFromString(SITE_ID);
            Integer day = map.getIntegerFromString(DAY);
            DateTime dt = DateTime.now().minusDays(day);
            service.deleteByDate(dt.toDate(), siteId);
            logger.info("run visit log delete job");
        } catch (SchedulerException e) {
            throw new JobExecutionException("Cannot get ApplicationContext", e);
        }
    }
}
