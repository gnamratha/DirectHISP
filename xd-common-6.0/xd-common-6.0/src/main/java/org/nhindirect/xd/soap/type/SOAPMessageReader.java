package org.nhindirect.xd.soap.type;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.io.ByteArrayInputStream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.xml.soap.SOAPConstants;


public class SOAPMessageReader {

    public static SOAPMessage createSOAPMessageFromString(String soapString) throws SOAPException, IOException {
        // Use SOAP 1.2 Message Factory
        MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
        return factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(soapString.getBytes(StandardCharsets.UTF_8)));
    }

    public static String readSOAPMessageFromFile(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) throws SOAPException, IOException {
        String filePath = "soap dump 2.txt"; // Update this with the path to your SOAP message file
        String soapMessageString = readSOAPMessageFromFile(filePath);
        System.out.println("SOAP Message:");
        System.out.println(soapMessageString);

        SOAPMessage soapMessage = createSOAPMessageFromString(soapMessageString);

        SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
        SOAPHeader header = envelope.getHeader();
        header.detachNode();
        header = envelope.addHeader();

        QName actionName = new QName("http://www.w3.org/2005/08/addressing", "Action", "a");
        SOAPHeaderElement action = header.addHeaderElement(actionName);
        action.setMustUnderstand(true);
        action.setValue("urn:ihe:iti:2007:ProvideAndRegisterDocumentSet-b");

        QName messageIdName = new QName("http://www.w3.org/2005/08/addressing", "MessageID", "a");
        SOAPHeaderElement messageId = header.addHeaderElement(messageIdName);
        messageId.setValue("uuid:df12cd75-0dc0-4ee6-b6a9-1cf065961759req@medicity.correlation");

        QName fromName = new QName("http://www.w3.org/2005/08/addressing", "From", "a");
        SOAPHeaderElement from = header.addHeaderElement(fromName);
        SOAPElement address = from.addChildElement("Address", "a");
        address.setValue("urn:Medicity-HISP");

        QName toName = new QName("http://www.w3.org/2005/08/addressing", "To", "a");
        SOAPHeaderElement to = header.addHeaderElement(toName);
        to.setMustUnderstand(true);
        to.setValue("https://xdr.medicity.net:20000/");

        QName replyToName = new QName("http://www.w3.org/2005/08/addressing", "ReplyTo", "a");
        SOAPHeaderElement replyTo = header.addHeaderElement(replyToName);
        SOAPElement replyAddress = replyTo.addChildElement("Address", "a");
        replyAddress.setValue("http://www.w3.org/2005/08/addressing/anonymous");

        QName addressBlockName = new QName("urn:direct:addressing", "addressBlock", "direct");
        SOAPHeaderElement addressBlock = header.addHeaderElement(addressBlockName);
        addressBlock.addAttribute(new QName("role"), "urn:direct:addressing:destination");
        addressBlock.addAttribute(new QName("relay"), "true");
        SOAPElement fromMail = addressBlock.addChildElement("from", "direct");
        fromMail.setValue("mailto:hisp@direct.digitalhie.com");
        SOAPElement toMail = addressBlock.addChildElement("to", "direct");
        toMail.setValue("mailto:hisp@directtest.digitalhie.com");

        soapMessage.saveChanges();
        soapMessage.writeTo(System.out);

    }
}



