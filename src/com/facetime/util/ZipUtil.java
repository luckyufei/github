package com.facetime.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	/**
	 * ����ʼ��Ƿ���ڣ������ڴ���
	 *
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static boolean checkFileDirectory(String filePath) {
		File f = new File(filePath);
		if (!(f.exists() && f.isDirectory())) {
			f.mkdirs();
			return false;
		} else {
			return true;
		}
	}

	/**
	 * ����ָ���ļ���Ŀ���ļ�
	 *
	 * @param sourceFileName
	 * @param destFileName
	 */
	public static void copy(String sourceFileName, String destFileName) throws Exception {
		try {
			File source = new File(sourceFileName);
			File dest = new File(destFileName);

			FileInputStream fis = new FileInputStream(source);
			FileOutputStream fos = new FileOutputStream(dest);

			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = fis.read(buf)) != -1) {
				fos.write(buf, 0, len);
			}
			fis.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void delete(File f) throws Exception {
		try {
			if (f.exists()) {
				if (f.isDirectory()) {
					File[] child = f.listFiles();
					for (File fc : child) {
						delete(fc);
					}
				}
				f.delete();
			}
			f.deleteOnExit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * delete file
	 *
	 * @param file
	 * @throws Exception
	 */
	public static void delete(String file) throws Exception {
		try {
			File f = new File(file);
			delete(f);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * �Ƿ���ѹ���ļ�
	 *
	 * @param file
	 * @return
	 */
	public static boolean isZipFile(File file) {
		boolean b = false;
		try {
			ZipFile zf = new ZipFile(file);
			zf.close();
			b = true;
		} catch (ZipException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * �����ļ�����Ŀ¼·��
	 *
	 * @param filePath
	 * @throws Exception
	 */
	public static void mkDir(String filePath) throws Exception {
		File f = new File(filePath);
		if (!(f.exists() && f.isDirectory())) {
			f.mkdirs();
		}
	}

	/**
	 * ��ѹ�����ļ� fis:��ͨ�Ķ��ļ���
	 *
	 * @param fis
	 * @param uploadPath
	 * @return
	 */
	public static List<String> unZip(InputStream fis, String uploadPath) {
		int BUFFER = 2048;// ���û������С2kb
		int count;// ʵ�ʶ������ֽ�
		// ��ѹ�����Ĵ��ļ����б�
		List<String> fileNames = new ArrayList<String>();
		byte data[] = new byte[BUFFER];
		try {
			// ѹ�����ļ���
			ZipInputStream zin = new ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry = zin.getNextEntry();
			// һ����ѹ���ļ�
			String name = "";
			while (entry != null) {
				if (entry.isDirectory()) {
					;
				} else {
					name = entry.getName();
					int i = name.lastIndexOf("/");
					if (i != -1) {
						name = name.substring(i + 1, name.length());
					}
					FileOutputStream fos = new FileOutputStream(uploadPath + File.separator + name);
					BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
					// ��д����
					while ((count = zin.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}
					String fileName = uploadPath + File.separator + entry.getName();
					fileNames.add(fileName);
					dest.flush();
					dest.close();
				}
				// ��ȡ��һ��ZipEntry
				entry = zin.getNextEntry();
			}
			zin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ���ؽ�ѹ�������ļ����б�
		return fileNames;
	}

	/**
	 * ��ѹ����ļ�
	 *
	 * @param zipNameList
	 * @param uploadPath
	 * @return
	 * @throws Exception
	 */
	public static List<String> unZip(List<String> zipNameList, String uploadPath) throws Exception {
		// ���û������С2kb
		int BUFFER = 2048;
		// ʵ�ʶ������ֽ�
		int count;
		// ��ѹ�����Ĵ��ļ����б�
		List<String> fileNames = new ArrayList<String>();

		Iterator<String> it = zipNameList.iterator();
		while (it.hasNext()) {
			// �õ�ѹ���ļ����ļ���
			String zipName = it.next();
			byte data[] = new byte[BUFFER];
			// ��ͨ�Ķ��ļ���
			FileInputStream fis = new FileInputStream(zipName);
			// ѹ�����ļ���
			ZipInputStream zin = new ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry;
			String name = "";
			// һ����ѹ���ļ�
			while ((entry = zin.getNextEntry()) != null) {
				if (entry.isDirectory()) {
					;
				} else {
					name = entry.getName();
					int i = name.lastIndexOf("/");
					if (i != -1) {
						name = name.substring(i + 1, name.length());
					}
					FileOutputStream fos = new FileOutputStream(uploadPath + File.separator + name);
					BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
					// ��д����
					while ((count = zin.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}
					String fileName = uploadPath + File.separator + entry.getName();
					fileNames.add(fileName);
					dest.flush();
					dest.close();
				}
			}
			zin.close();
		}
		// ���ؽ�ѹ�������ļ����б�
		return fileNames;
	}

	/**
	 * ��ѹ�����ļ� zipName:ѹ���ļ����ļ���
	 *
	 * @param zipName
	 * @param uploadPath
	 * @return
	 */
	public static List<String> unZip(String zipName, String uploadPath) {
		// ���û������С2kb
		int BUFFER = 2048;
		// ʵ�ʶ������ֽ�
		int count;
		// ��ѹ�����Ĵ��ļ����б�
		List<String> fileNames = new ArrayList<String>();
		byte data[] = new byte[BUFFER];
		try {
			// ��ͨ�Ķ��ļ���
			FileInputStream fis = new FileInputStream(zipName);
			// ѹ�����ļ���
			ZipInputStream zin = new ZipInputStream(new BufferedInputStream(fis));
			String name = "";
			ZipEntry entry;
			// һ����ѹ���ļ�
			while ((entry = zin.getNextEntry()) != null) {
				if (entry.isDirectory()) {
					;
				} else {
					name = entry.getName();
					int i = name.lastIndexOf("/");
					if (i != -1) {
						name = name.substring(i + 1, name.length());
					}
					FileOutputStream fos = new FileOutputStream(uploadPath + File.separator + name);
					BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
					// ��д����
					while ((count = zin.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}
					String fileName = uploadPath + File.separator + entry.getName();
					fileNames.add(fileName);
					dest.flush();
					dest.close();
				}
			}
			zin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ���ؽ�ѹ�������ļ����б�
		return fileNames;
	}

	/**
	 * ѹ���ļ���
	 *
	 * @param outputFileName
	 * @param inputFileName
	 * @return
	 */
	public static Boolean zip(String outputFileName, String inputFileName) {
		ZipOutputStream out;
		try {
			out = new ZipOutputStream(new FileOutputStream(outputFileName));
			File f = new File(inputFileName);
			if (f.exists()) {
				if (f.isDirectory()) {
					File[] fl = f.listFiles();
					for (File element : fl) {
						out.putNextEntry(new ZipEntry(element.getName()));
						FileInputStream in = new FileInputStream(element);
						byte[] buffer = new byte[1024];
						int b = 0;
						while ((b = in.read(buffer)) != -1) {
							out.write(buffer, 0, b);
							out.flush();
						}
						in.close();
					}
				} else if (f.isFile()) {
					out.putNextEntry(new ZipEntry(f.getName()));
					FileInputStream in = new FileInputStream(f);
					byte[] buffer = new byte[1024];
					int b = 0;
					while ((b = in.read(buffer)) != -1) {
						out.write(buffer, 0, b);
						out.flush();
					}
					in.close();
				}
			} else {
				return false;
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
