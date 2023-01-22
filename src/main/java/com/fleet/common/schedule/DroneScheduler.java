package com.fleet.common.schedule;

import com.fleet.drone.service.DroneService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DroneScheduler {

    final DroneService droneService;

    public DroneScheduler(DroneService droneService) {
        this.droneService = droneService;
    }

    // Every  3 minutes
    @Scheduled(cron = "0 */3 * * * *")
    public void updateDroneStatus() {
        droneService.updateDroneStatus();
    }

    // Every  minute
    @Scheduled(cron = "0 */1 * * * *")
    public void logDroneBatteryStatus() {
        droneService.logDronesBatteryLevel();
    }

    // Every  2 minutes
    @Scheduled(cron = "0 */2 * * * *")
    public void updateBatteryStatus() {
        droneService.updateBatteryLevel();
    }

}
