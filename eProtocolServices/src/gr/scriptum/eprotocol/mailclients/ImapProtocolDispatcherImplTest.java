package gr.scriptum.eprotocol.mailclients;

import gr.scriptum.domain.Protocol;

import java.io.File;

/**
 * Test Drive class
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */
public class ImapProtocolDispatcherImplTest {

	public static void main(String[] argv) {
		
		//works
		sendEmailFromGMail();
	
		//works
		//receiveMailFromGMail();
		
		//works
		//receiveMailFromIllumine();
		
		//works
		//sendEmailFromIllumine();
		
	}

	// ----------------------------
	// Test Drive for sending emails from GMAIL
	// ----------------------------
	public static void sendEmailFromGMail() {
		MailDispatcherConfig config = new MailDispatcherConfig();
		config.setSmtpHost("smtp.gmail.com");
		config.setSmtpUser("mike.mountrakis@gmail.com");
		config.setSmtpPort(587);
		config.setSmtpPassword("------");
		config.setEnableStarttls(true);
		config.setMessageFrom("mike.mountrakis@gmail.com");
		config.setMessageBodyTxt("This is a message from the electronic protocol.");
		config.setDebug(false);

		try {
			debug("STARTED");
			ImapProtocolDispatcherImpl disp = new ImapProtocolDispatcherImpl(config);

			File[] attachements = new File[2];
			attachements[0] = new File("C:\\hello.pdf");
			attachements[1] = new File("C:\\hello1.pdf");

			String[] to = new String[1];
			to[0] = "mountrakis@uit.gr";

			String[] cc = new String[1];
			cc[0] = "angelos@uit.gr";

			String subject = "Test";

			String text = "Apanta an sou irthe to Test";

			String from = config.getMessageFrom();
			SendMailReceipt rec = disp.sendOutgoingMail(to, cc, from, subject,text, attachements);

			debug("FINISHED");
			debug("Receipt " + rec.getSentTimestamp());

		} catch (Exception e) {
			e.printStackTrace();
			debug(e.toString());
		}
	}

	
	// ----------------------------
	// Test Drive for Receive emails from ILLUMINE
	// ----------------------------	
	public static void receiveMailFromIllumine() {
		MailDispatcherConfig config = new MailDispatcherConfig();
		config.setSmtpHost("mail.illumine.gr");
		//config.setSmtpPort(993);
		config.setSmtpUser("mountrakis@illumine.gr");
		config.setSmtpPassword("siga mi sou pw");
		config.setIMAP(); // works
		config.setDebug(true);
		System.out.println(config.toString());

		try {
			debug("STARTED");
			ImapProtocolDispatcherImpl disp = new ImapProtocolDispatcherImpl(
					config);

			Protocol[] receiveProtocols = disp
					.receiveProtocols();
			for (int i = 0; i < receiveProtocols.length; i++) {
				Protocol ip = receiveProtocols[i];
				System.out.println(ip.getSubject());
			}

			debug("FINISHED");
			debug("Received : " + receiveProtocols.length);

		} catch (Exception e) {
			e.printStackTrace();
			debug(e.toString());
		}
		
	}

	// ----------------------------
	// Test Drive for Receive emails from GMAIL
	// ----------------------------
	public static void receiveMailFromGMail() {
		MailDispatcherConfig config = new MailDispatcherConfig();
		config.setSmtpHost("imap.gmail.com");
		config.setSmtpPort(993);
		config.setSmtpUser("mike.mountrakis@gmail.com");
		config.setSmtpPassword("siga mi sou pw");
		config.setIMAPS(); // works
		config.setDebug(true);
		System.out.println(config.toString());

		try {
			debug("STARTED");
			ImapProtocolDispatcherImpl disp = new ImapProtocolDispatcherImpl(
					config);

			Protocol[] receiveProtocols = disp
					.receiveProtocols();
			for (int i = 0; i < receiveProtocols.length; i++) {
				Protocol ip = receiveProtocols[i];
				System.out.println(ip.getSubject());
			}

			debug("FINISHED");
			debug("Received : " + receiveProtocols.length);

		} catch (Exception e) {
			e.printStackTrace();
			debug(e.toString());
		}
	}

	// ----------------------------
	// Test Drive for sending emails from ILLUMINE
	// ----------------------------	
	public static void sendEmailFromIllumine() {
		MailDispatcherConfig config = new MailDispatcherConfig();
		config.setSmtpHost("mail.illumine.gr");
		config.setSmtpUser("mountrakis@illumine.gr");
		config.setSmtpPassword("siga mi sou pw");
		config.setMessageFrom("mountrakis@illumine.gr");
		config.setMessageBodyTxt("This is a message from the electronic protocol.");
		config.setDebug(true);

		try {
			debug("STARTED");
			ImapProtocolDispatcherImpl disp = new ImapProtocolDispatcherImpl(
					config);

			File[] attachements = new File[2];
			attachements[0] = new File("C:\\hello.pdf");
			attachements[1] = new File("C:\\hello1.pdf");

			String[] to = { "mountrakis@uit.gr" };

			String[] cc = { "angelos@uit.gr" };

			String subject = "Test";

			String text = "Apanta an sou irthe to Test";

			String from = config.getMessageFrom();
			SendMailReceipt rec = disp.sendOutgoingMail(to, cc, from, subject,
					text, attachements);

			debug("FINISHED");
			debug("Receipt " + rec.getSentTimestamp());

		} catch (Exception e) {
			e.printStackTrace();
			debug(e.toString());
		}
	}	
	
	
	
	
	
	
	
	
	
	public static void debug(String s) {
		System.out.println(s);
	}

}
