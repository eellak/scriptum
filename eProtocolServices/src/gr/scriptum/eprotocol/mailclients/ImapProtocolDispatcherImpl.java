package gr.scriptum.eprotocol.mailclients;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gr.scriptum.domain.Contact;
import gr.scriptum.domain.ProjectTask;
import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolDocument;
import gr.scriptum.domain.ProtocolNode.Direction;
import gr.scriptum.domain.ScriptumDocument;
import gr.scriptum.domain.TaskDocument;

class YourAuthenticator extends Authenticator {
	private String userName = null;
	private String password = null;

	public YourAuthenticator(String user, String passwd) {
		userName = user;
		password = passwd;
	}

	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}

/**
 * Mail Dispatcher. Handles sending and receiving Emails. Can cope with
 * protocols IMAP, POP3, IMAPS for Google.
 * 
 * @author Mike Mountrakis mountrakis@uit.gr
 * 
 */

public class ImapProtocolDispatcherImpl implements MailProtocolDispatcher {
	public static Log log = LogFactory.getLog(ImapProtocolDispatcherImpl.class);

	private static boolean Debug = false;

	MailDispatcherConfig config;

	// ------------------------------
	// Constructors
	// ------------------------------
	@SuppressWarnings("unused")
	private ImapProtocolDispatcherImpl() {
	}

	public ImapProtocolDispatcherImpl(MailDispatcherConfig config) {
		this.config = config;
		Debug = config.getDebug();
		this.config.init();
	}

	// ------------------------------
	// Public incoming
	// ------------------------------

	/**
	 * Contacts IMAP/POP3/IMAPS server <br>
	 * Authenticates <br>
	 * Retrieves incoming mail <br>
	 * Parses incoming mail <br>
	 * Creates Incoming Protoccols <br>
	 * Returns an array of InconingProtocols
	 */
	public synchronized Protocol[] receiveProtocols()
			throws MessagingException {

		// Get a session. Use a blank Properties object.
		Session session = Session.getInstance(new Properties());

		// Get a Store object from sessiοn
		Store store = session.getStore(config.getServerType());

		session.setDebug(config.getDebug());
		// connect
		store.connect(config.getSmtpHost(), config.getSmtpUser(),
				config.getSmtpPassword());
		debug("Connected.");

		// Get "INBOX"
		Folder inboxFolder = store.getFolder(config.getFolder());
		debug("got folder [" + config.getFolder() + "]");

		inboxFolder.open(Folder.READ_WRITE);
		if (inboxFolder.isOpen())
			debug("Folder [" + config.getFolder() + "] opened RW");

		Flags folderFLags = inboxFolder.getPermanentFlags();
		if (folderFLags != null) {
			if (inboxFolder.getPermanentFlags().contains(Flags.Flag.USER))
				debug("Folder [" + config.getFolder()
						+ "] Allows USER defined flags.");
			else
				debug("Folder [" + config.getFolder()
						+ "] NOT Allows USER defined flags.");
		}

		debug("Found in Inbox : " + inboxFolder.getMessageCount() + " messages");

		// Apply search criteria only for those messages UNSEEN
		Flag flagSeen = Flag.FLAGGED;
		Flags flags = new Flags();
		flags.add(flagSeen);
		FlagTerm onlyThoseUnseen = new FlagTerm(flags, false);
		Message[] emails = inboxFolder.search((SearchTerm) onlyThoseUnseen);
		// Use the following two lines for tests only.
		// Message [] emails = inboxFolder.getMessages(1,mailsToGet);
		// Message [] emails = inboxFolder.getMessages();
		debug("Unseen         : " + emails.length + " messages");

		// Decide to work only for a maxEmails number of messages each time
		int emailsToProcess = emails.length;
		if (emails.length > config.getMaxEmails())
			emailsToProcess = config.getMaxEmails();

		debug("Fetched        : " + emailsToProcess + " messages");

		Protocol[] incomingProtocols = new Protocol[emailsToProcess];
		for (int i = 0; i < emailsToProcess; i++) {
			try {
				incomingProtocols[i] = parseProtocolFromEmail(emails[i]);
				if (config.isDeleteOriginals())
					emails[i].setFlag(Flags.Flag.DELETED, true);

				if (emails[i].isSet(Flags.Flag.FLAGGED))
					debug("Mail " + i + " isflagged");
				emails[i].setFlag(Flags.Flag.FLAGGED, true);
				// emails[i].saveChanges(); ///BEWARE this line results in
				// exception
				debug("Message set " + i + " flagged. -> TRUE");
			} catch (Exception e) {
				// one prοblematic email will always been unseen
				// until someone manually makes it an incoming protocol.
				e.printStackTrace();
			}
		}

		inboxFolder.close(true);
		store.close();

		return incomingProtocols;

	}

	// -----------------------------
	// Private Helpers regarding Incoming Protocol Messages
	// -----------------------------

	private Protocol parseProtocolFromEmail(Message message)
			throws MessagingException, IOException {
		Protocol newIncoming = incomingNodeFromMessage(message);
		ArrayList<ProtocolDocument> documentList = new ArrayList<ProtocolDocument>();
		parseMail(message, documentList);
		Set<ProtocolDocument> set = new HashSet<ProtocolDocument>();
		for (ProtocolDocument doc : documentList)
			set.add(doc);
		newIncoming.setProtocolDocuments(set);
		// debugIncomingNode(newIncoming);

		return newIncoming;
	}

	private Protocol incomingNodeFromMessage(Message message)
			throws MessagingException, IOException {

		Protocol newProtocolNode = new Protocol(Direction.INCOMING);

		// Define the sender Contact for this incoming node
		InternetAddress[] from = (InternetAddress[]) message.getFrom();
		Contact fromContact = new Contact();
		fromContact.setEmail(from[0].getAddress());
		fromContact.setName(from[0].getPersonal());
		fromContact.setCreateDt(new Date());
		newProtocolNode.setSender(fromContact); 

		// Incoming protocol Date
		newProtocolNode.setIncomingDate(message.getSentDate());
		// subject
		newProtocolNode.setSubject(message.getSubject());

		// Recursively parse mail attachments. multiparts...
		ArrayList<ProtocolDocument> documentList = new ArrayList<ProtocolDocument>();
		parseMail(message, documentList);

		// transform the ArrayList to Set
		Set<ProtocolDocument> set = new HashSet<ProtocolDocument>();
		for (ProtocolDocument doc : documentList)
			set.add(doc);
		newProtocolNode.setProtocolDocuments(set);

		// debugIncomingNode(newProtocolNode);

		return newProtocolNode;
	}

	private void addDocumentFromString(String filenamePrefix, String content,
			ArrayList<ProtocolDocument> list) {
		int documentIdx = list.size() + 1;

		ProtocolDocument doc = new ProtocolDocument();
		doc.setDocumentName(filenamePrefix + documentIdx);
		doc.setDocumentNumber(new Integer(documentIdx));
		doc.setContent(content.getBytes());
		doc.setDocumentSize((long) content.getBytes().length);

		if (doc.getDocumentName() == null)
			doc.setDocumentName("UNAMED");

		list.add(doc);
	}

	private void addDocumentFromAttachment(Part content,
			ArrayList<ProtocolDocument> list) throws MessagingException,
			IOException {

		int documentIdx = list.size() + 1;

		ProtocolDocument doc = new ProtocolDocument();

		doc.setDocumentName(content.getFileName());
		doc.setDocumentNumber(documentIdx);

		OutputStream ost = new ByteArrayOutputStream();
		content.getDataHandler().writeTo(ost);
		doc.setContent(ost.toString().getBytes());
		doc.setDocumentSize((long) doc.getContent().length);
		// debug("Wrote " + doc.getContent().length + " bytes ");
		ost.close();

		if (doc.getDocumentName() == null)
			doc.setDocumentName("UNAMED");

		list.add(doc);
	}

	private void parseMail(Part message, ArrayList<ProtocolDocument> list)
			throws MessagingException, IOException {

		if ((message.isMimeType("text/*"))
				&& (message.getDisposition() != null && message
						.getDisposition().equals(Part.INLINE))) {

			// debug("Text part: Inline text/*");
			// debug((String) message.getContent());
			addDocumentFromString("TEXT_", (String) message.getContent(), list);

		} else if (message.isMimeType("multipart/alternative")) {

			Multipart mp = (Multipart) message.getContent();
			int partsCount = mp.getCount();
			// TAKE ONLY THE BEST PART
			// debug("Text part: multipart/alternative----");
			// debug((String) mp.getBodyPart(partsCount - 1).getContent());
			addDocumentFromString("MULTIPART_",
					(String) mp.getBodyPart(partsCount - 1).getContent(), list);

		} else if (message.isMimeType("multipart/*")) {

			Multipart mPart = (Multipart) message.getContent();
			int partCount = mPart.getCount();
			for (int i = 0; i < partCount; i++) {
				// PARSEING RECURSIVELY
				parseMail(mPart.getBodyPart(i), list);
			}

		} else if (message.isMimeType("message/*")) {
			try {
				Part part = (Message) message.getContent();
				// PARSEING RECURSIVELY
				parseMail(part, list);
			} catch (java.lang.SecurityException ex) {
				try {
					Part part = (MimeMessage) message.getContent();
					// PARSING RECURSIVELY
					parseMail(part, list);
				} catch (Exception ex1) {
					log.warn("parseMail(): " + ex1.getMessage());
				}
			}
		} else {
			// THIS IS AN ATTACHMENT
			// WRITE CODE TO HANDLE ATTACHMENT
			// debug("Attachment----");
			// debug("Name " + message.getFileName() );
			// debug("Size " + message.getSize() );
			// debug("Contents " + message.getContent().toString().getBytes() );
			// debug("Type " + message.getContentType());
			// debug("--------------");
			addDocumentFromAttachment(message, list);
		}
	}

	// ------------------------------
	// Debugging Routines
	// ------------------------------

	private static void debug(String s) {
		if (Debug)
			System.out.println(s);
	}

	private static void debugDocument(ProtocolDocument doc) {

		String content = new String(doc.getContent());
		if (content.length() > 20)
			content = content.substring(0, 20);

		debug("\t# " + doc.getDocumentNumber() + "\tName ["
				+ doc.getDocumentName() + "]\t\tSize " + doc.getDocumentSize()
				+ "\t\t[" + content + "]");
	}

	private static void debugIncomingNode(Protocol inode) {
		debug("\n-------------->New Incoming node" + "Date "
				+ inode.getIncomingDate() + "\tSubject " + inode.getSubject()
				+ "\tFrom " + inode.getSender().getContact().getEmail());
		debug("Documents:");
		Set<ProtocolDocument> documents = inode.getProtocolDocuments();
		for (ProtocolDocument doc : documents)
			debugDocument(doc);
		debug("-------------->Incoming protocol finished.");
	}

	// ----------------------------
	// Test Drive for receiving mails from gmail
	// ----------------------------

	// ----------------------------
	// Test Drive for sending emails from illumine
	// ----------------------------

	// ------------------------------
	// Public Outgoing
	// ------------------------------
	/**
	 * Check the library's specification.
	 * http://java.sun.com/products/javamail/javadocs
	 * /com/sun/mail/smtp/package-summary.html
	 * 
	 * See a simple SEND example
	 * http://www.java-tips.org/index.php?option=com_content
	 * &task=view&sectionid=&id=616&Itemid=70
	 **/

	public SendMailReceipt sendOutgoingProtocol(Protocol outProtocol) {

		SendMailReceipt mailReceipt = new SendMailReceipt(outProtocol);

		// Add recipients
		//TODO: Fill in with ProtocolCorrespondent instances
		List<String> addressesTo = new ArrayList<String>();
		List<String> addressesCc = new ArrayList<String>();
		String[] addressesToArray = addressesTo.toArray(new String[addressesTo
				.size()]);
		String[] addressesCcArray = addressesCc.toArray(new String[addressesCc
				.size()]);

		// Attachments: create temporary files
		Set<ProtocolDocument> documents = outProtocol.getProtocolDocuments();
		File[] attachements = new File[documents.size()];
		int i = 0;
		for (ScriptumDocument document : documents) {
			attachements[i++] = getFileFromBytes(document.getContent(),
					document.getDocumentName());
		}

		// send the email
		mailReceipt = sendOutgoingMail(addressesToArray, addressesCcArray,
				config.getMessageFrom(), outProtocol.getSubject(),
				config.getMessageBodyTxt(outProtocol.getProtocolNumber()),
				attachements);

		// delete all temporary files created during the operation
		for (int j = 0; i < attachements.length; j++) {
			try {
				if (attachements[j] != null)
					attachements[j].delete();
			} catch (Exception e) {
				log.warn("Could not delete temporary attachment ("
						+ attachements[j].getName() + " reason " + e.toString());
			}
		}

		return mailReceipt;
	}

	/**
	 * Send a mail when task state changes
	 * 
	 * @param outTask
	 *            The task this mail concerns about....
	 * @return the SendMailReceipt
	 */
	public SendMailReceipt sendOutgoingTask(ProjectTask outTask) {
		SendMailReceipt mailReceipt = new SendMailReceipt(outTask);

		String[] addressesToArray = mailReceipt.getSentTo();
		String[] addressesCcArray = mailReceipt.getSentCc();

		// Attachments: create temporary files
		Set<TaskDocument> documents = outTask.getTaskDocuments();
		File[] attachements = null; // do not send document content
		// File[] attachements = new File[documents.size()];
		// int i = 0;
		// for (ScriptumDocument document : documents) {
		// attachements[i++] = getFileFromBytes(document.getContent(),
		// document.getDocumentName());
		// }
		// send the email
		mailReceipt = sendOutgoingMail(addressesToArray, addressesCcArray,
				addressesToArray[0], outTask.getName(),
				outTask.getDescription(), attachements);

		// delete all temporary files created during the operation
		if (attachements != null) {
			for (int j = 0; j < attachements.length; j++) {
				try {
					if (attachements[j] != null)
						attachements[j].delete();
				} catch (Exception e) {
					log.warn("Could not delete temporary attachment ("
							+ attachements[j].getName() + " reason "
							+ e.toString());
				}
			}
		}
		return mailReceipt;
	}

	/**
	 * Simple Send Mail Method vi SMTP protocol
	 * 
	 * @param to
	 * @param cc
	 * @param from
	 * @param subject
	 * @param text
	 * @param attachements
	 * @return
	 */

	public SendMailReceipt sendOutgoingMail(String[] to, String[] cc,
			String from, String subject, String text, File[] attachements) {

		SendMailReceipt receipt = new SendMailReceipt();
		Transport transport = null;

		try {

			Properties props = new Properties();
			props.setProperty("mail.smtp.host", config.getSmtpHost());
			props.setProperty("mail.smtp.port", "" + config.getSmtpPort());
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.user", config.getSmtpUser());
			props.setProperty("mail.password", config.getSmtpPassword());
			if (config.getEnableStarttls())
				props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");
			Authenticator auth = new YourAuthenticator(config.getSmtpUser(),
					config.getSmtpPassword());
			Session mailSession = Session.getInstance(props, auth);

			// Session mailSession = Session.getDefaultInstance(props, null);
			System.out.println("sendOutgoingMail()--> Session properties set.");
			mailSession.setDebug(config.getDebug());
			transport = mailSession.getTransport();
			System.out.println("sendOutgoingMail()--> Got TRANSPORT Level.");

			MimeMessage msg1 = new MimeMessage(mailSession);
			msg1.setFrom(new InternetAddress(from));

			msg1.setSentDate(new Date());

			InternetAddress[] toAddresses = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++) {
				try {
					toAddresses[i] = new InternetAddress(to[i]);
				} catch (Exception e) {
					log.warn("Problem with TO mail address : " + e.getMessage());
				}
			}
			msg1.setRecipients(Message.RecipientType.TO, toAddresses);
			System.out
					.println("sendOutgoingMail()--> Message TO Addresses set.");

			
			if (cc != null) {
				InternetAddress[] ccAddresses = new InternetAddress[cc.length];
				for (int i = 0; i < cc.length; i++) {
					try {
						ccAddresses[i] = new InternetAddress(cc[i]);
					} catch (Exception e) {
						log.warn("Problem with CC mail address : "
								+ e.getMessage());
					}
				}
				msg1.setRecipients(Message.RecipientType.CC,
						ccAddresses);
				System.out
				.println("sendOutgoingMail()--> Message CC Addresses set.");
			}

			msg1.setSubject(subject);

			// create the Multipart and add its parts to it
			Multipart multiPart = new MimeMultipart();

			// create and fill the first message part
			MimeBodyPart mainPart = new MimeBodyPart();
			mainPart.setText(text);
			multiPart.addBodyPart(mainPart);

			if (attachements != null) {
				for (int i = 0; i < attachements.length; i++) {
					if (attachements[i] != null) {
						FileDataSource fds = new FileDataSource(attachements[i]);
						MimeBodyPart attachmentPart = new MimeBodyPart();
						attachmentPart.setDataHandler(new DataHandler(fds));
						attachmentPart.setFileName(fds.getName());
						multiPart.addBodyPart(attachmentPart);
					}
				}
				System.out
						.println("sendOutgoingMail()--> Message Attachements finished for all "
								+ attachements.length + " files.");
			}
			// add the Multipart to the message
			msg1.setContent(multiPart);

			// ---------
			transport.connect();
			System.out.println("sendOutgoingMail()--> Connected.");
			transport.sendMessage(msg1,
					msg1.getRecipients(Message.RecipientType.TO));
			transport.sendMessage(msg1,
					msg1.getRecipients(Message.RecipientType.CC));
			transport.close();
			System.out.println("sendOutgoingMail()--> Closed connection.");

			log.info("Message was sent");
			receipt.setSentTimestamp(new Date());

		} catch (Exception mex) {
			log.fatal("Message was NOT sent. Reason : " + mex.getMessage());
			mex.printStackTrace();

			receipt.seteMessage(mex.getMessage());
			receipt.seteCode("ERROR");
		} finally {
			// avoid connection leak
			if (transport != null) {
				if (transport.isConnected()) {
					try {
						transport.close();
						System.out
								.println("sendOutgoingMail()--> Closed connection after error.");
					} catch (MessagingException e) {
						log.warn("transport not closed. " + e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}

		return receipt;

	}

	/**
	 * Send all Outgoing Protocols to the SMTP mail server
	 */
	public SendMailReceipt[] sendAllOutgoingProtocols(
			Protocol[] outProtocol) {

		ArrayList<SendMailReceipt> receipts = new ArrayList<SendMailReceipt>();

		for (int i = 0; i < outProtocol.length; i++) {
			SendMailReceipt receipt = sendOutgoingProtocol(outProtocol[i]);
			receipts.add(receipt);
		}
		SendMailReceipt toReturn[] = new SendMailReceipt[receipts.size()];
		return receipts.toArray(toReturn);
	}

	// -----------------------
	// helpers
	// -----------------------

	// not used but keep it for reference
	private static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		byte[] bytes = null;

		try {
			// Get the size of the file
			long length = file.length();

			if (length > Integer.MAX_VALUE) {
				// File is too large
			}

			// Create the byte array to hold the data
			bytes = new byte[(int) length];

			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			// Ensure all the bytes have been read in
			if (offset < bytes.length) {
				throw new IOException("Could not completely read file "
						+ file.getName());
			}

			// Close the input stream and return bytes

		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					;
				}
			}
		}

		return bytes;
	}

	private static File getFileFromBytes(byte[] byteContent, String fileName) {
		File temp = null;
		BufferedWriter out = null;
		FileWriter out2 = null;
		try {
			// Create the file.
			temp = new File(fileName);
			// Write to the file
			out2 = new FileWriter(temp);
			out = new BufferedWriter(out2);
			out.write(new String(byteContent));
			out.flush();
			out.close();
			out2.flush();
			out2.close();
			return temp;
		} catch (Exception e) {
			log.error(e);
			log.error(e.getMessage());
		}finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception w) {
					log.error(w);
				}
			}
			if (out2 != null) {
				try {
					out2.close();
				} catch (Exception w) {
					log.error(w);
				}
			}
		}
		return temp;
	}

	// Example how to send attachments.
	private static void sendEmailWithAttachmentExample() {
		try {
			// Works
			Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.host", "mail.illumine.gr");
			props.setProperty("mail.user", "mountrakis@illumine.gr");
			props.setProperty("mail.password", "o gnwstos");

			Session mailSession = Session.getDefaultInstance(props, null);
			Transport transport = mailSession.getTransport();

			MimeMessage message = new MimeMessage(mailSession);
			message.setSubject("Testing javamail plain");
			message.setContent("This is a test", "text/plain");
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					"mountrakis@uit.gr"));
			message.setFrom(new InternetAddress("mountrakis@illumine.gr"));

			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText("This is the text of a Test email");

			byte[] content = { 'h', 'e', 'l', 'l', 'o', ' ', '"', 'w', 'o',
					'r', 'l', 'd', '!' };
			File file = getFileFromBytes(content, "mytest.txt");

			MimeBodyPart mbp2 = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(file);
			mbp2.setDataHandler(new DataHandler(fds));
			mbp2.setFileName(fds.getName());

			// create the Multipart and add its parts to it
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(mbp2);

			// add the Multipart to the message
			message.setContent(mp);
			transport.connect();
			transport.sendMessage(message,
					message.getRecipients(Message.RecipientType.TO));
			transport.close();

			log.info("Message was sent");

		} catch (Exception mex) {
			mex.printStackTrace();

		}
	}

}
