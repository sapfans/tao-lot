package org.tao.lot.service;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;

import org.bitlet.weupnp.GatewayDevice;
import org.bitlet.weupnp.GatewayDiscover;
import org.bitlet.weupnp.PortMappingEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import lombok.Getter;

@Service
@Getter
public class PortMapService {
    static Logger logger = LoggerFactory.getLogger(PortMapService.class);
    @Value("${upnp.externalPort}")
    int           externalPort;
    @Value("${server.port}")
    int           internalPort;

    String        externalIPAddress;
    String        localAddress;

    @Autowired
    EmailService  emailService;

    @PostConstruct
    public void setPortMaping()
            throws SocketException, UnknownHostException, IOException, SAXException, ParserConfigurationException {

        GatewayDiscover discover = new GatewayDiscover();
        discover.discover();
        GatewayDevice d = discover.getValidGateway();
        if (null != d) {
            logger.info("Found gateway device.\n{} ({})", new Object[] { d.getModelName(), d.getModelDescription() });
        } else {
            logger.error("No valid gateway device found.");
            throw new RuntimeException("No valid gateway device found.");
        }
        localAddress = d.getLocalAddress().getHostAddress();
        logger.info("Using local address: {}", localAddress);
        externalIPAddress = d.getExternalIPAddress();
        logger.info("External address: {}", externalIPAddress);

        PortMappingEntry portMapping = new PortMappingEntry();
        logger.info("Querying device to see if mapping for port {} already exists", externalPort);
        d.getSpecificPortMappingEntry(externalPort, "TCP", portMapping);
        if (portMapping.getInternalClient() != null) {
            if (!portMapping.getInternalClient().equals(localAddress)) {
                logger.error("Port {} was already mapped by other client.", externalPort);
                throw new RuntimeException("Port was already mapped by other client.");

            } else {
                logger.info("Port was already mapped by self. {}:{}", externalIPAddress, externalPort);
            }
        } else {
            logger.info("Sending port mapping request");
            if (!d.addPortMapping(externalPort, internalPort, localAddress, "TCP", "tao-lot")) {
                logger.error("Port mapping attempt failed");
                throw new RuntimeException("Port mapping attempt failed");
            } else {

                logger.info("Mapping successful. {} ：{}", externalIPAddress, externalPort);

            }
        }
        emailService.sendSimpleMail("ip and port", externalIPAddress + ":" + externalPort);
    }
}
