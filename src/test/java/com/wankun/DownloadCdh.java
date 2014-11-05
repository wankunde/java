package com.wankun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class DownloadCdh {
	public static String BASE_URL = "http://www.cloudera.com";
	public static String DOWNLOAD_DIR = "D:\\百度云同步盘\\我的文档\\docs\\y-Cloudera";
	// 创建HttpClient实例
	private static HttpClient client = new HttpClient();

	public static void main(String[] args) throws HttpException, IOException {
		HttpMethod method = new GetMethod("http://www.cloudera.com/content/cloudera/en/documentation/core/latest.html");
		client.executeMethod(method);
		BufferedReader reader = null;
		List<String> urlsline = new ArrayList<String>();
		try {
			reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.contains("pdf"))
					urlsline.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String pattern = "/content/cloudera/en/documentation/core/latest/PDF/";

		for (String line : urlsline) {
			int begin = 0;
			while (begin < line.length()) {
				boolean find = false;
				int idx1 = line.indexOf(pattern, begin);
				int idx2 = idx1 + pattern.length();
				for (int j = 0; j < 50 && !find; j++) {
					if (line.substring(idx2 + j).startsWith("pdf")) {
						String pdfurl = BASE_URL + line.substring(idx1, idx2 + j + 3);
						String filename = pdfurl.substring(pdfurl.lastIndexOf("/") + 1);
						System.out.println(pdfurl);
//						download(pdfurl, DOWNLOAD_DIR, filename);
						find = true;
					}
				}
				if (find)
					begin = line.length() + 1; // 退出查找当前行
				else
					begin = idx2 + 50;
			}
		}
	}

	public static void download(String url, String dir, String filename) {
		GetMethod httpget = new GetMethod(url);
		try {
			client.executeMethod(httpget);

			InputStream in = httpget.getResponseBodyAsStream();

			FileOutputStream out = new FileOutputStream(new File(dir, filename));

			byte[] b = new byte[1024];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			in.close();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpget.releaseConnection();
		}
	}
}
