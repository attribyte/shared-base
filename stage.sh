#!/bin/sh
VERSION="1.0.2"
cp attribyte-shared-base-1.0.pom dist/lib/attribyte-shared-base-${VERSION}.pom
cd dist/lib
gpg -ab attribyte-shared-base-${VERSION}.pom
gpg -ab attribyte-shared-base-${VERSION}.jar
gpg -ab attribyte-shared-base-${VERSION}-sources.jar
gpg -ab attribyte-shared-base-${VERSION}-javadoc.jar
jar -cvf ../bundle.jar *