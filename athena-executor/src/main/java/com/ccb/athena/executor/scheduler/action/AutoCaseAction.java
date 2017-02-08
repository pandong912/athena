package com.ccb.athena.executor.scheduler.action;

import com.ccb.athena.executor.scheduler.sender.Sender;
import com.ccb.athena.executor.scheduler.sender.SenderException;
import com.ccb.athena.executor.scheduler.sender.SenderFactory;
import com.ccb.athena.executor.scheduler.sender.SenderResult;

public class AutoCaseAction {
	
	public void sendMessage(){
		
		AutoCase autoCase = new AutoCase();
		
		Sender sender = SenderFactory.createDefault(autoCase.getChannel(), autoCase.getSecEnv(), autoCase.getSecNode());
		
		SenderResult senderResult = null;
		
		try {
			senderResult = sender.send(autoCase);
		} catch (SenderException e) {
			e.printStackTrace();
		}
	}

}
