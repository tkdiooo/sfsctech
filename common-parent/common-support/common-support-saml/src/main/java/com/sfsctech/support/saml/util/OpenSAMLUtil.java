package com.sfsctech.support.saml.util;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.SignableSAMLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;


public class OpenSAMLUtil {
    private static Logger logger = LoggerFactory.getLogger(OpenSAMLUtil.class);

    @SuppressWarnings("unchecked")
    public static <T> T buildSAMLObject(final Class<T> clazz) {
        T object;
        try {
            XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
            QName defaultElementName = (QName) clazz.getDeclaredField("DEFAULT_ELEMENT_NAME").get(null);
            XMLObjectBuilder<?> builder = builderFactory.getBuilder(defaultElementName);
            assert builder != null;
            object = (T) builder.buildObject(defaultElementName);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Could not create SAML object");
        }

        return object;
    }

    public static String logSAMLObject(final XMLObject object) {
        Element element = null;

        if (object instanceof SignableSAMLObject && ((SignableSAMLObject) object).isSigned() && object.getDOM() != null) {
            element = object.getDOM();
        } else {
            try {
                Marshaller out = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(object);
                assert out != null;
                out.marshall(object);
                element = object.getDOM();
            } catch (MarshallingException e) {
                logger.error(e.getMessage(), e);
            }
        }

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(element);

            transformer.transform(source, result);
            String xmlString = result.getWriter().toString();

            logger.info(xmlString);
            return xmlString;
        } catch (TransformerException e) {
            e.printStackTrace();
            return "";
        }
    }
}
