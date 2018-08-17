package com.jspxcms.customer.constants;

import com.jspxcms.customer.utils.PropertyUtil;

public class CustomerConst {

    public static final String SELF_URL = PropertyUtil.getValue("self.url");

    public static final String URL4TGT = PropertyUtil.getValue("cas.tgt.url");

    public static final String URL4USER_INFO = PropertyUtil.getValue("cas.validate.url");

}
