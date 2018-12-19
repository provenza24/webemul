package fr.provenzano.webemul.web.rest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import fr.provenzano.webemul.service.dto.MailDTO;
@RestController
@RequestMapping("/api")
public class MailResource {

	private final Logger log = LoggerFactory.getLogger(MailResource.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@GetMapping("/mails")
    @Timed
    public List<MailDTO> getMailList(HttpServletRequest request) {
				
		List<MailDTO> mailList = new ArrayList<MailDTO>();
		
		log.info("The time is now {}", dateFormat.format(new Date()));

		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = dateFormat.parse("2018-10-23 10:00:00");

			// mail server connection parameters
			String host = "pop.cti-pacac.cnamts.fr";
			String user = "GCTI069_SIICAM";
			String password = "netscape";

			// connect to my pop3 inbox
			Properties properties = System.getProperties();
			Session session = Session.getDefaultInstance(properties);
			Store store = session.getStore("pop3");
			store.connect(host, user, password);
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);

			// get the list of inbox messages
			Message[] messages = inbox.getMessages();

			if (messages.length == 0)
				System.out.println("No messages found.");

			for (int i = messages.length-1; i >= messages.length-6; i--) {
				// stop after listing ten messages
				/*
				 * if (i > 10) { System.exit(0); inbox.close(true);
				 * store.close(); }
				 */

				Message message = messages[i];
				if (message.getSentDate().after(date)) {
					
					System.out.println(
							"------------------------------------------------------------------------------------------");
					System.out.println("Message " + (i + 1));

					System.out.println("From : " + message.getFrom()[0]);
					System.out.println("Subject : " + message.getSubject());
					System.out.println("Sent Date : " + message.getSentDate());
					//System.out.println(getTextFromMessage(message));					

					MailDTO mailDTO = new MailDTO();
					mailDTO.setFrom(message.getFrom()[0].toString());
					mailDTO.setSentDate(message.getSentDate());
					mailDTO.setSubject(message.getSubject());
					mailDTO.setContent(getTextFromMessage(message));
					mailList.add(mailDTO);
					
					System.out.println(
							"------------------------------------------------------------------------------------------");
				} else {
					break;
				}
			}

			inbox.close(true);
			store.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailList;
	}

	private String getTextFromMessage(Message message) throws MessagingException, IOException {
		String result = "";
		if (message.isMimeType("text/plain")) {						
			result = message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}

	private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				System.out.println("Plain text");
				result = result + "\n" + bodyPart.getContent();
				break; // without break same text appears twice in my tests
			} else if (bodyPart.isMimeType("text/html")) {
				System.out.println("HTML");
				String html = (String) bodyPart.getContent();
				result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}

}
