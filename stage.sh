pushd .
cp attribyte-shared-base-1.0.pom dist/lib
cd dist/lib
gpg -ab attribyte-shared-base-1.0.pom
gpg -ab attribyte-shared-base-1.0.jar
gpg -ab attribyte-shared-base-1.0-sources.jar
gpg -ab attribyte-shared-base-1.0-javadoc.jar
jar -cvf ../bundle.jar *
popd


