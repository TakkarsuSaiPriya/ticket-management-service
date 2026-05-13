package com.cts.ticket.util;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.core.io.ClassPathResource;
import org.xml.sax.SAXException;

import java.io.StringReader;

import javax.xml.transform.stream.StreamSource;

public class XmlValidator {

    public static void validate(String xml) {
        try {

            // Load XSD file
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Schema schema = factory.newSchema(
                    new ClassPathResource("ticket.xsd").getFile()
            );

            Validator validator = schema.newValidator();

            // Validate XML
            validator.validate(new StreamSource(new StringReader(xml)));

        } catch (Exception e) {
            throw new RuntimeException("Invalid XML");
        }
    }
}
