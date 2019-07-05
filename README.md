MyBatis Generator Plugins
====================
Set of plugins for the mybatis-generator.

## RenameExampleMethodPlugin

e.g.:
```xml
<plugin type="com.jsmartx.mybatis.plugins.RenameExampleMethodPlugin">
  <property name="searchString" value="Example"/>
  <property name="replaceString" value="Query"/>
</plugin>
```

## Usage

```bash
# Package to jar file
mvn clean package

# Copy jar to your project
cp target/mybatis-generator-plugins.jar YOUR_PROJECT/lib/
```

Maven config:

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.mybatis.generator</groupId>
      <artifactId>mybatis-generator-maven-plugin</artifactId>
      <version>1.3.7</version>
      <configuration>
        <configurationFile>lib/generatorConfig.xml</configurationFile>
        <overwrite>true</overwrite>
      </configuration>
      <dependencies>
        <dependency>
          <groupId>mysql</groupId>
          <artifactId>mysql-connector-java</artifactId>
          <version>5.1.34</version>
        </dependency>
        <dependency>
          <groupId>com.jsmartx.mybatis</groupId>
          <artifactId>mybatis-generator-plugins</artifactId>
          <version>1.0.0</version>
          <scope>system</scope>
          <systemPath>${basedir}/lib/mybatis-generator-plugins.jar</systemPath>
        </dependency>
      </dependencies>
    </plugin>
  </plugins>
</build>
```