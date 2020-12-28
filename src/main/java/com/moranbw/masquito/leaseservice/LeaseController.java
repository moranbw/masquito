package com.moranbw.masquito.leaseservice;

import java.io.IOException;
import java.util.ArrayList;

import com.moranbw.masquito.common.DnsMasqCommands;
import com.moranbw.masquito.common.ProcessUtil;
import com.moranbw.masquito.common.ReaderWriterUtil;
import com.moranbw.masquito.properties.MasquitoProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/leases")
public class LeaseController {

    private final MasquitoProperties properties;
    Logger LOGGER = LoggerFactory.getLogger(LeaseController.class);

    @Autowired
    public LeaseController(MasquitoProperties aProperties) {
        this.properties = aProperties;
    }

    @GetMapping
    public ResponseEntity<ArrayList<Lease>> getLeases() {
        try {
            LOGGER.info("Reading dnsmasq.leases...");
            ArrayList<Lease> leases = ReaderWriterUtil.readDHCPLeases(this.properties.getDnsMasqLeasesPath());
            return ResponseEntity.ok().body(leases);
        } catch (IOException e) {
            LOGGER.error("ERROR: IOException while reading dnsmasq.leases");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{aMacAddress}")
    public ResponseEntity<String> deleteLease(@PathVariable String aMacAddress) {
        try {
            LOGGER.info("Reading dnsmasq.leases...");
            ArrayList<Lease> leases = ReaderWriterUtil.readDHCPLeases(this.properties.getDnsMasqLeasesPath());
            for (Lease lease : leases) {
                if (lease.getMacAddress().equals(aMacAddress)) {
                    leases.remove(lease);
                    break;
                }
            }

            LOGGER.info("Stopping dnsmasq...");
            ProcessUtil.runCommand(DnsMasqCommands.STOP);

            LOGGER.info("Writing dnsmasq.leases...");
            ReaderWriterUtil.writeDHCPLeases(this.properties.getDnsMasqLeasesPath(), leases);

            LOGGER.info("Starting dnsmasq...");
            ProcessUtil.runCommand(DnsMasqCommands.START);

            return ResponseEntity.ok().build();
        } catch (IOException e) {
            LOGGER.error("ERROR: IOException while reading and/or writing dnsmasq.leases");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (InterruptedException e) {
            LOGGER.error("ERROR: InterruptedException while stopping and/or starting dnsmasq");
            return ResponseEntity.badRequest().body("There was an error restarting dnsmasq");
        }
    }

}
