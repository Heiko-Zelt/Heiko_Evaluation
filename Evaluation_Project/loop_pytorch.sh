unset CLASSPATH
export JAVA_HOME=/usr/lib/jvm/java-1.14.0-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
for i in 1 2 3 4 5 6 7 8 9; do
java -Dfile.encoding=UTF-8 -classpath /home/heiko/git-repos/Heiko_Evaluation/Evaluation_Project/target/classes:/home/heiko/.m2/repository/org/apache/logging/log4j/log4j-api/2.14.1/log4j-api-2.14.1.jar:/home/heiko/.m2/repository/org/apache/logging/log4j/log4j-core/2.14.1/log4j-core-2.14.1.jar:/home/heiko/.m2/repository/javax/xml/bind/jaxb-api/2.2.5/jaxb-api-2.2.5.jar:/home/heiko/.m2/repository/com/sun/xml/bind/jaxb-impl/2.2.5/jaxb-impl-2.2.5.jar:/home/heiko/.m2/repository/javax/activation/activation/1.1.1/activation-1.1.1.jar -XX:+ShowCodeDetailsInExceptionMessages de.heikozelt.objectdetection.Eval pytorch_result$i.xml annotations.csv
echo threshold 0.$i
echo
echo
echo
done
