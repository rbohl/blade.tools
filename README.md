# Blade Tools

[![Travis CI Build Status](https://travis-ci.org/gamerson/blade.tools.svg?branch=master)](https://travis-ci.org/gamerson/blade.tools)
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/gamerson/blade.tools?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Blade Tools is a set of modular developer tools for building Liferay 7.0 plugins, aka modules built with OSGi.  The idea is that instead of many of the tools that would normally be developed and released only as a part of Liferay IDE can now be built in a re-usable way (i.e. modular, just like Liferay 7.0 Core itself) and can be used outside or in conjunction with Liferay IDE, making these tools available for non-Liferay IDE or non-Eclipse users.

## Install 

Right now the only installable tool is the ```blade``` CLI tool that provides a few commands.  It can be installed using the following:

### Install JPM 
Install JPM (Mac, Linux)
```
$ curl http://www.jpm4j.org/install/script | sh
```

OR

Install JPM (Windows)
```
Visit the JPM4J [Windows installation](https://www.jpm4j.org/#!/md/windows) setup guide.
```

### Build blade cli jar

Clone this repo, and then from the command line execute following command:

```
$ gradle build export.blade.cli
```

### Install Blade Tools jar using JPM

```
$ jpm install -fl blade.cli/generated/distributions/executable/blade.cli.jar
```

Now you should have the ```blade``` executable in your path. Try it by running:

```
blade
```

## Usage

Once you have the blade cli installed you can see the list of commands just type
```
blade
```

Or ```java -jar blade.cli.jar``` if not using JPM.

Current available commands

### Create

The ```Create``` command allows you to create new Liferay 7 module projects based on gradle or maven build.

```
$ blade create helloworld 
```

This will create a new helloworld portlet module project that contains an OSGi component built by a gradle script.
 To see all the options of the create command just run ```$blade create -h``` for all options.

### Deploy

First, start Liferay 7 Portal, once it is running you can build your Liferay 7 module and deploy it

```
helloworld $ gradle build
helloworld $ blade deploy build/libs/helloworld-1.0.jar
```

If you have Liferay 7 running the blade tool should connect to the Liferay Module Framework and install your newly built module jar file.  You can check the OSGi shell of Liferay to verify

```
$ telnet localhost 11311
```
Then issue the command ```lb helloworld```

### Migrate

## License
All source to this project is available under [Apache 2.0 License](/LICENSE.txt)
