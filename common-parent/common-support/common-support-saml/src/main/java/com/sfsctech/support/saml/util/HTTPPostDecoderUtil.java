package com.sfsctech.support.saml.util;

import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.decoder.MessageDecodingException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.saml2.binding.decoding.impl.HTTPPostDecoder;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;

/**
 * Class HTTPPostDecoderUtil
 *
 * @author 张麒 2019-12-4.
 * @version Description:
 */
public class HTTPPostDecoderUtil extends HTTPPostDecoder {

    private final static HTTPPostDecoderUtil httpPostDecoderUtil;

    static {
        httpPostDecoderUtil = new HTTPPostDecoderUtil();
    }

    private MessageContext postDecoder(HttpServletRequest request) {
        super.setHttpServletRequest(request);
        try {
            super.doDecode();
        } catch (MessageDecodingException e) {
            e.printStackTrace();
        }
        return super.getMessageContext();
    }

    private MessageContext postDecoder(HttpServletRequest request, String xml) {
        try {
            MessageContext<SAMLObject> messageContext = new MessageContext<>();
            String relayState = request.getParameter("RelayState");
            SAMLBindingSupport.setRelayState(messageContext, relayState);
            SAMLObject inboundMessage = (SAMLObject) super.unmarshallMessage(new ByteArrayInputStream(xml.getBytes()));
            messageContext.setMessage(inboundMessage);
            super.populateBindingContext(messageContext);
            super.setMessageContext(messageContext);
        } catch (MessageDecodingException e) {
            e.printStackTrace();
        }
        return super.getMessageContext();
    }

    @SuppressWarnings("unchecked")
    public static <T> MessageContext<T> doDecode(HttpServletRequest request) {
        return (MessageContext<T>) httpPostDecoderUtil.postDecoder(request);
    }

    @SuppressWarnings("unchecked")
    public static <T> MessageContext<T> doDecode(HttpServletRequest request, String xml) {
        return (MessageContext<T>) httpPostDecoderUtil.postDecoder(request, xml);

    }
}
