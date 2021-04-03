package com.synopsys.entity;

public class PSTask
{
	
	public PSTask()
	{
		
	}
	
	public PSTask(String endpointId,String uniqueKey,String workspace,String project,String stream,String source)
	{
		m_endpointId = endpointId;
		m_uniqueKey = uniqueKey;
		m_workspace = workspace;
		m_project = project;
		m_stream = stream;
		m_source = source;
		m_status= "init";
	}
	
	public void setId(Integer id)
	{
		m_id = id;
	}
	
	public Integer getId()
	{
		return m_id;
	}
	public void setWorkspace(String workspace)
	{
		m_workspace = workspace;
	}
	
	public String getWorkspace()
	{	
		return m_workspace;
	}
	
	public void setProject(String project)
	{
		m_project = project;
	}
	public String getProject()
	{	
		return m_project;
	}	
	
	public void setStream(String stream)
	{
		m_stream = stream;
	}
	
	public String getStream()
	{	
		return m_stream;
	}
	
	public void setStatus(String status)
	{
		m_status = status;
	}
	public String getStatus()
	{	
		return m_status;
	}	
	
	public void setSource(String source)
	{
		m_source = source;
	}
	
	public String getSource()
	{
		return m_source;
	}
	
	public void setUniqueKey(String uniqueKey)
	{
		m_uniqueKey = uniqueKey;
	}
	
	public String getUniqueKey()
	{
		return m_uniqueKey;
	}	
	public void setEndpointId(String endpointId)
	{
		m_endpointId = endpointId;
	}
	
	public String getEndpointId()
	{
		return m_endpointId;
	}	
	
	private int m_id;
	private String m_workspace;
	private String m_project;
	private String m_stream;
	private String m_source;
	private String m_status;
	private String m_uniqueKey;
	private String m_endpointId;
	
	private String m_log;
	
}