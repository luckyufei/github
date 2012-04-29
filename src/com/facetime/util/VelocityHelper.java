package com.facetime.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * Velocity模版工具的工具类
 */
public class VelocityHelper {

	
	private static final String FILE_RESOURCE_LOADER_PATH = "file.resource.loader.path";

	static {
		// FIXME 想办法改成可以配置的.
		Properties prop = new Properties();
		prop.put("runtime.log", "logs/");
		// 指定模版文件存放目录
		prop.put(FILE_RESOURCE_LOADER_PATH, "template/");
		prop.put("input.encoding", "UTF-8");
		prop.put("output.encoding", "UTF-8");
		try {
			Velocity.init(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param dataMap 保存数据的键值对
	 * @param destFile 生成的目标文件, 必须包含路径.
	 * @param templateFile 模版文件, 如果已经在velocity.properties中指定了file.resource.loader.path,
	 * 						则可以是相对路径, 否则必须是绝对路径
	 */
	public static void create(Map<String, ?> dataMap, String destFile, String templateFile) {
		assert destFile != null;
		assert templateFile != null;
		try {
			String destDir = destFile.substring(0, destFile.lastIndexOf("/"));
			File saveDir = new File(destDir);
			if (!saveDir.exists()) {
				boolean saved = saveDir.mkdirs();
				if (!saved) {
					throw new AssertionError("mk dir failed.");
				}
			}
			VelocityContext context = new VelocityContext();
			for (Entry<String, ?> entry : dataMap.entrySet()) {
				context.put(entry.getKey(), entry.getValue());
			}
			File templateFileTmp = new File(templateFile);
			Template template = null;
			if (templateFileTmp.isAbsolute()) {
				template = Velocity.getTemplate(templateFile);
			} else {
				template = Velocity.getTemplate(FILE_RESOURCE_LOADER_PATH + templateFile);
			}
			FileOutputStream outStream = new FileOutputStream(new File(destFile));
			OutputStreamWriter writer = new OutputStreamWriter(outStream, "UTF-8");
			BufferedWriter sw = new BufferedWriter(writer);
			template.merge(context, sw);
			sw.flush();
			sw.close();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
