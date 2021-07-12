package com.synopsys.service;

import java.util.List;
import java.util.ArrayList;
import com.synopsys.entity.PSTask;

import org.springframework.stereotype.Service;

import com.synopsys.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.synopsys.coverity.CoverityEndpoint;

import java.io.File;
import com.synopsys.util.PSUtil;


@Service
public class MainService
{
    @Autowired
    private MainMapper mainMapper;
	
	public void createTask(String endpoint,String uniqueKey,String workspace,String project,String stream,String genre,String source)
	{
		PSTask task = new PSTask(endpoint,uniqueKey,workspace,project,stream,genre,source);
		
		mainMapper.createTask(task);	
	}
	
	public PageInfo<PSTask> getTasks(int pageNo, int pageSize)
	{
		PageHelper.startPage(pageNo,pageSize);
		
		List<PSTask> tasks = mainMapper.selectAllTasks();//new ArrayList<PSTask>();
		
		PageInfo<PSTask> page = new PageInfo<PSTask>(tasks);
		
		System.out.println(tasks);
		for(int i = 0;i < tasks.size();i++) {
			System.out.println(tasks.get(i));
		}
		
		return page;
		
	}
	
	
	public void deleteTask(int taskId)
	{
		//PSTask task = mainMapper.selectOneTask(taskId);
		//File src = new File(PSUtil.workspaces()+File.separator+task.getWorkspace()+File.separator+task.getSource());
		//PSUtil.deleteAll(src);
		//File log = new //File(PSUtil.workspaces()+File.separator+task.getWorkspace()+File.separator+PSUtil.logFile(task.getUniqueKey()));
		//PSUtil.deleteAll(log);
		mainMapper.deleteOneTask(taskId);
	}
	

	
	public void updateTaskStatus(PSTask task, String status)
	{
		mainMapper.updateTaskStatus(task,status);
		
	}
	
	public List<String> getWorkspaces()
	{
		
		//List<String> workspaces = mainMapper.selectAllWorkspaces();
		List<String> workspaces =new ArrayList<String>();
		
		File baseDir = new File(PSUtil.workspaces());
		
		if(baseDir.isDirectory())
		{
			File[] files = baseDir.listFiles();
			for(File f:files)
			{
				if(f.isDirectory()){
					workspaces.add(f.getName());
				}
			}
		}
		
		return workspaces;
		
	}
	
	public void createEndpoint(CoverityEndpoint endpoint)
	{
		if(!mainMapper.isExists(endpoint))
		{
			mainMapper.createEndpoint(endpoint);
		}
		else
		{
			CoverityEndpoint dbEndpoint = mainMapper.loadEndpoint(endpoint);
			if(!dbEndpoint.getPassword().equals(endpoint.getPassword()))
			{
				System.out.println("reset password");
				//dbEndpoint.setPassword(endpoint.getPassword());
			}
			endpoint.setId(dbEndpoint.getId());			
		}
	}
	
	public CoverityEndpoint loadEndpointById(String id)
	{
		return mainMapper.loadEndpointById(id);
	}
	
	
}