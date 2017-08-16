/**
 *Creates a zip file containing all the test-result files, which will be then uploaded to QMetry - JIRA
 */

package com.qmetry.qaf.automation.integration.qtm4j;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

	public static final FileFilter JSON_FILE_FILTER = new FileFilter() {
		public boolean accept(File file) {
			return file.isDirectory() || file.getName().toLowerCase().endsWith("xml");
		}

	};

	/**
	 * @param sourceDir
	 *            source directory to compress
	 * @param zipfile
	 *            target zipfile
	 * @throws IOException
	 */
	public static void zipDirectory(String sourceDir, String zipfile) throws IOException {
		File dir = new File(sourceDir);
		File zipFile = new File(zipfile);
		FileOutputStream fout = new FileOutputStream(zipFile);
		ZipOutputStream zout = new ZipOutputStream(fout);
		zipSubDirectory("", dir, zout);
		zout.close();
	}

	private static void zipSubDirectory(String basePath, File dir, ZipOutputStream zout)
			throws IOException {
		byte[] buffer = new byte[4096];
		File[] files = dir.listFiles(JSON_FILE_FILTER);
		for (File file : files) {
			if (file.isDirectory()) {
				String path = basePath + file.getName() + "/";
				zout.putNextEntry(new ZipEntry(path));
				zipSubDirectory(path, file, zout);
				zout.closeEntry();
			} else if(file.getName().endsWith(".xml")){
				FileInputStream fin = new FileInputStream(file);
				zout.putNextEntry(new ZipEntry(basePath + file.getName()));
				int length;
				while ((length = fin.read(buffer)) > 0) {
					zout.write(buffer, 0, length);
				}
				zout.closeEntry();
				fin.close();
			}
		}
	}
}
