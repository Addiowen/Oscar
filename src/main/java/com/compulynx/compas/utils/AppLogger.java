package com.compulynx.compas.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Ceasar on 21/05/2019.
 */
@Component
public class AppLogger {

	// appLogger.doLog(AppUtilities.LOG_FILE_DEBUG_FILE,
	// AppUtilities.logPreString(), "authenticationModeId: " +authenticationModeId);

	// String errorMessage = AppUtilities.logPreString() + AppUtilities.ERROR +
	// ex.getMessage()
//					+ AppUtilities.STACKTRACE + AppUtilities.getExceptionStacktrace(ex);
//			apiLogger.doLog(AppUtilities.LOG_FILE_EXCEPTIONS, errorMessage);

	private Gson gson = new Gson();
//	private Logger logger = LoggerFactory.getLogger(ApiLogger.class);
	protected static final Logger logger = Logger.getLogger("COMPASAPILOGGER");

	@Value("${iprint.logs.path}")
	private String logpath;

	// @Autowired
	// private JmsTemplate jmsTemplate;
	// @Autowired
	// private JmsTemplate jmsLoggingPersistentTemplate;

	private final String LOGS_DIRECTORY = "PBU_API";
	private final String LOG_ADAPTOR_REQUEST_QUEUE = "LOG_ADAPTOR_REQUEST_QUEUE";

	private String filename = null;
	private String msg = null;
	public HashMap<String, String> logmap;

	public AppLogger() {
	}

	public void logHashMapMessage(String filename, HashMap map, String preString) {
		this.filename = filename;
		logmap = new HashMap(map);

		if (logmap.containsKey("field2")) {
			String Pan = logmap.get("field2").toString();
			if (Pan.length() == 16) {
				String Maskedpart = Pan.substring(6, 12);
				Pan = Pan.replace(Maskedpart, "******");
				logmap.put("field2", Pan);
			} else if (Pan.length() == 19) {
				String Maskedpart = Pan.substring(6, 15);
				Pan = Pan.replace(Maskedpart, "******");
				logmap.put("field2", Pan);
			}
		}
		// check for field52 and field35 and remove them PCI-DSS
		if (logmap.containsKey("field52")) {
			logmap.remove("field52");
		}
		if (logmap.containsKey("field35")) {
			logmap.remove("field35");
		}

		this.msg = preString + " ::: " + logmap.toString();
	}

	public void _logExceptionError(String filename, String errorMessage) {
		HashMap<String, String> finalMessageMap = new HashMap<>();
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS");
		String timestamp = format.format(new Date());

		// String correlationID = AppUtilities.generateUUID();

		finalMessageMap.put("LOGS_DIRECTORY", LOGS_DIRECTORY);
		finalMessageMap.put("FILE_NAME", filename);
		finalMessageMap.put("TIME_STAMP", timestamp);
		finalMessageMap.put("LOG_MESSAGE", errorMessage + "\n");

		/*
		 * jmsTemplate.send(LOG_ADAPTOR_REQUEST_QUEUE, new MessageCreator() {
		 * 
		 * @Override public Message createMessage(Session session) throws JMSException {
		 * ObjectMessage objectMessage = session.createObjectMessage();
		 * objectMessage.setJMSCorrelationID("CorrelationID"+correlationID);
		 * objectMessage.setObject(finalMessageMap); return objectMessage; } });
		 */
	}

	public void _logPersistentExceptionError(String filename, String errorMessage) {
		HashMap<String, String> finalMessageMap = new HashMap<>();
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS");
		String timestamp = format.format(new Date());

		// String correlationID = AppUtilities.generateUUID();

		finalMessageMap.put("LOGS_DIRECTORY", LOGS_DIRECTORY);
		finalMessageMap.put("FILE_NAME", filename);
		finalMessageMap.put("TIME_STAMP", timestamp);
		finalMessageMap.put("LOG_MESSAGE", errorMessage + "\n");

		/*
		 * jmsLoggingPersistentTemplate.send(LOG_ADAPTOR_REQUEST_QUEUE, new
		 * MessageCreator() {
		 * 
		 * @Override public Message createMessage(Session session) throws JMSException {
		 * ObjectMessage objectMessage = session.createObjectMessage();
		 * objectMessage.setJMSCorrelationID("CorrelationID"+correlationID);
		 * objectMessage.setObject(finalMessageMap); return objectMessage; } });
		 */
	}

	public void doLog(String filename, String logMessage) {
		if (AppUtilities.LOG_FILE_DEBUG_FILE.equals(filename)) {
			if (AppUtilities.IS_APP_IN_DEBUG_MODE) {
				if (logMessage.contains("ImageData")) {// ||
					// logMessage.contains(AppUtilities.LOG_FILE_FETCH_USER_RESPONSE))
					// {
					// ACCT_INQUIRY - don't log the customer image chunk of data
					// AppUtilities.LOG_FILE_FETCH_USER_RESPONSE - has a big
					// chunk
					// of
					// data
					int len = logMessage.length() > 750 ? 750 : logMessage.length();
					logMessage = logMessage.substring(0, len);

				}
				/*
				 * logger.info("logpath=======================================") ;
				 * logger.info("Wrapper logpath: "+ logpath);
				 * logger.info("logpath=======================================") ;
				 */
				logMessageToFile(logMessage, logpath, filename, "info");
			} else {
				// DO NOTHING
			}
		} else {
			if (logMessage.contains("ImageData")) {// ||
													// logMessage.contains(AppUtilities.LOG_FILE_FETCH_USER_RESPONSE))
													// {
				// ACCT_INQUIRY - don't log the customer image chunk of data
				// AppUtilities.LOG_FILE_FETCH_USER_RESPONSE - has a big chunk
				// of
				// data
				int len = logMessage.length() > 750 ? 750 : logMessage.length();
				logMessage = logMessage.substring(0, len);

			}
			/*
			 * logger.info("logpath=======================================");
			 * logger.info("Wrapper logpath: "+ logpath);
			 * logger.info("logpath=======================================");
			 */
			logMessageToFile(logMessage, logpath, filename, "info");
		}
	}

	public void doLogCheckSensitiveData(String filename, String logPreString, JsonObject loggingObject) {

		JsonObject obj = new JsonObject();
		String logMessage = logPreString;
		for (Map.Entry<String, JsonElement> entry : loggingObject.entrySet()) {
			String key = entry.getKey();
			String value = loggingObject.get(entry.getKey()).getAsString();

			if ("cardPinBlock".equals(key) || "cardTrack2Data".equals(key) || "username".equals(key)
					|| "password".equals(key) || "agentPin".equals(key) || "token".equals(key) || "field35".equals(key)
					|| "field52".equals(key) || "field55".equals(key) || "fingerPrint_base64string".equals(key)) {
				value = "*****";
			} else if ("cardPAN".equals(key) || "field2".equals(key)) {
				if (value.length() >= 16) {
					String Maskedpart = value.substring(6, 12);
					value = value.replace(Maskedpart, "******");
				}
			} else if ("macAddress".equals(key)) {
				if (value.length() >= 16) {
					String Maskedpart = value.substring(6, 12);
					value = value.replace(Maskedpart, "******");
				}
			} else if ("account_from".equals(key) || "account_to".equals(key) || "account".equals(key)
					|| "field102".equals(key) || "field103".equals(key)) {
				// if (value.length() > 5) {
				// int startIndex = value.length() - 4;
				// value = "XXXXXXXXX" + value.substring(startIndex,
				// value.length());
				// }
			} else if ("cardField60".equals(key)) {
				if (value.length() >= 16) {
					String Maskedpart = value.substring(6, value.length());
					value = value.replace(Maskedpart, "******");
				}
			} else if ("cardSecurityInfo".equals(key)) {
				if (value.length() >= 16) {
					String Maskedpart = value.substring(6, value.length());
					value = value.replace(Maskedpart, "******");
				}
			} else if ("cardICCData".equals(key)) {
				if (value.length() >= 16) {
					String Maskedpart = value.substring(6, value.length());
					value = value.replace(Maskedpart, "******");
				}
			} else if ("cardTrack1Data".equals(key)) {
				if (value.length() >= 16) {
					String Maskedpart = value.substring(6, value.length());
					value = value.replace(Maskedpart, "******");
				}
			} else if ("cardExpiryDate".equals(key)) {
				if (value.length() >= 16) {
					String Maskedpart = value.substring(6, value.length());
					value = value.replace(Maskedpart, "******");
				}
			} else if ("cardPANSequence".equals(key)) {
				if (value.length() >= 16) {
					String Maskedpart = value.substring(6, value.length());
					value = value.replace(Maskedpart, "******");
				}
			} else if ("field22POSEntryMode".equals(key)) {
				if (value.length() >= 16) {
					String Maskedpart = value.substring(6, value.length());
					value = value.replace(Maskedpart, "******");
				}
			}

			// Other-Critical DATA
			if ("token".equals(key) || "username".equals(key) || "password".equals(key) || "agentPin".equals(key)) {

				if (AppUtilities.IS_APP_IN_DEBUG_MODE) {
					obj.addProperty(key, value);
				}
			} else {
				obj.addProperty(key, value);
			}
		}
		logMessage += gson.toJson(obj);

		/*
		 * logger.info("logpath=======================================");
		 * logger.info("Wrapper logpath: "+ logpath);
		 * logger.info("logpath=======================================");
		 */
		logMessageToFile(logMessage, logpath, filename, "info");
	}

	public <T> void doLog(String filename, String logPreString, T klazz) {
		HashMap<String, String> finalMessageMap = new HashMap<>();
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS");
		String timestamp = format.format(new Date());

		String logMessage = logPreString + maskSensitiveData(klazz);

		finalMessageMap.put("LOGS_DIRECTORY", LOGS_DIRECTORY);
		finalMessageMap.put("FILE_NAME", filename);
		finalMessageMap.put("TIME_STAMP", timestamp);
		finalMessageMap.put("LOG_MESSAGE", logMessage);

		logMessageToFile(logMessage, logpath, filename, "info");
	}

	public <T> String maskSensitiveData(T klazz) {
		Gson gson = new Gson();
		String response = "";
		T logKlazz = klazz;// Avoid - pass by value and pass by reference bull
							// shit
		response = gson.toJson(logKlazz);

		return response;
	}

	private String doMask(String field, int showFisrtN, int showLastN) {
		logger.info("field: " + field);
		String response;
		String mask = "****";
		if (field == null) {
			response = "";
		} else if (field.length() < showFisrtN) {
			response = field;
		} else if ((showFisrtN + showLastN) > field.length()) {
			response = field.substring(0, showFisrtN) + mask;
		} else {
			response = field.substring(0, showFisrtN) + mask + field.substring(field.length() - showLastN);
		}
		return response;
	}

	/**
	 * 
	 * @param messsage
	 * @param logpath
	 * @param fileName
	 * @param level
	 */
	public void logMessageToFile(String messsage, String logpath, String fileName, String level) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String daylog = format.format(new Date());

		String path = createDailyDirectory(logpath);
		String file = path + fileName + ".xml";

		FileHandler fh = null;
		try {
			if (fileName.equals(AppUtilities.LOG_FILE_POS_REQUEST)) {
				messsage = "INSTALLED_API_VERSION = " + AppUtilities.INSTALLED_API_VERSION
						+ "[[]] INSTALLED_API_VERSION_BUILD_DATE = " + AppUtilities.INSTALLED_API_VERSION_BUILD_DATE
						+ "[[]] " + messsage;
			}

			messsage = daylog + "::::" + messsage;
			// TODO Manage log file size
//			fh = new FileHandler(file, 500000000, 20, true);//500Mb
//			fh = new FileHandler(file, 25000000, 20, true);//25Mb
			fh = new FileHandler(file, 10000000, 100, true);// 5Mb
			fh.setFormatter(new SimpleFormatter());
			logger.addHandler(fh);
			// TODO Override Logger Parent Handlers
			logger.setUseParentHandlers(false);
			switch (level) {
			case "severe":
				logger.log(Level.SEVERE, messsage);
				break;
			case "warning":
				logger.log(Level.WARNING, messsage);
				break;
			case "info":
				logger.log(Level.INFO, messsage);
				break;
			case "config":
				logger.log(Level.CONFIG, messsage);
				break;
			case "fine":
				logger.log(Level.FINE, messsage);
				break;
			case "finer":
				logger.log(Level.FINER, messsage);
				break;
			case "finest":
				logger.log(Level.FINEST, messsage);
				break;
			default:
				logger.log(Level.FINE, messsage);
				break;
			}
		} catch (Exception ex) {
			logger.log(Level.SEVERE, null, ex);
		} finally {
			if (fh != null)
				fh.close();
		}
	}

	public String createDailyDirectory(String appdirectory) {
		String dailyDirecory = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String daylog = format.format(new Date());
			dailyDirecory = appdirectory + "/" + daylog + "/PBU_AgencyAPI";
			new File(dailyDirecory).mkdirs();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dailyDirecory + "/";
	}
}
