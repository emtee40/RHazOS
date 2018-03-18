# RHazOS v1.1

## Features

See https://github.com/RHazDev/TestPlugin

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
		<version>1.1</version>
		<scope>compile</scope> <!-- If you want to shade it -->
	</dependency>
</dependencies>
```

## Changelog

##### v1.1 "The Command Update"

* Improved commands system

* Added tools for "help" commands