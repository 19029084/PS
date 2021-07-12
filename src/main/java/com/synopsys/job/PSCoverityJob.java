package com.synopsys.job;

import com.synopsys.mapper.MainMapper;

import com.synopsys.service.MainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.DisposableBean;

import lombok.extern.slf4j.Slf4j;


import java.util.List;
import java.util.ArrayList;
import java.io.File;

import com.synopsys.util.PSUtil;

import com.synopsys.entity.PSTask;


@Component
@Slf4j
public class PSCoverityJob implements DisposableBean,Runnable {
	
	private Thread thread;
    private volatile boolean online = true;

    private final MainService mainService;

    private final MainMapper mainMapper;
	// dao 和service注入
    @Autowired
    public PSCoverityJob(MainService mainService, MainMapper mainMapper) {
        this.mainService = mainService;
        this.mainMapper = mainMapper;
        this.thread = new Thread(this);
        this.thread.start();
        log.info("Thread is starting");
    }

    @Override
    public void run() {
		PSCoverityThread psCoverityThread = PSCoverityThread.getInstance();
		
		psCoverityThread.setService(mainService);
		
        while (online) {
            // 数据库调用
           	List<PSTask> pendingTasks = mainMapper.selectPendingTasks();
            if (pendingTasks == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
				
				for(int i=0;i<pendingTasks.size();i++)
				{
					psCoverityThread.pushTask(pendingTasks.get(i));
				}
            }
			
           	List<PSTask> deletedTasks = mainMapper.selectDeletedTasks();
            if (deletedTasks == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
				
				//System.out.println(deletedTasks.size()+" tasks will be deleted!");
				
				for(int i=0;i<deletedTasks.size();i++)
				{
					PSTask task = deletedTasks.get(i);
					
					File src = new File(PSUtil.workspaces()+File.separator+task.getWorkspace()+File.separator+task.getSource());
					PSUtil.deleteAll(src);
					
					File log = new File(PSUtil.workspaces()+File.separator+task.getWorkspace()+File.separator+PSUtil.logFile(task.getUniqueKey()));
					PSUtil.deleteAll(log);
					
					File workdir = new File(PSUtil.workspaces()+File.separator+task.getWorkspace()+File.separator+task.getUniqueKey());
					PSUtil.deleteAll(workdir);

					
				}
            }			
			
			
        }

    }
	
    @Override
    public void destroy() throws Exception {
        online = false;
    }
}
