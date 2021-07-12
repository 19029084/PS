package com.synopsys.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.PathVariable;



import org.springframework.stereotype.Controller;

import com.coverity.ws.v9.*;

import com.synopsys.coverity.CoverityEndpoint;
import javax.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.synopsys.service.*;

import java.util.UUID;


import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;

import com.synopsys.entity.PSTask;
import java.util.List;



@Controller
@SessionAttributes({"endpoint","user"}) 
public class MainController {
	private final String USER = "user";
	private final String ENDPOINT = "endpoint";
	private final String UPLOADED = "workspaces";
	
	@Autowired
    private MainService mainService;
	
	
	
	@RequestMapping("/")
	//@ResponseBody
	public String index() {
		//return "Greetings from Spring Boot!";
		
		return "login";
	}
	
	@RequestMapping(value="login",method = RequestMethod.POST)
	//@ResponseBody
	public String login(@RequestParam("username") String username, 
					@RequestParam("password") String password,
					@RequestParam(name="host",defaultValue="http://127.0.0.1:8080") String hostUrl,
					Model model) {
		//return "Greetings from Spring Boot!";
		
		CoverityEndpoint endpoint = new CoverityEndpoint(hostUrl,username,password);

		DefectService ds= null;
		ConfigurationService cs = null;
		VersionDataObj version = null;
		try
		{
			cs = endpoint.getCS();
			ds = endpoint.getDS();
			version = cs.getVersion();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		if(version!=null)
		{
			model.addAttribute(USER,username);
			
			mainService.createEndpoint(endpoint);
			
			model.addAttribute(ENDPOINT, endpoint);
			return "redirect:/display?action=list&&PageNo=1";
		}
		else
		{
			return "redirect:/";
		}
		//return "home";
	}
	
	@RequestMapping("/display")
	//@ResponseBody
	public String display(@RequestParam(name = "action") String action,
						  @RequestParam(name="pageNo",defaultValue="1") int pageNo, 
						  @RequestParam(name="pageSize",defaultValue="10") int pageSize,
						  Model model) {
		
		CoverityEndpoint endpoint = (CoverityEndpoint)model.getAttribute(ENDPOINT);
		if(endpoint != null)
		{	
			if(action.equals("list"))
			{
				PageInfo<PSTask> page = mainService.getTasks(pageNo,pageSize);
				model.addAttribute("pageInfo", page);
				

				return "home";
			}
			else if(action.equals("create"))
			{
				List<String> workspaces = mainService.getWorkspaces();
				
				model.addAttribute("workspaces",workspaces);
				return "create";
			}
			else
			{
				return "home";
			}
		}
		else
		{
			return "login";
		}
	}
	
	@RequestMapping(value="/task/{taskId}",method=RequestMethod.GET)
	public String deleteTask(@PathVariable("taskId") int taskId,
							 @RequestParam(name="pageNo",defaultValue="1") int pageNo,
							 Model model)
	{
		CoverityEndpoint endpoint = (CoverityEndpoint)model.getAttribute(ENDPOINT);
		if(endpoint != null)
		{
			System.out.println("Deleting");
			mainService.deleteTask(taskId);
			return "redirect:/display?action=list&&PageNo="+pageNo;
		}else{
			return "login";
		}

	}
	@PostMapping("/task/url")
	public String createTask(
							@RequestParam("endpoint") String endpoint,
							@RequestParam("workspace") String workspace,
							@RequestParam("project") String project,
							@RequestParam("stream") String stream,
							@RequestParam("sourceURL") String source)
	{
					
			System.out.println("Create URL Task");
					
			//try
			{
				String dirName = UPLOADED+File.separator+workspace;
				
				String uuid =  UUID.randomUUID().toString().replaceAll("-", "");
				
				
				
				String ext= null;
				
				if(source!=null && source.length()>0 && source.contains(" ")){
					ext = source.substring(0,source.indexOf(" ")).trim();
					source=source.substring(source.lastIndexOf(" ")).trim();
				}else{
					ext = "zip";
				}				
				
				System.out.println(ext+" source:"+source);
				
				mainService.createTask(endpoint,uuid,workspace,project,stream,ext,source);
				
				
			}
			//catch(IOException e)
			{
				//e.printStackTrace();
			}
			
			return "redirect:/display?action=list";
	
	
	
	}
	
	@PostMapping("/task/file")
	public String createTask(
							@RequestParam("endpoint") String endpoint,
							@RequestParam("workspace") String workspace,
							@RequestParam("project") String project,
							@RequestParam("stream") String stream,
							@RequestParam("sourceFile") MultipartFile source)
	{
		System.out.println("Create File Task");
		
		if (source.isEmpty()) {
		    return null;
		}
		String originalFilename = source.getOriginalFilename();
		
		String ext= null;
		if(originalFilename.contains(".")){
		    ext = originalFilename.substring(originalFilename.lastIndexOf("."));
		}else{
		    ext = ".zip";
		}


			try
			{
				
				//SimpleDateFormat sdf =new SimpleDateFormat("yyyy"+File.separator+"MM"+File.separator+"dd");
				
				//Date now= new Date();
				//String dateStr = sdf.format(now);
				String dirName = UPLOADED+File.separator+workspace;
				
				String uuid =  UUID.randomUUID().toString().replaceAll("-", "");
				
				String fileName= dirName+File.separator+uuid + ext;
				
				System.out.println("file:"+fileName);
				
				File target = new File(dirName);
				
				if(!target.exists()){
					
					target.mkdirs();				
	
				}
				else
				{
					//target.delete();
				}
				
				byte[] bytes = source.getBytes();
					
				Path path = Paths.get(fileName);
				
				Files.write(path, bytes);
				
				//source.transferTo(target);

				mainService.createTask(endpoint,uuid,workspace,project,stream,ext.substring(1),uuid + ext);
				
				
			}catch(IOException e)
			{
				e.printStackTrace();
			}
			
			return "redirect:/display?action=list";
							 
	}
	
	
	
	
	
	

}