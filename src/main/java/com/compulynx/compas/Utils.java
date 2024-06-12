package com.compulynx.compas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CLLSDJACKT013 on 2/20/2019.
 */
public class Utils {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private Logger logger = LoggerFactory.getLogger(Utils.class);

    public Utils(){

    }


    public String generateReceiptNumber(String intent){
        Date date = new Date();
        String dateStamp = sdf.format(date);
        Integer lastThreeDigits = generateThreeDigitRandomNumbers(1000,100).intValue();
        String receiptNumber = intent.concat("-").concat(dateStamp.substring(dateStamp.length()-3)).concat(lastThreeDigits.toString());
        logger.info(receiptNumber);
        return receiptNumber;
    }

    public Double generateThreeDigitRandomNumbers(Integer max, Integer min){
        return Math.floor(Math.random()*(max-min)+min);
    }
}
