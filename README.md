Fleet
==============================

Fleet is an application that provide REST APIs that allows clients to communicate with the drones. 

The APIs allows:
- Registering a drone;
- Loading a drone with medication items;
- Checking loaded medication items for a given drone; 
- Checking available drones for loading;
- Check drone battery level for a given drone;


Building the project
====================

To build the application you need JDK 17, Gradle and git installed in your machine.

After installed the required tools follow the steps below.

1. Checkout the application repo 
2. Run the command`./gradlew bootRun` within the module

Working with the IDE
====================
The repo can be opened in either Eclipse or IntelliJ. 

Running Tests
=============
1. Run the command`./gradlew clean test` within the module

API Endpoints
====================
Overall, the API is able to handle the following requests and responses:

## Drone APIs
- POST api/drone/register: to register a drone
- POST api/drone/<drone-id>/load: to load a drone with medication items

- GET  api/drone/: get all drones
- GET  api/drone/<serialNumber>/medications: to check loaded medication items for a given drone
- GET  api/drones/available: to check available drones for loading
- GET  api/drone/<serialNumber>/battery: to check drone battery level for a given drone
- PUT  api/drone/<serialNumber>: update drone data

## Medication APIs
- POST api/medication/register: to register medication
- POST api/medication/<id>: get medication item
- GET  api/medication/: get all medications
- GET  api/drone/<id>: update medication data

Please note the endpoints are password protected. Use username : `Admin` and password: `password` for credentials.

Logs
====================
The application generates logs showing the Drones battery level periodically. These logs can be found the /logs directory.
