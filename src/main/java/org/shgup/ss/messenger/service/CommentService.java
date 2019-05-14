package org.shgup.ss.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.shgup.ss.messenger.database.DatabaseClass;
import org.shgup.ss.messenger.exception.ErrorMessage;
import org.shgup.ss.messenger.model.Comment;
import org.shgup.ss.messenger.model.Message;

public class CommentService {
	
	private Map<Long,Message> messages = DatabaseClass.getMessagges();
	
	
	public List<Comment> getAllComments( long messageId)
	{
		return new ArrayList<Comment>(messages.get(messageId).getComments().values());
	}
	
	public Comment getComment(long messageId, long commentId)
	{
		ErrorMessage errorMessage  = new ErrorMessage("Comments not found",404,"No documentationFound"); 
		Response response= Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
		
		Message message=messages.get(messageId);
		if(message==null)
			throw new WebApplicationException(response);
		Comment comment=message.getComments().get(commentId);
		if(comment==null)
			throw new NotFoundException(response);
		return messages.get(messageId).getComments().get(commentId);
	}
	
	public Comment addComment(long messageId,Comment comment)
	{
		Map<Long, Comment> comments= messages.get(messageId).getComments();
		comment.setId(comments.size()+1 );
		comments.put(comment.getId(), comment);
		return comment;
		
	}
	
	public Comment updateComment(long messageId,Comment comment)
	{
		Map<Long, Comment> comments= messages.get(messageId).getComments();
		if(comment.getId() <=0)
			return null;
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment removeComment(long messageId, long commentId)
	{
		Map<Long, Comment> comments= messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
		
	
}
