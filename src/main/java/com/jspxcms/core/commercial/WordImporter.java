package com.jspxcms.core.commercial;

import java.io.IOException;
import java.net.URISyntaxException;

import org.htmlparser.util.ParserException;
import org.springframework.web.multipart.MultipartFile;

import com.jspxcms.common.web.PathResolver;
import com.jspxcms.core.domain.PublishPoint;
import com.jspxcms.core.service.AttachmentService;

/**
 * Word文档导入功能类。本功能在商业版中提供。
 * 
 * @author liufang
 *
 */
public class WordImporter {
	public static String importDoc(MultipartFile file, Integer userId, Integer siteId, String ip, String prefix,
			String path, PublishPoint point, PathResolver pathResolver, AttachmentService attachmentService)
			throws IllegalStateException, IOException, ParserException, URISyntaxException {
		return "本功能在商业版中提供";
	}
}
