package com.synopsys.job;

import java.io.File;
import com.synopsys.entity.PSTask;


import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.lang.StringBuilder;
import java.io.InputStreamReader;

import java.util.Arrays;

import com.synopsys.util.PSUtil;

import com.synopsys.service.MainService;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

import com.synopsys.coverity.CoverityEndpoint;


@Slf4j
public class PSCoverityThread extends Thread {
	
	//@Autowired
    protected MainService mainService;
	
    List<PSTask> queue = new ArrayList<PSTask>();
	
	List<Integer> taskIds = new ArrayList<Integer>();
	
    private final int MAX_QUEUE_SIZE = 20;
    private final int MAX_THREAD_COUNT = 2;
	
	private static PSCoverityThread instance= null;
	
	public static synchronized PSCoverityThread getInstance()
	{
		    if (instance == null) {
				instance = new PSCoverityThread();
				instance.start();
			}
			return instance;
	}
	
	protected PSCoverityThread()
	{
		
	}
	
	public void setService(MainService service)
	{
		mainService=service;
	}

    @Override
    public void start() {
        super.start();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    PSTask task = popTask();
					
					CoverityEndpoint endpoint=mainService.loadEndpointById(String.valueOf(task.getEndpointId()));
					
					boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
					List< List<String> > commands = new ArrayList< List<String> >();
					String curDir = PSUtil.workspaces()+File.separator+task.getWorkspace()+File.separator+"bin"+File.separator;
					if (isWindows) {
						try{
							//commands.add(Arrays.asList("cmd.exe", "/c", "dir"));
							
							commands.add(Arrays.asList(curDir+"setup.bat",task.getUniqueKey(),task.getGenre(),task.getSource()));
							
							commands.add(Arrays.asList(curDir+"configure.bat",task.getUniqueKey()));
							
							commands.add(Arrays.asList(curDir+"build.bat",task.getUniqueKey()));
							
							commands.add(Arrays.asList(curDir+"analyze.bat",task.getUniqueKey()));
							
							commands.add(Arrays.asList(curDir+"commit.bat",task.getUniqueKey(),endpoint.getScheme(),endpoint.getHost(),endpoint.getPort(),endpoint.getUsername(),endpoint.getPassword(),task.getStream()));
							
							commands.add(Arrays.asList(curDir+"clean.bat",task.getUniqueKey(),task.getSource()));
						}catch(Exception e)
						{
							e.printStackTrace();
						}
					}else {
						//commands.add(Arrays.asList("sh", "-c", "ls"));
						
						commands.add(Arrays.asList(curDir+"setup.sh",task.getUniqueKey(),task.getGenre(),task.getSource()));
						
						commands.add(Arrays.asList(curDir+"configure.sh",task.getUniqueKey()));
						
						commands.add(Arrays.asList(curDir+"build.sh",task.getUniqueKey()));
						
						commands.add(Arrays.asList(curDir+"analyze.sh",task.getUniqueKey()));
						
						commands.add(Arrays.asList(curDir+"commit.sh",task.getUniqueKey(),endpoint.getScheme(),endpoint.getHost(),endpoint.getPort(),endpoint.getUsername(),endpoint.getPassword(),task.getStream()));
						
						commands.add(Arrays.asList(curDir+"clean.sh",task.getUniqueKey(),task.getSource()));
					}
					System.out.println(commands);
					
					File dir = new File(curDir);
					
					
					
					for(int i=0;i<commands.size();i++)
					{
						updateTaskStatus(task,String.valueOf((int)((i/(float)commands.size())*100))+"%");
						
						execute(commands.get(i),dir);
					}		
					
					updateTaskStatus(task,"Done");
					
					
                }
            }
        };
        // Create a Group of Threads for processing
        for (int i = 0; i < MAX_THREAD_COUNT; i++) {
            new Thread(task).start();
        }
    }
	
	protected void execute(List<String> cmd,File dir)
	{

		//ProcessBuilder builder = ;

		//builder.command(cmd);
		
		//builder.directory(dir);
		
		try
		{
			Process process = new ProcessBuilder(cmd).directory(dir).start();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder strBuilder = new StringBuilder();
			String line = null;
			while ( (line = reader.readLine()) != null) {
				System.out.println(line);
				strBuilder.append(line);
				strBuilder.append(System.getProperty("line.separator"));
			}
			//String result = builder.toString();
			//System.out.println(result);
			//StreamGobbler streamGobbler = 
			//	new StreamGobbler(process.getInputStream(), System.out::println);
			//Executors.newSingleThreadExecutor().submit(streamGobbler);
			int exitCode = process.waitFor();
			assert exitCode == 0;	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	protected void updateTaskStatus(PSTask task,String status)
	{
		mainService.updateTaskStatus(task,status);
	}

    // Pulls a message from the queue
    // Only returns when a new message is retrieves
    // from the queue.
    private synchronized PSTask popTask() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        return queue.remove(0);
    }

    // Push a new message to the tail of the queue if
    // the queue has available positions
    public synchronized void pushTask(PSTask task) {
		if(!taskIds.contains(task.getId()))
		{
			if (queue.size() < MAX_QUEUE_SIZE) 
			{
				taskIds.add(task.getId());
				queue.add(task);
				notifyAll();
			}
		}

    }
}