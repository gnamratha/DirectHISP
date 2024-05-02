package org.nhindirect.xd.transform.util;

import java.util.Date;
import java.util.UUID;
import org.nhindirect.xd.transform.pojo.SimplePerson;

/**
 * Generate DigitalHIE XDR Meta parameters when missing in the original metadata
 * @date Sep 21 2014
 */
public class Utils {

    private static SimplePerson DigitalHIESimplePerson = null;
    private static final String DigitalHIE_LOCAL_ID = "LocalOrg";
    private static final String DigitalHIE_LOCAL_ORG = "2.16.840.1.123.3.123";
    private static final String DigitalHIE_FIRSTNAME = "Sai";
    private static final String DigitalHIE_LASTNAME = "Valluripalli";
    private static final String DigitalHIE_Street = "4100 Lafayette Center Dr, Suite#204";
    private static final String DigitalHIE_CITY = "Chantilly";
    private static final String DigitalHIE_STATE = "VA";
    private static final String DigitalHIE_COUNTRY = "US";
    private static final String DigitalHIE_ZIPCODE = "02151";
    private static final String DigitalHIE_SOURCEID = "1";
    private static final String DigitalHIE_CONTENTTYPE_CODE = "XDR";
    private static final String DigitalHIE_CLASSIFICATION_CODE = "XDR";
    private static final String DigitalHIE_CONFIDENTIAL_CODE = "XDR";
    private static final String EMAIL_BODY_CLASS_CODE = "56444-3";
    private static final String EMAIL_BODY_CLASS_CODE_LOCALIZED = "Healthcare Communication";
    private static final String LOINC_OID = "2.16.840.1.123.3.123";
    private static final String XD_SMTP_HEADER = "X-XdOriginated";
    private static final String DEFAULT_DOCUMENT_NAME = "XDR Document";
    private static final String DEFAULT_MESSAGEBODY_NAME = "TextBody.txt";
    private static final String DEFAULT_SUBMISSION_NAME = "XDR Message";
    public static final String NONE = "NONE";
    private static final String PATIENT_ID = "PID-3|LocalOrg^^^&amp;2.16.840.1.123.3.123&amp;ISO";
    private static long docIndex = 0;

    public static SimplePerson GetDigitalHIESimplePersion() {
        if (DigitalHIESimplePerson == null) {
            DigitalHIESimplePerson = new SimplePerson();
            DigitalHIESimplePerson.setLocalId(DigitalHIE_LOCAL_ID);
            DigitalHIESimplePerson.setLocalOrg(DigitalHIE_LOCAL_ORG);
            DigitalHIESimplePerson.setFirstName(DigitalHIE_FIRSTNAME);
            DigitalHIESimplePerson.setLastName(DigitalHIE_LASTNAME);
            DigitalHIESimplePerson.setStreetAddress1(DigitalHIE_Street);
            DigitalHIESimplePerson.setCity(DigitalHIE_CITY);
            DigitalHIESimplePerson.setState(DigitalHIE_STATE);
            DigitalHIESimplePerson.setCountry(DigitalHIE_COUNTRY);
            DigitalHIESimplePerson.setZipCode(DigitalHIE_ZIPCODE);
            DigitalHIESimplePerson.setGenderCode("F");
            DigitalHIESimplePerson.setBirthDateTime("19850808");
        }
        return DigitalHIESimplePerson;
    }

    public static String GetDigitalHIEOID(){
        return DigitalHIE_LOCAL_ORG;
    }

    public static String GetDigitalHIESourceID(){
        return DigitalHIE_LOCAL_ORG + "." + DigitalHIE_SOURCEID;
    }
    public static String GetDigitalHIEPatientID(){
        //	 return GetDigitalHIESimplePersion().getSourcePatientId();
        return PATIENT_ID;
    }

    public static String GetDigitalHIEContentTypeCode(){
        return DigitalHIE_CONTENTTYPE_CODE;
    }

    public static String GetDigitalHIEClassificationCode(){
        return DigitalHIE_CLASSIFICATION_CODE;
    }
    public static String GetDigitalHIEConfidentialCode(){
        return DigitalHIE_CONFIDENTIAL_CODE;
    }

    public static String GetEmailBodyClassCode(){
        return EMAIL_BODY_CLASS_CODE;
    }

    public static String GetEmailBodyClassCodeLocalized(){
        return EMAIL_BODY_CLASS_CODE_LOCALIZED;
    }

    public static synchronized String GetDocUniqueId(){
        return DigitalHIE_LOCAL_ORG +"." + (new Date().getTime()) + "." + (++docIndex);
    }

    public static String GetXdSmtpHeader(){
        return XD_SMTP_HEADER;
    }

    public static String GetDefaultDocName(){
        return DEFAULT_DOCUMENT_NAME;
    }

    public static String GetDefaultSubmissionName(){
        return DEFAULT_SUBMISSION_NAME;
    }
    public static String GetDefaultTextBodyName(){
        return DEFAULT_MESSAGEBODY_NAME;
    }

}