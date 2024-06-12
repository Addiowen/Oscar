package com.compulynx.compas.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Ceasar on 06/05/2019.
 */
public class AppUtilities {   
	
	public static String INSTALLED_API_VERSION = "v1.0.2.9";
	public static String INSTALLED_API_VERSION_BUILD_DATE = "2021_11_16_1600";
	
	
	public static final boolean IS_APP_IN_DEBUG_MODE = true;
	public static final boolean IS_APP_RUNNING_IN_LOCAL_PC = true;
	//
	public static final boolean SHOULD_FORCE_MANUAL_REVERSAL = false;
	public static final boolean SHOULD_BYPASS_OTP_SMS = true;

	
	
	public static final String CHARGE_BURDEN_1_CUSTOMER = "1 - CUSTOMER";
	public static final String CHARGE_BURDEN_2_BANK = "2 - BANK";


	public static final String TRN_AMOUNT_LIMIT_STATUS_SUCCESS = "TRN_AMOUNT_LIMIT_SUCCESS";

	public static final String PASSWORD_STRENGTH_SUCCESS = "PWD_STRENGTH_SUCCESS";
	public static final String PASSWORD_ALLOWED_SPECIAL_CHARACTERS = "~!@#$%^&*()_-";

	public static final String BASE_API_PATH = "/api";
	public static final String BASE_DEBUG_API_PATH = "/debug";

	//
	public static final String LOG_FILE_DEBUG_FILE = "DEBUG_FILE";
	public static final String LOG_FILE_PBU_MIDDLEWARE_RREQUEST = "PBU_MiddlewareRequest";
	public static final String LOG_FILE_PBU_MIDDLEWARE_RESPONSE = "PBU_MiddlewareResponse";
	public static final String LOG_FILE_POS_DEVICE_CONFIG_RESPONSE = "DEVICE_CONFIG_RESPONSE";
	public static final String LOG_FILE_POS_DEVICE_LICENSE_CONFIG_RESPONSE = "DEVICE_LICENSE_CONFIG_RESPONSE";
	public static final String LOG_FILE_SEND_NOTIFICATION = "SEND_NOTIFICATION";
	public static final String LOG_FILE_AFIS_REQUEST = "AfisRequest";
	public static final String LOG_FILE_AFIS_RESPONSE = "AfisResponse";

	public static final String LOG_FILE_EXCEPTIONS = "Exceptions";
	public static final String LOG_FILE_PREFERRED_TARIFFS = "PreferredTariffs";
	public static final String LOG_FILE_SAVED_TRANSACTIONS = "SAVED_TRANSACTIONS";
	public static final String LOG_FILE_TRN_CHARGES = "Trn_Charges";
	public static final String LOG_FILE_TEST_LOGGING = "Test_Logging";
	public static final String LOG_FILE_FETCH_USER_RESPONSE = "FetchUserResponse";
	public static final String LOG_FILE_PAYBILL_UMEME_VALIDATE = "PAYBILL_UMEME_VALIDATE";
	public static final String LOG_FILE_PAYBILL_UMEME_PAYMENT = "PAYBILL_UMEME_PAYMENT";
	public static final String LOG_FILE_POS_REQUEST = "PosRequest";
	public static final String LOG_FILE_POS_RESPONSE = "PosResponse";
	public static final String LOG_FILE_TRN_TARIFFS = "TRN_TARIFFS";
	public static final String LOG_FILE_SWITCH_RESPONSE = "SWITCH_RESPONSE";
	public static final String LOG_FILE_DUPLICATE_REQUESTS = "DUPLICATE_REQUESTS";
	public static final String LOG_FILE_ProcessDRandCR = "ProcessDRandCR_Response";
	public static final String LOG_FILE_CBS_REVERSALS = "CBS_REVERSALS";
	public static final String LOG_FILE_SWITCH_PING_REQUESTS = "SWITCH_PING_REQUESTS";
	public static final String LOG_FILE_SWITCH_PING_EXCEPTIONS = "SWITCH_PING_EXCEPTIONS";
	public static final String LOG_FILE_SWITCH_REQUESTS = "SWITCH_REQUESTS";
	public static final String LOG_FILE_FULL_STATEMENT_EMAIL = "FullStatementEmail";
	public static final String LOGGEDIN_AGENT_NAME = "LOGGEDIN_AGENT_NAME";
	public static final String LOG_FILE_SWITCH_REVERSALS = "SWITCH_REVERSALS";
	public static final String LOG_FILE_LOGIN_RESPONSE = "LOGIN_Response";
	public static final String LOG_FILE_LOGIN_REQUEST = "LOGIN_Request";

	//
	public static final String UNTRUSTED_CLIENT = "UNTRUSTED_CLIENT";
	public static final String ERROR = " \nERROR: ";
	public static final String STACKTRACE = " \nSTACKTRACE: ";
	public static final String DUPLICATE_TRANSACTION_REQUEST = "Duplicate transaction. Please check your last transaction status and reprint receipt before posting another transaction. You can send a similar transaction after one minute.";
	public static final String IS_DUPLICATE_TRN_REQUEST_CHECK_OK = "IS_DUPLICATE_TRN_REQUEST_CHECK_OK";
	public static final String DUPLICATE_TRANSACTION_REQUEST_CHECK_MSG = "DUPLICATE_TRANSACTION_REQUEST_CHECK_MSG";

	public static final String LOG_FILE_CARD_TRN_CHARGE_REQ_RESPONSE = "CARD_TRN_CHARGE_REQ_RESPONSE";

	public static final String ACCOUNT_LOOK_UP_CUSTOMER = "CUSTOMER";
	public static final String ACCOUNT_LOOK_UP_BENEFICIARY = "BENEFICIARY";
	public static final String TRANSACTION_TOTAL_CHARGE = "total_charge";
	public static final String TRANSACTION_CHARGE_DB_AMOUNT = "charge_amount";
	public static final String LOAN_APPLICATION_TYPE_NEW_LOAN_APPLICATION = "NewLoanApplication";
	public static final String ATM_CARD_APPLICATION_TYPE_NEW_CARD_APPLICATION = "NewCardApplication";
	public static final String RECEIPT_NUMBER = "REQUEST_RECEIPT_NUMBER";
	public static final String AGENT_CODE = "REQUEST_AGENT_CODE";
	//
	public static final String UTILITY_CUSTOMER_VALIDATION_STEP_RESPONSE_REQUEST_ID = "utility_customer_validation_step_response_request_id";
	// "SchemeCode": "MPOS",
	public static final String AGENT_ACCOUNT_SCHEME_CODE = "MPOS";
	public static final String BANK2WALLET_FULLFILLMENT_RESPONSE = "BANK2WALLET_FULLFILLMENT_RESPONSE";


	/**
	 * Log pre string.
	 *
	 * @return the string
	 */
	public static String logPreString() {
		return " I&M Bank API | " + Thread.currentThread().getStackTrace()[2].getClassName() + " | "
				+ Thread.currentThread().getStackTrace()[2].getLineNumber() + " | "
				+ Thread.currentThread().getStackTrace()[2].getMethodName() + "() | ";
	}

	public static String generateUUID() {
		String randomUUIDString = "";
		try {
			MessageDigest salt = MessageDigest.getInstance("SHA-256");
			salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
			randomUUIDString = Bytes2HexString(salt.digest());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());

			randomUUIDString = UUID.randomUUID().toString().replace("-", "");
		}
		return randomUUIDString;
	}

	/**
	 * Get exception string stack trace
	 *
	 * @param ex
	 * @return
	 */
	public static String getExceptionStacktrace(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}

	public String anyDate(String format) {
		try {
			if ("".equals(format)) {
				format = "yyyy-MM-dd HH:mm:ss"; // default
			}
			java.util.Date today;
			SimpleDateFormat formatter;

			formatter = new SimpleDateFormat(format);
			today = new java.util.Date();
			return (formatter.format(today));
		} catch (Exception ex) {
			System.out.println(" \n**** anyDate ****\n" + ex.getMessage());
		}
		return "";
	}

	public AppUtilities() {

	}

	public static void printHexString(String hint, byte[] b) {
		System.out.print(hint);
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			System.out.print(hex.toUpperCase() + " ");
		}
		System.out.println("");
	}

	public static String Bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}

	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	public static byte[] HexString2Bytes(String src) {
		byte[] ret = new byte[8];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < 8; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	public static BigDecimal round(BigDecimal d, int scale, boolean roundUp) {
		int mode = (roundUp) ? BigDecimal.ROUND_UP : BigDecimal.ROUND_DOWN;
		return d.setScale(scale, mode);
	}

	public String FormatAmountFromSwitch(String amount, int rootdivide) {
		if (amount.contains("C000")) {
			amount = amount.substring(4, amount.length());
		} else {
			amount = "-" + amount.substring(4, amount.length());
		}
		BigDecimal root = new BigDecimal(rootdivide);
		BigDecimal decimalamount = new BigDecimal(amount);
		decimalamount = decimalamount.divide(root);
		decimalamount = round(decimalamount, 2, true);
		return String.valueOf(decimalamount);
	}

	public String FormatAmountToSwitch(String amount, int rootmultiply) {
		BigDecimal root = new BigDecimal(rootmultiply);
		BigDecimal decimalamount = new BigDecimal(amount);
		decimalamount = round(decimalamount, 2, true);
		decimalamount = decimalamount.multiply(root);
		decimalamount = round(decimalamount, 0, true);
		return String.valueOf(decimalamount);
	}

	/**
	 * yyyy-MM-dd HH:mm:ss.SSS yyyy-MM-dd HH:mm:ss
	 * 
	 * yyyy-MM-dd HH:mm:ss YYMMdd yyyy-MM-dd HH:mm:ss md yyyy-MM-dd hh:mm:ss a
	 * 
	 * DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
	 * Timestamp date = new Timestamp(dateFormat.parse("04/04/2014 21:07:13.897"
	 * ).getTime());
	 * 
	 * // format variables into 0-filled strings String formattedDate =
	 * String.format("%011d", date.getTime() / 1000);
	 *
	 * "HH:mm:ss.SSSZ" "EEE, d MMM yyyy HH:mm:ss Z" "EEE, d MMM yyyy,HH:mm:ss"
	 *
	 * NB: CC is nothing but the first two digits of the year which mean the
	 * century
	 *
	 * @param format
	 * @return
	 */
	public static String genericDatetime(String format) {

		String date = "";
		try {
			ZonedDateTime zonedDateTime = ZonedDateTime.now();

			if (!format.trim().isEmpty()) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
				date = formatter.format(zonedDateTime);
			} else {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				date = formatter.format(zonedDateTime);
			}
		} catch (Exception ex) {
			System.out.println(getExceptionStacktrace(ex));
		}
		return date;
	}

	public static String formatAmountAddCommaSeparator(String amountToFormat) {
		DecimalFormat formatter = new DecimalFormat("#,###.##");
		return formatter.format(Double.parseDouble(amountToFormat));
	}

	public static java.sql.Date convertStringToJavaSqlDate(String dateToParse) {
		try {
			return (java.sql.Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(dateToParse);
		} catch (Exception ex) {
			System.out.println(" \n**** anyDate ****\n" + ex.getMessage());
		}
		return new java.sql.Date(Calendar.getInstance().getTime().getTime());

	}

	/**
	 * 
	 * @param agentCode
	 * @param macAddress
	 * @param agentID
	 * @return
	 */
	public static String generateAccessToken(String agentCode, String macAddress, Integer agentID) {
		return agentCode + "|" + macAddress + "|" + genericDatetime("yyyy-MM-dd HH:mm:ss") + "|" + agentID;
	}


	public static boolean shouldSendNotificationEmailOrNot(String timestamp, String currentTime) {
		boolean response = false;

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date start = df.parse(timestamp); // start date
			Date stop = df.parse(currentTime); // end date

			long milliseconds = stop.getTime() - start.getTime(); // time gap in
																	// mil-seconds

			int seconds = (int) (milliseconds / 1000) % 60;
			int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
			int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);

			// System.out.println("currentTime: "+currentTime);
			// System.out.println("tokenIssuedTimestamp:
			// "+tokenIssuedTimestamp);
			// System.out.println("hours: "+hours);
			// System.out.println("minutes: "+minutes);
			// System.out.println("seconds: "+seconds);

			// if (minutes > 10 || hours > 0 || minutes < 0 || hours < 0) {

			if (minutes > 30) {
				response = true;
			}

		} catch (Exception ex) {
			System.out.println(" \n**** anyDate ****\n" + ex.getMessage());
		}

		return response;
	}
	
	
	public static String subtractDaysFromCurrentDateReturnDate(int daysToSubtract) {
		String theDate = "";
		try {
		
			LocalDateTime currentDate = LocalDateTime.now().minusDays(daysToSubtract);
		theDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		} catch (Exception ex) {
			System.out.println(" \n**** Exception ****\n" + ex.getMessage());
		}
		return theDate;
		
	}

	public static void main(String[] args) {
		try {

			/*
			 * System.out.println(genericDatetime("HH:mm:ss").replaceAll(
			 * "[^0-9]", "")); System.out.println(genericDatetime("hhmmss"));//
			 * .replaceAll("[^0-9]", // "")); // MMDD
			 * System.out.println(genericDatetime("MMdd"));//
			 * .replaceAll("[^0-9]", // ""));
			 * 
			 * SimpleDateFormat format = new SimpleDateFormat(
			 * "yyyy-MM-dd HH:mm:ss"); Date date = format.parse(
			 * "2019-05-22 16:15:59.210"); long timestamp = date.getTime();
			 * System.out.println("timestamp: " + timestamp);
			 * 
			 * System.out.println("yyyyMMddHHmmssSSS: " +
			 * genericDatetime("yyyyMMddHHmmssSSS"));// .replaceAll("[^0-9]", //
			 * ""));
			 * 
			 * System.out.println("yyyyMMddHHmmss: " +
			 * genericDatetime("yyyyMMddHHmmss"));
			 * 
			 * System.out.println("yyyyMMdd: " + genericDatetime("yyyyMMdd"));
			 * 
			 * System.out.println("yyyy-MM-dd HH:mm:ss.SSS: " + genericDatetime(
			 * "yyyy-MM-dd HH:mm:ss.SSS"));
			 * 
			 * System.out.println("yyyy-MM-dd'T'HH:mm:ss: " +
			 * genericDatetime("yyyy-MM-dd'T'HH:mm:ss"));
			 * 
			 * System.out.println("[yyyy-MM-dd HH:mm:ss]: " + genericDatetime(
			 * "[yyyy-MM-dd HH:mm:ss]"));
			 * 
			 * System.out.println("yyyyMMdd_HHmmss: " +
			 * genericDatetime("yyyyMMdd_HHmmss")); System.out.println(
			 * "dd-MM-yyyy hh:mm a z: " + genericDatetime("dd-MM-yyyy hh:mm a z"
			 * )); System.out.println("yyyy-MM-dd'T'HH:mm:ss.SSSZ: " +
			 * genericDatetime("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
			 * System.out.println("yyMMddHHmmss'Z': " +
			 * genericDatetime("yyMMddHHmmss'Z'")); System.out.println(
			 * "MM_dd_yyyy-HH_mm_ss: " +
			 * genericDatetime("MM_dd_yyyy-HH_mm_ss")); System.out.println(
			 * "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ: " +
			 * genericDatetime("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ"));
			 * System.out.println("MMMM d, yyyy G: " + genericDatetime(
			 * "MMMM d, yyyy G")); System.out.println(
			 * "EEE, MMM dd yyyy HH:mm:ss.SSS zzz: " + genericDatetime(
			 * "EEE, MMM dd yyyy HH:mm:ss.SSS zzz")); System.out.println(
			 * "EEEE, MMMM dd yyyy HH:mm:ss.SSS zzz: " + genericDatetime(
			 * "EEEE, MMMM dd yyyy HH:mm:ss.SSS zzz"));
			 * 
			 * String yearWeekDayofweek = genericDatetime("yyyy-'W'ww-F");
			 * String yearandDayoftheYear = genericDatetime("yyyy-DDD");
			 * 
			 * System.out.println("yearWeekDayofweek: " + yearWeekDayofweek);
			 * System.out.println("yearandDayoftheYear: " +
			 * yearandDayoftheYear); System.out.println("MyDate: " +
			 * yearWeekDayofweek + "-D" +
			 * yearandDayoftheYear.substring((yearandDayoftheYear.length() - 3),
			 * yearandDayoftheYear.length()));
			 * 
			 */

			// System.out.println(": "+genericDatetime(""));

			// https://dzone.com/articles/java-simpledateformat-guide
			/*
			 * Date With Week ... new SimpleDateFormat("yyyy-'W'ww"); // prints:
			 * 2017-W08
			 * 
			 * 
			 * Date With Week and Day of the Week ... new
			 * SimpleDateFormat("yyyy-'W'ww-F"); // prints: 2017-W08-3
			 * 
			 * 
			 * Date With Year and Day of the Year ... new
			 * SimpleDateFormat("yyyy-DDD"); // prints: 2017-050
			 * 
			 * 
			 * Time of the Day in Local Time Zone ... new
			 * SimpleDateFormat("HH:mm:ss"); // prints: 22:52:11
			 * 
			 * 
			 * Time of the Day With Time Zone ... new
			 * SimpleDateFormat("HH:mm:ssZZZZ"); // prints: 22:55:37-0800
			 */
			/*
			 * yyyy-MM-dd'T'HH:mm:ssZZZZ yyyy-MM-dd'T'HH:mmZZZZ
			 * yyyy-MM-dd'T'HH:mm:ss.SSSZZZZ yyyy-'W'ww yyyy-'W'ww-F yyyy-DDD
			 * HH:mm:ssZZZZ" yyyy-MM-dd yyyyMMdd_HHmmss yyyy-MM-dd HH:mm:ss
			 * dd/MM/yyyy dd HH:mm [yyyy-MM-dd HH:mm:ss] yyyyMMddHHmmss
			 * yyyy-MM-dd HH:mm:ss dd-MM-yyyy hh:mm a z dd/MM/yyyy
			 * yyyy-MM-dd'T'HH:mm:ss.SSSZ yyyy-MM-dd HH:mm:ss yyMMddHHmmss'Z'
			 * MM_dd_yyyy-HH_mm_ss "HH:mm:ss" M/d/yy h:mm a
			 * 
			 * yyyy-MM-dd HH:mm:ss SSS Z yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ
			 * yyyy-MM-dd'T'HH:mm:ss.SSSXXX yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
			 * dd/MM/yyyy EEE, MMM dd yyyy HH:mm:ss.SSS zzz MMMM d, yyyy G
			 * yyyy-MM-dd HH:mm:ss.SSS Z "yyyy-MM-dd-hh.mm.ss"
			 * 
			 * yyyy-MM-dd EEE MMM dd hh:mm:ss yyyy
			 */

			/*
			 * SimpleDateFormat dateFormat = new SimpleDateFormat("d-MMM"); Date
			 * date2 = new Date(); System.out.println("d-MMM: " +
			 * dateFormat.format(date2));
			 * 
			 * String someString = "1234"; long c1 =
			 * someString.chars().filter(ch -> ch == '1').count();
			 * System.out.println("1234 -> count of 1 = "+c1);
			 * 
			 * someString = "1134"; long c2 = someString.chars().filter(ch -> ch
			 * == '1').count(); System.out.println(someString+
			 * " -> count of 1 = "+c2);
			 * 
			 * someString = "1114"; long c3 = someString.chars().filter(ch -> ch
			 * == '1').count(); System.out.println(someString+
			 * " -> count of 1 = "+c3);
			 * 
			 * someString = "1111"; long c4 = someString.chars().filter(ch -> ch
			 * == '1').count(); System.out.println(someString+
			 * " -> count of 1 = "+c4);
			 * 
			 * someString = "1034"; long c5 = someString.chars().filter(ch -> ch
			 * == '1').count(); System.out.println(someString+
			 * " -> count of 1 = "+c5);
			 * 
			 * someString = "9134"; long c6 = someString.chars().filter(ch -> ch
			 * == '1').count(); System.out.println(someString+
			 * " -> count of 1 = "+c6);
			 * 
			 * someString = "1901"; long c7 = someString.chars().filter(ch -> ch
			 * == '1').count(); System.out.println(someString+
			 * " -> count of 1 = "+c7);
			 * 
			 * someString = "0000"; long c8 = someString.chars().filter(ch -> ch
			 * == '1').count(); System.out.println(someString+
			 * " -> count of 1 = "+c8);
			 * 
			 * someString = "9999"; long c9 = someString.chars().filter(ch -> ch
			 * == '1').count(); System.out.println(someString+
			 * " -> count of 1 = "+c9);
			 */

			DecimalFormat formatter = new DecimalFormat("#,###.##");
			String amount = formatter.format(Double.parseDouble("5000.00"));
			System.out.println("amount UGX: " + amount);

		} catch (Exception ex) {
			System.out.println(" \n**** anyDate ****\n" + ex.getMessage());
		}

	}
}
