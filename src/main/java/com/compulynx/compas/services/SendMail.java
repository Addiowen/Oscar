package com.compulynx.compas.services;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by paulk on 2/1/2019.
 */

@Service
public class SendMail {
	private Logger logger = LoggerFactory.getLogger(SendMail.class);

	public boolean InitiateMail(String subject, String recipient, String message, String emailattachment,
			String emailHost, String emailPort, String emailUsername, String emailSender, String emailPass,
			String sslState) {
		try {
			if (message.contains("html")) {
				// Create the email message
				HtmlEmail email = new HtmlEmail();
				email.setHostName(emailHost);
				email.setSmtpPort(Integer.parseInt(emailPort));
				email.setDebug(false);
				email.setAuthenticator(new DefaultAuthenticator(emailSender, emailPass));
				if ("true".equals(sslState) || sslState.equals("true")) {
					email.setSSLOnConnect(true);
				} else {
					email.setTLS(true);
				}
				email.addTo(recipient);
				email.setFrom(emailSender, emailUsername);
				email.setSubject(subject);
				if (!"".equals(emailattachment) && emailattachment != null) {
					// Create the attachment
					File file = new File(emailattachment);
					EmailAttachment attachment = new EmailAttachment();
					attachment.setPath(emailattachment);
					attachment.setDisposition(EmailAttachment.ATTACHMENT);
					attachment.setDescription(emailUsername);
					attachment.setName(file.getName());
					email.attach(attachment);
				}

				// embed the image and get the content id
				URL url = new URL("someurl");
				String cid = email.embed(url, "Exim Logo");
				// set the html message
				email.setHtmlMsg(message + "<html><h5>Somecompany</h5><p>Powered by somecompany</p></html>"
						+ "<img src=\"cid:" + cid + "\">");
				email.send();
			} else if (message.contains("http")) { // dowbload attachment from a link if the source contains http
				System.out.println("SENDING HTTP FORMATTED EMAIL");
				// Create email message
				MultiPartEmail multipartemail = new MultiPartEmail();
				multipartemail.setHostName(emailHost);
				multipartemail.setSmtpPort(Integer.parseInt(emailPort));
				multipartemail.setDebug(false);
				multipartemail.setAuthenticator(new DefaultAuthenticator(emailSender, emailPass));
				if (emailUsername.equals("true")) {
					multipartemail.setSSLOnConnect(true);
				} else {
					multipartemail.setTLS(true);
				}
				multipartemail.addTo(recipient);
				multipartemail.setFrom(emailSender, emailUsername);
				multipartemail.setSubject(subject);
				multipartemail.setMsg(message);
				if (!"".equals(emailattachment) && emailattachment != null) {
					// Create the attachment
					File file = new File(emailattachment);
					EmailAttachment attachment = new EmailAttachment();
					attachment.setURL(new URL(emailattachment));
					attachment.setDisposition(EmailAttachment.ATTACHMENT);
					attachment.setDescription(emailUsername);
					attachment.setName(file.getName());
					multipartemail.attach(attachment);
				}

				multipartemail.send();
			} else {
				logger.info("SENDING PLAIN EMAIL");
				// Create email message
				MultiPartEmail multipartemail = new MultiPartEmail();
				multipartemail.setHostName(emailHost);
				multipartemail.setSmtpPort(Integer.parseInt(emailPort));
				multipartemail.setDebug(false);
				multipartemail.setAuthenticator(new DefaultAuthenticator(emailSender, emailPass));
				// multipartemail.setSSLCheckServerIdentity(true);
				// multipartemail.setAuthentication(emailFrom, emailPass);
				if (sslState.equals("true")) {
					multipartemail.setSSLOnConnect(true);
				} else {
					multipartemail.setTLS(true);
				}
				multipartemail.addTo(recipient);
				multipartemail.setFrom(emailSender, emailUsername);
				multipartemail.setSubject(subject);
				multipartemail.setMsg(message);
				// multipartemail.setStartTLSEnabled(true);
				// multipartemail.setStartTLSRequired(true);

				if (!"".equals(emailattachment)) {
					// Create the attachment
					File file = new File(emailattachment);
					EmailAttachment attachment = new EmailAttachment();
					attachment.setPath(emailattachment);
					attachment.setDisposition(EmailAttachment.ATTACHMENT);
					attachment.setDescription(emailUsername);
					attachment.setName(file.getName());
					multipartemail.attach(attachment);

				}
				multipartemail.send();
			}
			return true;
		} catch (MalformedURLException | EmailException ex) {

			logger.info("Error at send email " + ex.getMessage());

			return false;

		}
	}
}
