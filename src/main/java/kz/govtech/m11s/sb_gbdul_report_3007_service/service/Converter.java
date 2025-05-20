package kz.govtech.m11s.sb_gbdul_report_3007_service.service;

import kz.govtech.m11s.sb_gbdul_report_3007_service.config.AppConfigurator;
import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.RequestDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.ResponseDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.mapper.RequestMapper;
import kz.govtech.m11s.sb_gbdul_report_3007_service.mapper.ResponseMapper;
import kz.govtech.m11s.sb_gbdul_report_3007_service.xsd.RequestAndResponse.ObjectFactory;
import kz.govtech.m11s.sb_gbdul_report_3007_service.xsd.RequestAndResponse.Request;
import kz.govtech.m11s.sb_gbdul_report_3007_service.xsd.RequestAndResponse.Response;
import kz.govtech.m11s.syncshepclient.dto.DataResponse;
import kz.govtech.m11s.syncshepclient.service.AConverter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class Converter extends AConverter<RequestDTO, Request, Response, ResponseDTO> {
    private final AppConfigurator configurator;
    private final RequestMapper requestMapper;
    private final ResponseMapper responseMapper;

    @Override
    protected String getServiceId() {
        return configurator.getShepServiceId();
    }

    @Override
    protected QName getRequestQName() {
        return new QName("request");
    }

    @Override
    protected Class<Request> getServiceRequestClass() {
        return Request.class;
    }

    @Override
    protected Class<Response> getServiceResponseClass() {
        return Response.class;
    }

    @Override
    protected Request convertToServiceRequest(RequestDTO requestDto) {
        return requestMapper.map(requestDto);
    }

    @SneakyThrows
    @Override
    protected ResponseDTO convertToApiResponse(Response response) {
        return responseMapper.toDTO(response);
    }

    public ResponseDTO processCreateResponse(DataResponse dataResponse) {
        if (dataResponse == null || dataResponse.getData() == null) return null;
        Element dataElement = dataResponse.getData();
        Optional<Element> firstElementOpt = findFirstElement(dataElement);
        if (firstElementOpt.isPresent()) {
            Response parsed = extractResponseFromElement(firstElementOpt.get());
            return convertToApiResponse(parsed);
        } else {
            String textXml = extractTextContent(dataElement);
            if (textXml != null && !textXml.trim().isEmpty()) {
                Response parsed = unmarshalFromXmlString(textXml);
                return convertToApiResponse(parsed);
            }
        }
        return createResponse(dataResponse);
    }


    private Optional<Element> findFirstElement(Element dataElement) {
        NodeList children = dataElement.getChildNodes();
        Element firstElement = null;
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                if (firstElement != null) {
                    return Optional.empty();
                }
                firstElement = (Element) children.item(i);
            }
        }
        return Optional.ofNullable(firstElement);
    }

    public Response extractResponseFromElement(Element element) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(element), new StreamResult(writer));
            String responseXml = writer.toString();
            JAXBContext jaxbContext = createJaxbContext(Response.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            JAXBElement<Response> jaxbElement = (JAXBElement<Response>)
                unmarshaller.unmarshal(new StringReader(responseXml));
            return jaxbElement.getValue();
        } catch (Exception e) {
            log.error("Ошибка при разборе XML: ", e);
        }
        return null;
    }

    private String extractTextContent(Element element) {
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() == Node.TEXT_NODE || node.getNodeType() == Node.CDATA_SECTION_NODE) {
                return node.getTextContent();
            }
        }
        return null;
    }

    private Response unmarshalFromXmlString(String xml) {
        try {
            JAXBContext jaxbContext = createJaxbContext(Response.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            JAXBElement<Response> jaxbElement = (JAXBElement<Response>)
                unmarshaller.unmarshal(new StringReader(xml));
            return jaxbElement.getValue();
        } catch (Exception e) {
            log.error("Ошибка при разборе текстовой XML-строки: ", e);
            return null;
        }
    }

    private JAXBContext createJaxbContext(Class<?> clazz) throws JAXBException {
        return JAXBContext.newInstance(clazz.getPackage().getName());
    }
}
