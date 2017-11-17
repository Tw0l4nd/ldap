# auth-app

auth with AD 2003 using ldaps protocol

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

* AD server
* account for **auth-app** in AD with read/write access
* certificates for LDAPS both on host (in `keystore` of JVM) and server: `192.168.151.10 ad-grsu.grsu.local` 
* you should have `ad-grsu.grsu.local` record in your `hosts` file
* `.\keytool.exe -trustcacerts -keystore "C:\Program Files\Java\jre1.8.0_144\lib\security\cacerts" -storepass changeit -importcert -alias grsu-11 -file ad-grsu.cer`
* `.\keytool.exe -trustcacerts -keystore "C:\Program Files\Java\jre1.8.0_144\lib\security\cacerts" -storepass changeit -importcert -alias grsu-12 -file CONT-CA01.cer`
* `.\keytool.exe -trustcacerts -keystore "C:\Program Files\Java\jre1.8.0_144\lib\security\cacerts" -storepass changeit -importcert -alias grsu-13 -file grsu-local.cer`

### Installing

_TODO: write this section_

## Running the tests

_TODO: write this section_

## Deployment

_TODO: write this section_

## Built With

* [Spring LDAP](https://projects.spring.io/spring-ldap/) – Spring LDAP is a Java library for simplifying LDAP operations, based on the pattern of Spring's JdbcTemplate. The framework relieves the user of common chores, such as looking up and closing contexts, looping through results, encoding/decoding values and filters, and more.
* [Maven](https://maven.apache.org/) – Dependency Management

## Authors

* **Tw0l4nd**
* **RomanovskijAlexandr**

## License

Copyright (C) Tw0l4nd, RomanovskijAlexandr – All Rights Reserved

Unauthorized copying of this file, via any medium is strictly prohibited.
Proprietary and confidential.
Written by Tw0l4nd, November 2017
