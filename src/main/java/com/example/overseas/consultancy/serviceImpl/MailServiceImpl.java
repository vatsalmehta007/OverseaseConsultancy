package com.example.overseas.consultancy.serviceImpl;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.overseas.consultancy.entity.UserDto;
import com.example.overseas.consultancy.service.MailService;

@Service("mailService")
@Transactional
public class MailServiceImpl implements MailService
{
	public static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	
	@Autowired
	Session session;

	@Autowired
	private JavaMailSender mailsender;
	
	@Override
	public void sendwelcomemail(UserDto user) 
	{
		try
		{
			
			MimeMessage massage =new MimeMessage(session);
			MimeMessageHelper helper=new MimeMessageHelper(massage);
		
			helper.setTo(user.getEmail());
			helper.setText("hello "+user.getName()+" welcom to overseas consultancy (^_^)");
			helper.setSubject("this is overseas consultancy");
			mailsender.send(massage);
			
			logger.info("mail send succsesfully" + user.getEmail());
		}
		catch (Exception e) 
		{
			
			logger.error("problem occure sending mail"+e.getMessage());
		}
	}

	
	
}
