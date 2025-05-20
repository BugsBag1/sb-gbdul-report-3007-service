package kz.govtech.m11s.sb_gbdul_report_3007_service.msg;

import kz.govtech.m11s.syncshepclient.web.ws.client.interceptor.IShepClientInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.context.MessageContext;
import org.springframework.xml.transform.TransformerHelper;
import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import javax.xml.XMLConstants;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

@Component
@RequiredArgsConstructor
@Order(25000)
@Slf4j
public class PayloadPrefixInterceptor extends TransformerHelper implements IShepClientInterceptor {
    private static final String XMLNS_PREFIX = "xmlns:";

    @SneakyThrows
    @Override
    public boolean handleRequest(final MessageContext messageContext) throws WebServiceClientException {
        try {
            WebServiceMessage request = messageContext.getRequest();
            Source payloadSource = request.getPayloadSource();
            DOMResult result = new DOMResult();

            transform(payloadSource, result);
            Document document = (Document) result.getNode();
            document.normalizeDocument();

            cleanNamespaces(document.getDocumentElement(), false);

            String xmlString = serializeDocument(document);

            Transformer transformer = getCleanTransformer();
            transformer.transform(new StreamSource(new StringReader(xmlString)), request.getPayloadResult());

            return true;
        } catch (Exception e) {
            log.error("Ошибка при обработке SOAP-запроса", e);
            return false;
        }
    }

    private void cleanNamespaces(Node node, boolean isInsideData) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            boolean currentIsInsideData = isInsideData || "data".equals(element.getLocalName());

            NamedNodeMap attributes = element.getAttributes();
            for (int i = attributes.getLength() - 1; i >= 0; i--) {
                Node attr = attributes.item(i);
                if (attr != null && attr.getNodeName().startsWith(XMLNS_PREFIX) && !currentIsInsideData) {
                    attributes.removeNamedItem(attr.getNodeName());
                }
            }

            NodeList children = element.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                cleanNamespaces(children.item(i), isInsideData);
            }
        }
    }
    private String serializeDocument(Document document) throws Exception {
        DOMImplementationLS domImpl = (DOMImplementationLS) document.getImplementation().getFeature("LS", "3.0");
        LSSerializer serializer = domImpl.createLSSerializer();
        serializer.getDomConfig().setParameter("format-pretty-print", false);
        serializer.getDomConfig().setParameter("xml-declaration", false);
        return serializer.writeToString(document);
    }

    private Transformer getCleanTransformer() throws TransformerConfigurationException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

        return transformer;
    }

    @Override
    public boolean handleResponse(final MessageContext messageContext) {
        return true;
    }

    @Override
    public boolean handleFault(final MessageContext messageContext) {
        return true;
    }

    @Override
    public void afterCompletion(final MessageContext messageContext, final Exception e) {
        if (e != null) {
            log.error("Ошибка в обработке SOAP-сообщения", e);
        }
    }
}
