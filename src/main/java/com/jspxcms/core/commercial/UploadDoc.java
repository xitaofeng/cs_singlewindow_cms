package com.jspxcms.core.commercial;

import java.io.File;

import com.jspxcms.common.file.FileHandler;
import com.jspxcms.core.service.AttachmentService;

/**
 * 上传DOC文件。将doc文件转换成pdf，将pdf文件转化成swf文件。本功能在商业版中提供。
 * 
 * @author liufang
 *
 */
public class UploadDoc {
	public static void exec(AttachmentService attachmentService, FileHandler fileHandler, String pathname,
			String extension, String pdfPathname, String swfPathname, File file, String ip, Integer userId,
			Integer siteId) throws Exception {
		// 本功能在商业版中提供
	}
}
