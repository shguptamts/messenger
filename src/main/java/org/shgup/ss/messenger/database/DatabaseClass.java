package org.shgup.ss.messenger.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shgup.ss.messenger.model.Message;
import org.shgup.ss.messenger.model.Profile;

public class DatabaseClass {
	
	private static Map<Long,Message> messages =  new HashMap<>();
	private static Map<String,Profile> profiles = new HashMap<>(); 
	
	
	
	public static Map<Long,Message> getMessagges()
	{
		return messages;
	}
	
	public static Map<String,Profile> getProfiles()
	{
		return profiles;
	}

}
