/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.materiel.push;

import static com.hengyi.japp.materiel.push.ImportSapMaraResource.CHANNEL;
import javax.faces.application.FacesMessage;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;

/**
 *
 * @author jzb
 */
@PushEndpoint(CHANNEL)
public class ImportSapMaraResource {

    public static final String CHANNEL = "/importSapMara";

    @OnMessage(encoders = {JSONEncoder.class})
    public FacesMessage onMessage(FacesMessage message) {
        return message;
    }
}
