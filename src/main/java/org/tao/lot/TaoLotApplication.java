package org.tao.lot;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.xml.sax.SAXException;

@EnableAutoConfiguration
@SpringBootApplication
public class TaoLotApplication extends SpringBootServletInitializer {
    static Logger logger = LoggerFactory.getLogger(TaoLotApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TaoLotApplication.class);
    }

    public static void main(String[] args)
            throws SocketException, UnknownHostException, IOException, SAXException, ParserConfigurationException {

        SpringApplication.run(TaoLotApplication.class);
    }

}
