package com.synopsys.util;

import java.io.File;

public class PSUtil
{
	
	public static String workspaces()
	{
		File root = new File("");
		String workspace="";
		try
		{
			workspace = root.getCanonicalPath()+File.separator+"workspaces";
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return workspace;
		}
		
	}
	
	public static String logFile(String uniqueKey)
	{
		return "log_"+uniqueKey+".zip";
	}
	
	
	public static void deleteAll(File file) {
		if(file.exists())
		{
			if (file.isFile() || file.list().length == 0) {
				file.delete();
			} else {
				for (File f : file.listFiles()) {
					deleteAll(f); // 递归删除每个文件

				}
				file.delete(); // 删除文件夹
			}
		}
    }
	
};