package org.shgup.ss.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.shgup.ss.messenger.database.DatabaseClass;
import org.shgup.ss.messenger.model.Message;

public class MessageService {
	
	private Map<Long,Message> messages =  DatabaseClass.getMessagges();
	
	public MessageService()
	{
		messages.put(1l, new Message(1l, "Hello world" , "Shubham"));
		messages.put(2l, new Message(2l,"Hello shubham", "world"));
	}
	
	public List<Message> getAllMessages()
	{
		
		return new ArrayList<Message>(messages.values());
	}
	
	public List<Message> getAllMessagesFromYear(int year)
	{
		List<Message> messagesFromYear =  new ArrayList<Message>();
		Calendar cal = Calendar.getInstance();
		for(Message message : messages.values())
		{
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR)== year)
				messagesFromYear.add(message);
		}
		
		return messagesFromYear;
	}
	
	public List<Message> getAllMessagesFiltered(int start, int size)
	{
		
		List<Message> list = new ArrayList<Message>(messages.values());
		if(start+size>list.size())
			return new ArrayList<Message>();
		return list.subList(start, start+size);
	}
	
	public Message getMessage(long id)
	{
		return messages.get(id);
	}
	
	public Message addMessage(Message message)
	{
		message.setId( messages.size() +1);
		messages.put(message.getId(), message);
		return message;
		
		
	}
	
	public Message updateMessage(Message message)
	{
		if(message.getId()<=0)
			return null;
		
		messages.put(message.getId(),message);
		return message;
		
	}
	
	public Message removeMessage(long id)
	{
		return messages.remove(id); 
	}

}
