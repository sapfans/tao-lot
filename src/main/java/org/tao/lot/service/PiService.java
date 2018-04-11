package org.tao.lot.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@Service
public class PiService {
    public static final GpioController gpio = GpioFactory.getInstance();
    public static GpioPinDigitalOutput myLed;
    
    @PostConstruct
    public void initPi(){
    myLed   = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "" ,PinState.LOW);
    }
}
