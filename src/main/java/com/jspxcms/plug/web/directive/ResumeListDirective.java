package com.jspxcms.plug.web.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.jspxcms.common.freemarker.Freemarkers;
import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.plug.domain.Resume;
import com.jspxcms.plug.service.ResumeService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * ResumeListDirective
 * <p>
 * FreeMarker标签类需实现TemplateDirectiveModel接口
 *
 * @author liufang
 */
public class ResumeListDirective implements TemplateDirectiveModel {
    public static final String SITE_ID = "siteId";

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        // 使用标签时，返回变量必须存在，如[@ResumeList; list]...[/@ResumeList]中分号后的list。
        if (loopVars.length < 1) {
            throw new TemplateModelException("Loop variable is required.");
        }
        // 标签体必须存在，即[@ResumeList; list]...[/@ResumeList]中间的部分。
        if (body == null) {
            throw new RuntimeException("missing body");
        }
        // 获取标签参数，如[@ResumeList siteId='123'; list]...[/@ResumeList]中的123。
        Integer[] siteId = Freemarkers.getIntegers(params, SITE_ID);
        if (siteId == null && params.get(SITE_ID) == null) {
            // 如果没有传入siteId这个参数，则获取当前站点的ID。
            siteId = new Integer[]{ForeContext.getSiteId(env)};
        }
        Sort defSort = new Sort(Direction.DESC, "creationDate", "id");
        Limitable limitable = Freemarkers.getLimitable(params, defSort);
        List<Resume> list = service.findList(siteId, limitable);
        // 将获取的数据放到返回变量里。
        loopVars[0] = env.getObjectWrapper().wrap(list);
        // 执行标签体。
        body.render(env.getOut());
    }

    @Autowired
    private ResumeService service;
}
