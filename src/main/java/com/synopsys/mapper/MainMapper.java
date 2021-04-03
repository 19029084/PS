package com.synopsys.mapper;

import java.util.List;

import com.synopsys.entity.PSTask;

import org.apache.ibatis.annotations.Mapper;

import com.synopsys.coverity.CoverityEndpoint;

@Mapper
public interface MainMapper
{
	
	List<String> selectAllWorkspaces();
		
	List<PSTask> selectAllTasks();
	
	List<PSTask> selectPendingTasks();
	
	List<PSTask> selectDeletedTasks();
	
	PSTask selectOneTask(int id);
	
	int createTask(PSTask task);
	
	void updateTaskStatus(PSTask task,String status);
	
	void deleteOneTask(int id);
	
	int createEndpoint(CoverityEndpoint endpoint);
	
	boolean isExists(CoverityEndpoint endpoint);
	
	CoverityEndpoint loadEndpoint(CoverityEndpoint endpoint);
	
	CoverityEndpoint loadEndpointById(String endpointId);
	
	
}