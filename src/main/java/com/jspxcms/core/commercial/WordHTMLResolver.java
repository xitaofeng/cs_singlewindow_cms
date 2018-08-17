package com.jspxcms.core.commercial;

import java.io.IOException;
import java.net.URISyntaxException;

import org.htmlparser.util.ParserException;

import com.jspxcms.common.file.FileHandler;
import com.jspxcms.core.service.AttachmentService;

/**
 * 处理由word文档（doc、docx）转换成的HTML。本功能在商业版中提供。
 * 
 * @author liufang
 *
 */
public class WordHTMLResolver {
	public static String resolver(String filename, String prefix, String path, FileHandler fileHandler, String ip,
			Integer userId, Integer siteId, AttachmentService attachmentService) throws ParserException,
			URISyntaxException, IllegalStateException, IOException {
		return "本功能在商业版中提供";
	}
}
