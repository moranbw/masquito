package com.moranbw.masquito.confservice;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.moranbw.masquito.properties.MasquitoProperties;
import com.moranbw.masquito.common.DnsMasqCommands;
import com.moranbw.masquito.common.ProcessUtil;
import com.moranbw.masquito.common.ReaderWriterUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/conf")
public class ConfigurationController {

    private final MasquitoProperties properties;
    Logger LOGGER = LoggerFactory.getLogger(ConfigurationController.class);

    @Autowired
    public ConfigurationController(MasquitoProperties aProperties) {
        this.properties = aProperties;
    }

    @GetMapping
    public ResponseEntity<Map<String, Collection<String>>> getConfiguration() {
        try {
            LOGGER.info("Reading dnsmasq.conf...");
            Map<String, Collection<String>> map = ReaderWriterUtil.readDnsMasqConf(this.properties.getDnsMasqConfPath());
            return ResponseEntity.ok().body(map);
        } catch (IOException aException) {
            LOGGER.error("ERROR: IOException while reading dnsmasq.conf");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping
    public ResponseEntity<String> setConfiguration(@RequestBody Map<String, Collection<String>> aDnsMasqConfMap) {
        try {
            LOGGER.info("Stopping dnsmasq...");
            ProcessUtil.runCommand(DnsMasqCommands.STOP);

            LOGGER.info("Writing dnsmasq.conf...");
            ReaderWriterUtil.writeDnsMasqConf(this.properties.getDnsMasqConfPath(), aDnsMasqConfMap);

            LOGGER.info("Starting dnsmasq...");
            ProcessUtil.runCommand(DnsMasqCommands.START);

            return ResponseEntity.ok().build();
        } catch (IOException aException) {
            LOGGER.error("ERROR: IOException while writing dnsmasq.conf");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (InterruptedException aException) {
            LOGGER.error("ERROR: InterruptedException while stopping and/or starting dnsmasq");
            return ResponseEntity.badRequest().body("There was an error restarting dnsmasq");
        }
    }

    @PutMapping("/{aCommand}")
    public ResponseEntity<String> doCommand(@PathVariable String aCommand) {
        try {
            if (aCommand.equals("start")) {
                LOGGER.info("Starting dnsmasq...");
                ProcessUtil.runCommand(DnsMasqCommands.START);
                return ResponseEntity.ok().build();
            } else if (aCommand.equals("stop")) {
                LOGGER.info("Stopping dnsmasq...");
                ProcessUtil.runCommand(DnsMasqCommands.STOP);
                return ResponseEntity.ok().build();
            } else if (aCommand.equals("restart")) {
                LOGGER.info("Restarting dnsmasq...");
                ProcessUtil.runCommand(DnsMasqCommands.RESTART);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (IOException e) {
            LOGGER.error("ERROR: IOException while starting/stopping/restarting dnsmasq");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (InterruptedException e) {
            LOGGER.error("ERROR: InterruptedException while starting/stopping/restarting dnsmasq");
            return ResponseEntity.badRequest().body("There was an error starting/stopping/restarting dnsmasq");
        }
    }

}