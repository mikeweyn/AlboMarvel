docker run -it --rm  \
-v "$(pwd)"/.m2:/root/.m2 \
-v "$(pwd)":/usr/src  \
-w /usr/src maven:3-jdk-11 \
mvn -B -X -e -Dmaven.test.skip=true clean compile install


