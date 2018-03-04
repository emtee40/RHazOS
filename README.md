# RHazEvents v1.0

Event system for Java

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
		<artifactId>events</artifactId>
		<version>1.0</version>
		<scope>compile</scope> <!-- If you want to shade it -->
	</dependency>
</dependencies>
```