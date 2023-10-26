# DelilahBot
![dream_TradingCard (3)](https://github.com/aleclev/delilah-bot/assets/55294110/66d6d8a4-8b04-48bc-b567-f2964a36384e)

Delilah is the official discord bot of discord.gg/sherparun.

This is a multipurpose bot we use to facilitate user interactions on the server. Popular functionalities include the GroupEvent (LFG) creation module and Dictionary module.

## Setup
Setting up this bot is fairly simple and consists mainly of setting up java and the many environnment variables. 

### Java/Maven
This project uses [Maven](https://maven.apache.org/download.cgi) for dependency management. You will need to download and install it.

Then, from the root of the project, run the following command:
`mvn clean install`

This will install all required dependencies.

To execute this project you will need a Java JDK (version 18+ recommended). You can start the project with the following command:
`java $JAVA_OPTS -cp target/classes:target/dependency/* delilah.DelilahApplication`

### Environnment variables and External Dependencies
This project has a few external dependencies, these dependencies can be configured with several the many environment variables.

For a list of these environment variables, please consult this file: src/main/resources/application.properties

For Discord dependencies, you will need to register an application through [Discord's developer portal](https://discord.com/developers/applications).

This project uses a MongoDB database. However, it should be fairly easy to adapt this to other Database engines.
