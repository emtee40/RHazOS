![](https://ipfs.io/ipfs/QmTUEKxGh39BL8BZfXGzLTV5XdALdmoYWZ2vTJx8vsz8Ak)

# RHazOS v1.3.5

A powerful Java console with plugins 

## Goto

* ##### [Wiki](https://github.com/RHazDev/RHazOS/wiki)

* ##### [Javadoc](https://rhazdev.github.io/RHaz-Maven/fr/rhaz/os/1.3.4/apidocs/)

* ##### [Discussion & Issues](https://github.com/RHazDev/RHazOS/issues)

* ##### [RHazOS for Android](https://github.com/RHazDev/RHazOS-Android), a RHazOS implementation for Android

* ##### [TestPlugin](https://github.com/RHazDev/TestPlugin), a plugin that tests and shows RHazOS features

## Overview

![](https://image.prntscr.com/image/CDe19kwzQvmG6lcXmI2euQ.png)

## Download

* ##### [Stable](https://github.com/RHazDev/RHazOS/releases)

* ##### [Build](https://github.com/RHazDev/RHazOS/tree/master/target)

## Features & Examples

```
new Command("mycommand").addAlias("mine").addExecutor(new CommandExecutor() {
			
	@Override
	public void run(CommandLine context) throws ExecutionException, PermissionException, ArgumentException {
		context.getSender().write("Hello world!");
	}
	
});
```

See [TestPlugin](https://github.com/RHazDev/TestPlugin) for more examples

## Maven dependency

Add the following sections to your pom.xml

```
<repositories>
	<repository>
		<id>RHazDev</id>
		<url>https://raw.github.com/RHazDev/RHaz-Maven/</url>
	</repository>
</repositories>

<dependencies>
	<dependency>
		<groupId>fr.rhaz</groupId>
		<artifactId>os</artifactId>
		<version>1.3.5</version>
		<scope>provided</scope>
	</dependency>
</dependencies>
```

## Changelog

#### v1.3.4 "The Console update"

* Improved logging and console

##### v1.1 "The Command Update"

* Improved commands system

* Added tools for "help" commands