package com.synopsys.coverity;
import com.coverity.ws.v9.*;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;
import javax.xml.ws.handler.Handler;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import javax.xml.datatype.XMLGregorianCalendar;

import java.util.Map;
import java.util.HashMap;



public class CoverityEndpoint
{
	
	public CoverityEndpoint()
	{
		m_scheme=null;
		m_host=null;
		m_port=null;
		m_username=null;
		m_password=null;
		m_hostUrl=null;
		m_id=0;
	}
	
	
	public CoverityEndpoint(String hostUrl, String username, String password)
	{
		//m_server = server;
		//m_port = port;
		m_hostUrl = hostUrl;		
		m_username = username;
		m_password = password;
	}
	
	public void setId(int id)
	{	
		m_id = id;
	}
	
	public int getId()
	{
		return m_id;
	}
	public void setHost(String host)
	{
		m_host = host;
	}
	public String getHost()
	{
		if((m_host==null||m_host.isEmpty())&&(m_hostUrl!=null&&!m_hostUrl.isEmpty()))
		{
			try{
				
				URL u = new URL(m_hostUrl);
				m_host = u.getHost();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return m_host;
	}
	
	public void setPort(String port)
	{
		m_port = port;
	}
	public String getPort()
	{
		if((m_port==null||m_port.isEmpty())&&(m_hostUrl!=null&&!m_hostUrl.isEmpty()))
		{
			try{
				
				URL u = new URL(m_hostUrl);
				m_port = String.valueOf(u.getPort());
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return m_port;
	}	
	
	public void setScheme(String scheme)
	{
		m_scheme = scheme;
	}
	public String getScheme()
	{
		if((m_scheme==null||m_scheme.isEmpty())&&(m_hostUrl!=null&&!m_hostUrl.isEmpty()))
		{
			try{
				URL u = new URL(m_hostUrl);
				m_scheme = u.getProtocol();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return m_scheme;
	}	
	
	public void setUsername(String username)
	{
		m_username = username;
	}
	public String getUsername()
	{
		
		return m_username;
	}	

	public void setPassword(String password)
	{
		m_password = password;
	}
	public String getPassword()
	{
		return m_password;
	}	
	
	protected String getHostUrl()
	{
		if(m_hostUrl!=null)
		{
			return m_hostUrl;
		}
		else
		{
			return m_scheme+"://"+m_host+":"+m_port;
		}
	}

	
	public Map<String,List<String> > getProjectStreams()
	{
		Map<String,List<String> > results = new HashMap< String,List<String> >();
		try
		{
			ProjectFilterSpecDataObj projectFilter = new ProjectFilterSpecDataObj();
			projectFilter.setNamePattern("*");
			List<ProjectDataObj> projects = getCS().getProjects(projectFilter);
			
			for(int i=0;i<projects.size();i++)
			{
				ProjectDataObj project = projects.get(i);
				System.out.println("Project--"+project.getId().getName());
				
				List<StreamDataObj> streams = project.getStreams();
				List<String> nameOfStreams = new ArrayList<String>();
				for(int j=0;j<streams.size();j++)
				{
					StreamDataObj stream = streams.get(j);
					System.out.println("stream--"+stream.getId().getName());
					nameOfStreams.add(stream.getId().getName());
				}
				
				results.put(project.getId().getName(),nameOfStreams);
			}		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		return results;
		
		
	}
	
	public List<String> getStreams()
	{
		List<String> results = new ArrayList<String>();
		try
		{
			StreamFilterSpecDataObj streamFilter = new StreamFilterSpecDataObj();
			streamFilter.setNamePattern("*");
			List<StreamDataObj> streams = getCS().getStreams(streamFilter);
			
			for(int i=0;i<streams.size();i++)
			{
				results.add(streams.get(i).getId().getName());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return results;
			
	}

	public DefectService getDS() throws java.net.MalformedURLException {
		System.out.println(getHostUrl()+ "/ws/v9/defectservice?wsdl");
        DefectServiceService dss = new DefectServiceService(
                new URL(getHostUrl() + "/ws/v9/defectservice?wsdl"),
                new QName("http://ws.coverity.com/v9", "DefectServiceService"));
        DefectService ds = dss.getDefectServicePort();

        // Attach an authentication handler to it
        BindingProvider bindingProvider = (BindingProvider) ds;
        bindingProvider.getBinding().setHandlerChain(
                Arrays.asList((Handler) new ClientAuthenticationHandlerWSS(m_username, m_password)));

        return ds;
    }
	
    public ConfigurationService getCS() throws java.net.MalformedURLException {
										  
		System.out.println(getHostUrl() + "/ws/v9/configurationservice?wsdl");
        ConfigurationServiceService dss = new ConfigurationServiceService(
                new URL( getHostUrl() + "/ws/v9/configurationservice?wsdl"),
                new QName("http://ws.coverity.com/v9", "ConfigurationServiceService"));
        ConfigurationService ds = dss.getConfigurationServicePort();

        // Attach an authentication handler to it
        BindingProvider bindingProvider = (BindingProvider) ds;
        bindingProvider.getBinding().setHandlerChain(
                Arrays.asList((Handler) new ClientAuthenticationHandlerWSS(m_username, m_password)));

        return ds;
    }
	
	
	private int m_id;
	private String m_scheme;
	private String m_host;
	private String m_port;
	private String m_username;
	private String m_password;
	private String m_hostUrl;
	
}