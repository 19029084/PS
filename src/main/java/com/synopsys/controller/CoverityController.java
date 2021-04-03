package com.synopsys.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;


import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

import com.synopsys.coverity.CoverityEndpoint;


@RestController
@RequestMapping("/coverity")
class CoverityController
{
	
	@RequestMapping("/{project}/streams")
	@ResponseBody
	public List<String> getStreams(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("project") String project) throws IOException {
				
				List<String> streams = new ArrayList<String>();
				HttpSession session = request.getSession();
				CoverityEndpoint endpoint = (CoverityEndpoint)session.getAttribute("endpoint");
				if(endpoint!=null)
				{
					streams.addAll(endpoint.getStreams());
				}				
				return streams;	
				
			}
	
	
	

	
	
}