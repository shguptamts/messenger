package org.shgup.ss.messenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.shgup.ss.messenger.model.Message;
import org.shgup.ss.messenger.service.MessageService;

@Path("messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {
	
	MessageService messageService = new MessageService();
	
	@GET
	public List<Message> getMessages(@QueryParam("year") int year,
										@QueryParam("start") int start,
										@QueryParam("size") int size)
	{
		if(year>0)
			return messageService.getAllMessagesFromYear(year);
		else if(start>=0 && size>0)
			return messageService.getAllMessagesFiltered(start, size);
		
		return messageService.getAllMessages();
	}
	
	
//  using Bean class to get query parameters	
//	@GET
//	public List<Message> getMessages( @BeanParam MessageFilterBean messageBean)
//	{
//		if(messageBean.getYear()>0)
//			return messageService.getAllMessagesFromYear(messageBean.getYear());
//		else if(messageBean.getStart()>=0 && messageBean.getSize()>0)
//			return messageService.getAllMessagesFiltered(messageBean.getStart(), messageBean.getSize());
//		
//		return messageService.getAllMessages();
//	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId")long id, @Context UriInfo uriInfo)
	{
		Message message= messageService.getMessage(id);
		message.addLink( getUriForSelf(uriInfo, message), "self");
		message.addLink( getUriForProfile(uriInfo,message),"profile");
		message.addLink( getUriForComments(uriInfo, message), "comments");
		return message;
	}


	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String url = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(Long.toString( message.getId()))
				.build()
				.toString();
		return url;
	}
	
	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String url = uriInfo.getBaseUriBuilder()
				.path(ProfileResource.class)
				.path( message.getAuthor())
				.build()
				.toString();
		return url;
	}
	
	private String getUriForComments(UriInfo uriInfo, Message message)
	{
		String url = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(MessageResource.class,"getComments")
				.path(CommentResource.class)
				.resolveTemplate("messageId", message.getId())
				.build()
				.toString();
		return url;
	}
	
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException
	{
		
		Message newMessage  =  messageService.addMessage(message);
		String newId=  String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri)
				.header("token", "sdhsj")
				.entity(message)
				.build();
		 
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id,Message message)
	{
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id)
	{
		messageService.removeMessage(id);
	}

	@Path("/{messageId}/comments")
	public CommentResource getComments()
	{
		return new CommentResource();
	}

}
