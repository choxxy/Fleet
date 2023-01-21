package com.fleet.common.schedule;

import com.fleet.drone.service.DroneService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BatteryScheduler {

    final DroneService droneService;

    public BatteryScheduler(DroneService droneService) {
        this.droneService = droneService;
    }

    // Every  minute
    @Scheduled(cron = "0 */1 * * * *")
    public void logDroneBatteryStatus() {
        droneService.logDronesBatteryLevel();
    }

    // Every  2 minutes
    @Scheduled(cron = "0 */2 * * * *")
    public void updateBatteryStatus() throws IOException {
        droneService.updateBatteryLevel();
    }

}
